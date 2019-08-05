package com.llv.chapiion.netdemo.net.callback;

import com.llv.chapiion.netdemo.net.client.Logger;

import retrofit2.Call;
import retrofit2.Response;
//可用可不用 可按需求自定义
public class Callback<T> implements retrofit2.Callback<T> {
    public boolean issuccess = true;
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
//        DialogShow.closeDialog();
        if(response.code()!=200){
//            ToastUtils.showToast(AppContext.getInstance().getContext(), "网络状态异常");
            issuccess = false;
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Logger.getLogger().e(t);
//        DialogShow.closeDialog();
//        ToastUtils.showToast(AppContext.getInstance().getContext(), "网络连接异常");
    }
}

