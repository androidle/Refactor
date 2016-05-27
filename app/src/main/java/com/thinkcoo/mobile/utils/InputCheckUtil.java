package com.thinkcoo.mobile.utils;

import android.text.TextUtils;

import com.thinkcoo.mobile.injector.ActivityScope;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

/**
 * Created by Robert.yao on 2016/3/24.
 */
@ActivityScope
public class InputCheckUtil {

    @Inject
    public InputCheckUtil(){}

    public boolean checkPhoneNumber(String phoneNumber) {

        if (TextUtils.isEmpty(phoneNumber)) {
            return false;
        } else {
            String regex = "^((13[0-9])|(14[0-9])|(15[^4,\\D])|(17[0,5-9])|(18[0-9])|(19[0-9]))\\d{8}$";
            return match(regex, phoneNumber);
        }
    }

    public boolean checkVerifyCode(String verifyCode) {
        return !TextUtils.isEmpty(verifyCode) && TextUtils.isDigitsOnly(verifyCode) && verifyCode.trim().length() == 4;
    }

    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public boolean checkPassword(String password) {
        return false;
    }
}
