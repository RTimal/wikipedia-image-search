package com.rajievtimal.wikipediaimagesearch.base;

public interface ServiceCallback<T> {
    void finishedLoading(T t, String error);
}
