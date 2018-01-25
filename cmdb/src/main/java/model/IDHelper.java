package model;

import java.util.concurrent.atomic.AtomicInteger;

public class IDHelper {
	private final static AtomicInteger c = new AtomicInteger(0);
    public static int getID() {
        return c.incrementAndGet();
    }
}
