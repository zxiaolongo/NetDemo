package com.llv.chapiion.netdemo.net.callback;

import android.support.annotation.NonNull;

import com.llv.chapiion.netdemo.net.bean.BaseData;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class MyObservable<T extends BaseData> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {
        //开始
    }

    @Override
    public void onNext(T value) {
        if (value == null){
            onFail("服务器响应为空");
            return;
        }
        if (value.getnRes() ==1){
            onSuccess(value);
            return;
        }
        onFail(value.getVcRes());
    }

    @Override
    public void onError(Throwable e) {
        //失败
        onFail("网络异常");
    }

    @Override
    public void onComplete() {
        //结束
    }
    public abstract void onSuccess(@NonNull T t);
    public abstract void onFail(String error);
}
