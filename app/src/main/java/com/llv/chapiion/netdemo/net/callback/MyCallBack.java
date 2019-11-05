package com.llv.chapiion.netdemo.net.callback;

import android.support.annotation.NonNull;

import com.llv.chapiion.netdemo.net.bean.BaseData;

import retrofit2.Callback;

public abstract class MyCallBack<T extends BaseData> implements Callback<T> {
    @Override
    public void onResponse(retrofit2.Call<T> call, retrofit2.Response<T> response) {
        if (response.code() == 200){
            onFail("网络异常");
            return;
        }
        if (response.body() == null){
            onFail("服务器响应为空");
            return;
        }
        if (response.body().getnRes() == 1){
            onSuccess(response.body());
            return;
        }
        onFail(response.body().getVcRes());
    }

    @Override
    public void onFailure(retrofit2.Call<T> call, Throwable t) {
        onFail("网络异常");
    }
    public abstract void onFail(String errer);
    public abstract void onSuccess(@NonNull T t);

}
