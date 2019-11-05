package com.llv.chapiion.netdemo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.llv.chapiion.netdemo.net.HttpUtils;
import com.llv.chapiion.netdemo.net.bean.ServerResponse;
import com.llv.chapiion.netdemo.net.callback.MyCallBack;
import com.llv.chapiion.netdemo.net.callback.MyObservable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    private void testNet() {
        HttpUtils.getInstance().appLogout("dsdsd").enqueue(new MyCallBack<ServerResponse>() {
            @Override
            public void onFail(String errer) {
            }

            @Override
            public void onSuccess(@NonNull ServerResponse serverResponse) {
            }
        });

        HttpUtils.getInstance().appLogoutB("tokenId")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObservable<ServerResponse>() {
                    @Override
                    public void onSuccess(@NonNull ServerResponse serverResponse) {

                    }

                    @Override
                    public void onFail(String error) {

                    }
                });
    }
}
