package com.rajievtimal.wikipediaimagesearch.base;

public interface ServiceCallback<T> {
    //TODO: Add error field later when handling API errors
    void finishedLoading(T t, String error);
}
