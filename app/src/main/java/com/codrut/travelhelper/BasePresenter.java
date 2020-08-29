package com.codrut.travelhelper;

public interface BasePresenter<T> {
    void attachView(T view);

    void detachView();
}
