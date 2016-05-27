package com.thinkcoo.mobile.model.rest.encrypt;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import android.content.Context;
import android.support.v4.util.LruCache;
import android.util.Base64;
import com.thinkcoo.mobile.utils.log.ThinkcooLog;

public class UrlDecodeUtil {
	
	public static final int CACHE_SIZE = 1024 * 1024 * 1;
	public static final String TAG = "UrlDecodeUtil";
	public static final String UTF8_CODEX = "UTF-8";
	
	private static final int MAX_ENCRYPT_BLOCK = 117;
	//private static final int MAX_DECRYPT_BLOCK  = 128;
	/**
	 * true--开启加密
	 */
	private static boolean WORK = true;
	private static PublicKey pubkey;
	private static Cipher cipherpub;
	private static boolean initIsSuccess;
	private static LruCache<String, String> urlLruCahce;

	public static void init(Context context) {
		initLruCahce();
		initIsSuccess = initPublicKey(context) && initCipher();
	}
	
	private static void initLruCahce() {
		urlLruCahce = new LruCache<>(CACHE_SIZE);
	}

	private static boolean initPublicKey(Context context){
		
		try{
			pubkey = getRSAPublicKeyFromAssets(context);
			return true;
		}catch(Exception e){
			ThinkcooLog.e(TAG, e.getMessage());
			return false;
		}
	}
	
	private static PublicKey getRSAPublicKeyFromAssets(Context context) {

		InputStream inputStream = null;
		ObjectInputStream objectInputStream = null;
		try {
			CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
			inputStream = context.getResources().getAssets().open("public_key.der");
			Certificate cert = certificatefactory.generateCertificate(inputStream);
			PublicKey publicKey = cert.getPublicKey();
			return publicKey;
		} catch (Exception e) {
			ThinkcooLog.e(TAG, e.getMessage());
		} finally {
			safeClose(objectInputStream);
			safeClose(inputStream);
		}
		return null;
	}
	

	private static boolean initCipher(){
		
		try {
			cipherpub = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipherpub.init(Cipher.ENCRYPT_MODE, pubkey);
			return true;
		} catch (Exception e) {
			ThinkcooLog.e(TAG, e.getMessage());
			return false;
		}
	}

	
	private static void safeClose(Closeable closeable) {
		
		if (null != closeable) {
			try {
				closeable.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	static String encryptionResultForDebug = null;
	
	public static String getEncryptionResultForDebug() {
		return encryptionResultForDebug;
	}
	
	public static String urlEncrypt(String rawUrl){
		
		if (!WORK) {
			return rawUrl;
		}
		
		String fromCahce = tryGetFromCache(rawUrl);
		if(null != fromCahce){
			ThinkcooLog.d(TAG, "从缓存中获得Url："  + fromCahce);
			return fromCahce;
		}
		
		UrlSpliter urlSpliter = new UrlSpliter(rawUrl);
		urlSpliter.doSplit();
		
		if (urlSpliter.isSplitSuccessed() && initIsSuccess) {
			String baseUrl = urlSpliter.getBaseUrl();
			String tobeEncryptionUrl = urlSpliter.getToBeEncryptionUrl();
			try {
				String encryptionResult = doEncrypt(tobeEncryptionUrl);
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append(baseUrl);
				stringBuilder.append(encryptionResult);
				String resultUrl = stringBuilder.toString();
				resultUrl = removeBr(resultUrl);
				cacheUrl(rawUrl,resultUrl);
				return resultUrl;
			} catch (Exception e) {
				ThinkcooLog.e(TAG, e.getMessage());
				return rawUrl;
			}
		}else {
			return rawUrl;
		}
	}
	
	private static String tryGetFromCache(String rawUrl) {
		if(null != urlLruCahce){
			return urlLruCahce.get(rawUrl);
		}
		return null;
	}

	private static void cacheUrl(String rawUrl, String resultUrl) {
		if (null != urlLruCahce) {
			urlLruCahce.put(rawUrl, resultUrl);
		}
	}

	private static String removeBr(String resultUrl) {
		String removeBrUrl = resultUrl.replaceAll("\n", "").trim();
		return removeBrUrl;
	}
	/**
	 * 加密
	 * @param rawString
	 * @return
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws IOException
	 */
	private static String doEncrypt(String rawString) throws IllegalBlockSizeException, BadPaddingException, IOException{
		byte[] rawData = rawString.getBytes(UTF8_CODEX);
		byte[] rsacode = cipherSectionProcess(cipherpub , rawData , MAX_ENCRYPT_BLOCK);
		String resdecode64 = Base64.encodeToString(rsacode,Base64.DEFAULT);
		return resdecode64;
	}
	
	
	private static byte[] cipherSectionProcess(Cipher cipher, byte[] rawData, int maxBlockSize)
			throws IllegalBlockSizeException, BadPaddingException, IOException {

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		int inputLength = rawData.length;
		int offset = 0;
		byte[] cache;
		int i = 0;

		while (inputLength - offset > 0) {
			if (inputLength - offset > maxBlockSize) {
				cache = cipher.doFinal(rawData, offset, maxBlockSize);
			} else {
				cache = cipher.doFinal(rawData, offset, inputLength - offset);
			}
			out.write(cache, 0, cache.length);
			i++;
			offset = i * maxBlockSize;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();

		return encryptedData;

	}
	
}
