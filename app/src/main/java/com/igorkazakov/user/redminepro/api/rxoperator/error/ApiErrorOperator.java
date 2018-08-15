package com.igorkazakov.user.redminepro.api.rxoperator.error;

import com.igorkazakov.user.redminepro.api.ApiException;

import java.io.IOException;

import io.reactivex.ObservableOperator;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOperator;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.HttpException;

public final class ApiErrorOperator<T> implements ObservableOperator<T, T>, SingleOperator<T, T> {

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

                handleError(e, (CompositeObservable) observer);
            }

            @Override
            public void onComplete() {
                observer.onComplete();
            }
        };
    }

    @Override
    public SingleObserver<? super T> apply(SingleObserver<? super T> observer) throws Exception {
        return new SingleObserver<T>() {
            @Override
            public void onSubscribe(Disposable d) {
                observer.onSubscribe(d);
            }

            @Override
            public void onSuccess(T t) {
                observer.onSuccess(t);
            }

            @Override
            public void onError(Throwable e) {
                handleError(e, (CompositeObservable) observer);
            }
        };
    }

    private void handleError(Throwable e, CompositeObservable observer) {

        if (e instanceof HttpException) {

            HttpException error = (HttpException) e;
            Response response = error.response();
            ApiException exception = new ApiException(response.message(), response.code());

            observer.onError(exception);

        } else if (e instanceof IOException) {
            ApiException exception = new ApiException("No Network Connection", 1);
            observer.onError(exception);

        } else {
            observer.onError(new ApiException(e.getLocalizedMessage(), 2));
        }
    }

    private interface CompositeObservable extends Observer, SingleObserver {

    }
}