package util;

import java.util.ArrayList;

public class MinPriorityQueue<T extends Comparable<T>> implements MinPrioritable<T> {

    private final ArrayList<T> elements;
    private int heapSize;

    public MinPriorityQueue() {
        elements = new ArrayList<>();
        heapSize = 0;
    }

    public ArrayList<T> getElements() {
        return elements;
    }

    @Override
    public T front() {
        if (!isEmpty())
            return elements.get(0);
        else
            return null;
    }

    @Override
    public T back() {
        if (!isEmpty())
            return elements.get(elements.size() - 1);
        else
            return null;
    }

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    @Override
    public void insert(T elem) {
        elements.add(elem);
        this.heapSize = elements.size();
        if (!isEmpty()) {
            for (int i = (this.heapSize / 2) - 1; i >= 0; i--) {
                this.heapSize = elements.size() - 1;
                minHeapify(i);
            }
        }
    }

    @Override
    public T extractMin() {
        int last = elements.size() - 1;
        if (!isEmpty()) {
            T max = elements.get(0);
            elements.set(0, elements.get(last));
            elements.remove(last);
            this.heapSize = elements.size() - 1;
            minHeapify(0);
            return max;
        } else {
            return null;
        }
    }


    public void increaseKey(int i, T key) {
        if (key.compareTo(elements.get(i)) > 0) {
            elements.set(i, key);
            i++;
            while (i > 0 && elements.get((i / 2) - 1).compareTo(elements.get(i - 1)) < 0) {
                T temp = elements.get(i - 1);
                elements.set(i - 1, elements.get((i / 2) - 1));
                elements.set((i / 2) - 1, temp);
                i = (i / 2) - 1;
            }
        }
    }

    @Override
    public void decreaseKey(int i, T value) {
        if (value.compareTo(elements.get(i)) < 0) {
            elements.set(i, value);
            i++;
            while (i > 0 && elements.get((i / 2) - 1).compareTo(elements.get(i - 1)) > 0) {
                T temp = elements.get(i - 1);
                elements.set(i - 1, elements.get((i / 2) - 1));
                elements.set((i / 2) - 1, temp);
                i = (i / 2) - 1;
            }
        }

    }

    @Override
    public void minHeapify(int i) {
        i++;
        int l = (2 * i) - 1;
        int r = 2 * i;
        i--;
        int shortest = i;
        if (l <= heapSize && elements.get(l).compareTo(elements.get(i)) < 0) {
            shortest = l;
        }
        if (r <= heapSize && elements.get(r).compareTo(elements.get(shortest)) < 0) {
            shortest = r;
        }
        if (shortest != i) {
            T temp = elements.get(i);
            elements.set(i, elements.get(shortest));
            elements.set(shortest, temp);
            minHeapify(shortest);
        }
    }

    @Override
    public void heapSort() {
        this.heapSize = elements.size() - 1;
        for (int i = this.heapSize; i >= 1; i--) {
            T temp = elements.get(0);
            elements.set(0, elements.get(i));
            elements.set(i, temp);
            this.heapSize--;
            minHeapify(0);
        }
    }

    public int getHeapSize() {
        return elements.size() - 1;
    }

    public T getElem(int i) {
        return elements.get(i);
    }

    public T getElem(T elem) {
        for (T element : elements) {
            if (element.equals(elem)) {
                return element;
            }
        }
        return null;
    }

}

