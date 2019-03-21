package com.blink.utilities;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import static org.junit.Assert.*;

public class BlinkJSONTest {

    @Test
    public void prettyJSONTest() {
        System.out.println(BlinkJSON.toPrettyJSON(new Person("Kasun", 28, "Sample")));
    }
}