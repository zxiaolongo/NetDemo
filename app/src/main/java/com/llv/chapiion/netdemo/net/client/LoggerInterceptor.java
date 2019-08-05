package com.llv.chapiion.netdemo.net.client;

import android.text.TextUtils;
import android.util.Log;


import com.llv.chapiion.netdemo.net.client.Logger;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/*
 * okHttp相关日志
 */
class LoggerInterceptor implements Interceptor {

    private Logger logger = Logger.getLogger();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response;
        Request request = chain.request();
        long startNs = System.nanoTime();
        try {
            response = chain.proceed(request);

            // notice:  以下是自己添加的如果后台返回SessionTimeOut就进入登陆界面--额外添加3处
            ResponseBody responseBody = response.body();
            long contentLength = responseBody.contentLength();
            if (!bodyEncoded(response.headers())) {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();
                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    try {
                        charset = contentType.charset(UTF8);
                    } catch (UnsupportedCharsetException e) {
                        return response;
                    }
                }
                if (!isPlaintext(buffer)) {
                    return response;
                }

                if (contentLength != 0) {
                    String result = buffer.clone().readString(charset);
//                            Log.e("tag", " response.url():"+ response.request().url());
                    Log.e("http", " response.body():" + result);
                    if ("{\"status\":\"sessionTimeOut\"}".equals(result)){
// 这里是进入登陆界面                       AppContext.goToLoginActivity();
                    }
                }
            }
        } catch (Exception e) {
            logForRequest(request, e);
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        return logForResponse(response, tookMs);
    }

    // notice:  额外添加1
    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
    // notice:  额外添加2
    private static final Charset UTF8 = Charset.forName("UTF-8");
    // notice:  额外添加3
    private static boolean isPlaintext(Buffer buffer) throws EOFException {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }


    private void logForRequest(Request request, Exception e) {
        Request clone = request.newBuilder().build();
        String formBody = getFromBody(clone);
        String requestString = request.url().toString();
        if (!TextUtils.isEmpty(formBody)) {
            requestString += "?" + formBody;
        }
        Headers headers = request.headers();
        String tokenId = headers.get("tokenId");
        logger.e(requestString, e);
    }

    private Response logForResponse(Response response, long tookMs) {
        try {
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            String formBody = getFromBody(clone.request());

            String responseString = tookMs + "ms " + clone.code();
            if (!TextUtils.isEmpty(clone.message())) {
                responseString += " " + clone.message();
            }
            responseString += " " + clone.request().url().toString();
            if (!TextUtils.isEmpty(formBody)) {
                responseString += "?" + formBody;
            }
            logger.i(clone.request().method(), responseString);
            String tokenId = clone.request().header("tokenId");
            logger.i("header", tokenId);
            ResponseBody body = clone.body();
            if (body != null) {
                MediaType mediaType = body.contentType();
                if (mediaType != null) {
                    String resp = body.string();
                    if (isPlaintext(mediaType)) {
                        logger.i("responseBody", resp);
                    } else {
                        logger.i("requestBody's content", "maybe [file part] , too large too print , ignored!");
                    }
                    body = ResponseBody.create(mediaType, resp);
                    return response.newBuilder().body(body).build();
                }
            }
        } catch (Exception e) {
            logger.e(e);
        }
        return response;
    }

    private String getFromBody(final Request request) {
        String args = "";
        Request clone = request.newBuilder().build();
        Buffer buffer = new Buffer();
        RequestBody body = clone.body();
        if (body == null) return args;
        MediaType mediaType = body.contentType();
        if (isPlaintext(mediaType) && body instanceof FormBody) {
            try {
                body.writeTo(buffer);
            } catch (IOException e) {
                logger.e(e);
            }
            args = buffer.readUtf8();
        }
        return args;
    }

    private static boolean isPlaintext(MediaType mediaType) {
        if (mediaType == null) return false;
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        String subtype = mediaType.subtype();
        if (subtype != null) {
            subtype = subtype.toLowerCase();
            if (subtype.contains("x-www-form-urlencoded") ||
                    subtype.contains("json") ||
                    subtype.contains("xml") ||
                    subtype.contains("html"))
                return true;
        }
        return false;
    }
}