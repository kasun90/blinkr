package com.blink.atag.tags;

import com.blink.shared.article.ATagType;

import java.util.LinkedList;

public class OrderedList extends List {
    public OrderedList() {
        super(ATagType.ORDERED_LIST);
    }
}
