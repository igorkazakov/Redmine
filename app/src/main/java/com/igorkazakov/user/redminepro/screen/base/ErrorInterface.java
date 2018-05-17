package com.igorkazakov.user.redminepro.screen.base;

import com.igorkazakov.user.redminepro.api.ApiException;

public interface ErrorInterface {
    void showError(ApiException e);
}
