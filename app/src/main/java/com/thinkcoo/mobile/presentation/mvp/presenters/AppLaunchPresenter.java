package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.domain.UseCaseRuntimeTrack;
import com.thinkcoo.mobile.domain.account.GetLoggedAccountUseCase;
import com.thinkcoo.mobile.domain.applaunch.CompatOldVersionDbUseCase;
import com.thinkcoo.mobile.domain.applaunch.CopyDataDictionaryDbFileUseCase;
import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.model.entity.Account;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.WelcomeView;
import com.thinkcoo.mobile.utils.log.ThinkcooLog;
import javax.inject.Inject;
import rx.Subscriber;

/**
 * Created by robert on 2016/5/22.
 */
@ActivityScope
public class AppLaunchPresenter extends MvpBasePresenter<MvpView>{

    private static  String TAG = "AppLaunchPresenter";

    CopyDataDictionaryDbFileUseCase copyDataDictionaryDbFileUseCase;
    CompatOldVersionDbUseCase compatOldVersionDbUseCase;
    GetLoggedAccountUseCase getLoggedAccountUseCase;
    ErrorMessageFactory errorMessageFactory;
    UseCaseRuntimeTrack mUseCaseRuntimeTrack;

    @Inject
    public AppLaunchPresenter(
            CopyDataDictionaryDbFileUseCase copyDataDictionaryDbFileUseCase,
            CompatOldVersionDbUseCase compatOldVersionDbUseCase,
            ErrorMessageFactory errorMessageFactory,
            GetLoggedAccountUseCase getLoggedAccountUseCase,
            UseCaseRuntimeTrack useCaseRuntimeTrack
    ) {

        this.copyDataDictionaryDbFileUseCase = copyDataDictionaryDbFileUseCase;
        this.compatOldVersionDbUseCase = compatOldVersionDbUseCase;
        this.errorMessageFactory = errorMessageFactory;
        this.getLoggedAccountUseCase = getLoggedAccountUseCase;
        this.mUseCaseRuntimeTrack = useCaseRuntimeTrack;
    }

    public void appLaunch() {
        if (!isViewAttached()){
            return;
        }
        ThinkcooLog.d(TAG,"=== 开始执行兼容老版本数据用例 ===");
        mUseCaseRuntimeTrack.firstUseCaseStart();
        compatOldVersionDbUseCase.execute(compatOldVersionDbSub);
    }

    private Subscriber copyDataDictionaryDbFileSub = new Subscriber<Void>() {
        @Override
        public void onCompleted() {
            ThinkcooLog.d(TAG,"=== 执行拷贝字典数据库用例完成 ===");
            ThinkcooLog.d(TAG,"=== 开始执行获取上次登录账户用例 ===");
            mUseCaseRuntimeTrack.lastUseCaseEnd();
            getLoggedAccountUseCase.execute(getLoggedAccountSub,mUseCaseRuntimeTrack.getWelcomePageHoldMillisecond());
        }

        @Override
        public void onError(Throwable e) {
            showToastByThrowableAndFinishView(e);
        }

        @Override
        public void onNext(Void b) {
        }
    };

    private WelcomeView getWelcomeView() {
        return (WelcomeView)getView();
    }


    private Subscriber  compatOldVersionDbSub = new Subscriber<Boolean>() {
        @Override
        public void onCompleted() {
            ThinkcooLog.d(TAG,"=== 执行兼容老版本数据用例完成 ===");
            ThinkcooLog.d(TAG,"=== 开始执行拷贝外部数据库用例 ===");
            copyDataDictionaryDbFileUseCase.execute(copyDataDictionaryDbFileSub);
        }

        @Override
        public void onError(Throwable e) {
            showToastByThrowableAndFinishView(e);
        }

        @Override
        public void onNext(Boolean b) {
        }
    };


    private Subscriber getLoggedAccountSub = new Subscriber<Account>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(Account account) {
            if (!isViewAttached()){
                return;
            }
            ThinkcooLog.d(TAG,"=== 开始根据获得账号跳转界面 ===");
            getWelcomeView().gotoNextPageByAccount(account);
        }
    };

    private void showToastByThrowableAndFinishView(Throwable e){
        if (!isViewAttached()){
            return;
        }
        getWelcomeView().showToast(errorMessageFactory.createStringResId(e));
        getWelcomeView().closeSelf();
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        copyDataDictionaryDbFileUseCase.unSubscribe();
        compatOldVersionDbUseCase.unSubscribe();
        getLoggedAccountUseCase.unSubscribe();
    }
}
