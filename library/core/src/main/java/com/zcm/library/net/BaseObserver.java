package com.zcm.library.net;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(BaseResponse<T> response) {
        if (response.getCode() == 200) {
            onSuccess(response.getNewslist());
        } else {
            onFailure(null, response.getMsg());
        }
    }

    @Override
    public void onError(Throwable e) {
        onFailure(e, RxExceptionUtil.exceptionHandler(e));
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T result);

    public abstract void onFailure(Throwable e, String errorMsg);

}
