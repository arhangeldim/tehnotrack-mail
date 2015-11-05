package ru.mail.track.reflection;

/**
 *
 */
public class Manual implements Gear {
    private int count;

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void setGear(int gear) {

    }

    @Override
    public void nextGear() {

    }

    @Override
    public String toString() {
        return "Manual{" +
                "count=" + count +
                '}';
    }
}
