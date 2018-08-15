package com.igorkazakov.user.redminepro.utils;

import com.igorkazakov.user.redminepro.api.rxoperator.error.ApiErrorOperator;

public class RxUtils {

    public static <T> ApiErrorOperator<T> getApiErrorTransformer() {

        return new ApiErrorOperator<>();
    }
}
