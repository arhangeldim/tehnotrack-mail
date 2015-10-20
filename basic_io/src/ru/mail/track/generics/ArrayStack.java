package ru.mail.track.generics;

import java.util.Collection;

/**
 *
 */
public class ArrayStack<E> implements Stack<E> {
    private Object[] elements;
    private int capacity;
    private int size;

    public ArrayStack(int capacity) {
        this.capacity = capacity;
        size = 0;
        elements = new Object[capacity];
    }

    @Override
    public void push(E element) throws StackException {
        if (isFull()) {
            throw new StackException("Stack overflow");
        }
        elements[size] = element;
        size++;
    }

    @Override
    public boolean isFull() {
        return size == capacity;
    }

    @Override
    public E pop() throws StackException {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void pushAll(Collection<E> src) throws StackException {

    }

    @Override
    public void popAll(Collection<E> dst) throws StackException {

    }
}
