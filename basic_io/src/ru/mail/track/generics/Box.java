package ru.mail.track.generics;

/**
 *
 */
public class Box<T> {
    private T item;

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
        // Object - nothing more
    }
}
