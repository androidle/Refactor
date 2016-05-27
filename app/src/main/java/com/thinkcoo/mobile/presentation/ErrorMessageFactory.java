package com.thinkcoo.mobile.presentation;

import android.accounts.NetworkErrorException;
import android.content.Context;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.exception.account.LicenseNotAgreeException;
import com.thinkcoo.mobile.model.exception.account.PhoneNumberOrPasswordErrorException;
import javax.inject.Inject;

public class ErrorMessageFactory {

    private Context applicationContext;

    public String create(Throwable e) {
        return  getString(createStringResId(e));
    }

    public int createStringResId(Throwable e){

        int message = R.string.default_error_msg;
        if (e instanceof NetworkErrorException) {
            message = R.string.net_no;
        }else if( e instanceof PhoneNumberOrPasswordErrorException){
            message = R.string.errTips_phone_pws_error;
        }else if (e instanceof LicenseNotAgreeException){
            message = R.string.license_must_agree;
        }
        return message;
    }
    @Inject
    public ErrorMessageFactory(Context context) {
        applicationContext = context.getApplicationContext();
    }
    public Context getContext(){
        return applicationContext;
    }
    private String getString(int stringResId){
        return  applicationContext.getString(stringResId);
    }

}
