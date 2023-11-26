package com.t2.cyed.util;

public interface MinPrioritable<T> {
    public T front();

    public T back();

    public boolean isEmpty();

    public void insert(T elem);

    public T extractMin();

    public void decreaseKey(int i, T key);

    public void minHeapify(int i);

    public void heapSort();
}
