package com.tone.coast.movie.util;


import com.tone.coast.movie.model.entity.DataResult;

import org.jsoup.HttpStatusException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observable;


public class HttpExceptionApi {

    //未知错误
    private static final int ERROR_UNKNOWN = 1000;
    //解析错误
    private static final int ERROR_PARSE = 1001;
    //网络错误
    private static final int ERROR_NETWORK = 1002;
    //网络连接超时
    private static final int ERROR_TIMEOUT = 1003;

    public static <T> Observable<DataResult<T>> handleException(Throwable throwable) {
        DataResult<T> resultEntity = new DataResult();
        if (throwable instanceof HttpStatusException) {
            HttpStatusException httpException = (HttpStatusException) throwable;
            int code = httpException.getStatusCode();
            String msg = httpException.getMessage();
            resultEntity.code = code;
            resultEntity.msg = msg;
        } else if (throwable instanceof ConnectException
                || throwable instanceof UnknownHostException) {
            resultEntity.code = ERROR_NETWORK;
            resultEntity.msg = "请查看网络连接情况";
        } else if (throwable instanceof SocketTimeoutException) {
            resultEntity.code = ERROR_TIMEOUT;
            resultEntity.msg = "网络超时";
        } else {
            resultEntity.code = ERROR_UNKNOWN;
            resultEntity.msg = "未知错误";
        }

        return Observable.just(resultEntity);
    }
}
