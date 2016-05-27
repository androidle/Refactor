package com.thinkcoo.mobile;

import android.app.Application;
import android.widget.Toast;
import com.thinkcoo.mobile.injector.components.ApplicationComponent;
import com.thinkcoo.mobile.model.rest.ApiFactory;
import com.thinkcoo.mobile.utils.log.ThinkcooLog;

/**
 * Created by Administrator on 2016/3/16.
 */
public class ThinkcooApp extends Application{

    public static final String TAG = "ThinkcooApp";

    private ThinkcooAppInitDelegate mThinkcooAppInitDelegate;

    @Override
    public void onCreate() {
        super.onCreate();
        appInit();
    }
    private void appInit() {
        Toast.makeText(this,"appInit",Toast.LENGTH_SHORT).show();
        ThinkcooLog.d(TAG,"appInit");
        mThinkcooAppInitDelegate = new ThinkcooAppInitDelegate(this);
        mThinkcooAppInitDelegate.init();
    }
    public ApplicationComponent getApplicationComponent() {
        return mThinkcooAppInitDelegate.getmApplicationComponent();
    }
    public ApiFactory getApiFactory() {
        return mThinkcooAppInitDelegate.getApiFactory();
    }


}