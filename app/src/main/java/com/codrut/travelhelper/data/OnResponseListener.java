package com.codrut.travelhelper.data;

public interface OnResponseListener<T> {
    void onSuccess(T data);

    void onFailure(Exception e);
}
