package com.example.acceptance.base;


/**
 * 描述：
 */

public interface IPresenter<V extends IView> {
    void detachView();
}
