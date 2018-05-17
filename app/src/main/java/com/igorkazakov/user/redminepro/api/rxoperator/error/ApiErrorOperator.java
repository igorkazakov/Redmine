package com.igorkazakov.user.redminepro.api.rxoperator.error;

import com.igorkazakov.user.redminepro.api.ApiException;

import java.io.IOException;

import io.reactivex.ObservableOperator;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.HttpException;

public final class ApiErrorOperator<T> implements ObservableOperator<T, T> {
    private static final String TAG = "ApiErrorOperator";

    @Override
    public Observer<? super T> apply(Observer<? super T> observer) throws Exception {
        return new Observer<T>() {
            @Override
            public void onSubscribe(Disposable d) {
                observer.onSubscribe(d);
            }

            @Override
            public void onNext(T value) {
                observer.onNext(value);
            }

            @Override
            public void onError(Throwable e) {

                if (e instanceof HttpException) {

                    HttpException error = (HttpException) e;
                    Response response = error.response();
                    ApiException exception = new ApiException(response.message(), response.code());

                    observer.onError(exception);


                } else if (e instanceof IOException) {
                    ApiException exception = new ApiException("No Network Connection Error", 1);
                    observer.onError(exception);

                } else {
                    observer.onError(new ApiException(e.getLocalizedMessage(), 2));
                }
            }

            @Override
            public void onComplete() {
                observer.onComplete();
            }
        };
    }
}