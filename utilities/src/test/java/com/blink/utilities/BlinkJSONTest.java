package com.blink.utilities;

import org.junit.Test;

import static org.junit.Assert.*;

public class BlinkJSONTest {

    @Test
    public void prettyJSONTest() {
        System.out.println(BlinkJSON.toPrettyJSON(new Person("Kasun", 28)));
    }
}