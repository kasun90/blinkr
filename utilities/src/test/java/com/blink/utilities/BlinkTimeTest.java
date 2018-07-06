package com.blink.utilities;

import org.junit.Test;

import static org.junit.Assert.*;

public class BlinkTimeTest {

    @Test
    public void longTimeTest() {
        System.out.println(BlinkTime.getCurrentTimeMillis());
    }

    @Test
    public void formatTest() {
        System.out.println(BlinkTime.format(1530871326784L, "yyyy MM dd"));
    }
}