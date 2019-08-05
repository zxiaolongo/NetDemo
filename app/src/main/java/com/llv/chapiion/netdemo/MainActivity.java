package com.llv.chapiion.netdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.llv.chapiion.netdemo.net.HttpUtils;
import com.llv.chapiion.netdemo.net.bean.ServerResponse;
import com.llv.chapiion.netdemo.net.callback.Callback;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    private void testNet() {
        HttpUtils.getInstance().appLogout("dsdsd").enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                super.onResponse(call, response);
                if (issuccess) {

                }

            }
        });
    }
}
