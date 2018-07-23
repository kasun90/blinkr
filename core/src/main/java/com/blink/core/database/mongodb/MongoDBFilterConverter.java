package com.blink.core.database.mongodb;

import com.blink.core.database.Filter;
import com.blink.core.database.FilterPair;
import com.blink.core.database.SimpleDBObject;
import com.blink.core.database.SortCriteria;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.conversions.Bson;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MongoDBFilterConverter {
    public static Bson toQuery(SimpleDBObject dbObject) {
        if (dbObject.getData().size() == 1) {
            Map.Entry<String, FilterPair> entry = dbObject.getData().entrySet().iterator().next();
            return convertSingleFilter(entry.getKey(), entry.getValue());
        } else {
            return Filters.and(dbObject.getData().entrySet().stream().map(entry -> convertSingleFilter(entry.getKey(), entry.getValue())).collect(Collectors.toList()));
        }
    }

    private static Bson convertSingleFilter(String fieldName, FilterPair pair) {
        Bson bson;
        switch (pair.getFilter()) {
            case EQ:
                bson = Filters.eq(fieldName, pair.getValue());
                break;
            case LT:
                bson = Filters.lt(fieldName, pair.getValue());
                break;
            case LTE:
                bson = Filters.lte(fieldName, pair.getValue());
                break;
            case GT:
                bson = Filters.gt(fieldName, pair.getValue());
                break;
            case GTE:
                bson = Filters.gte(fieldName, pair.getValue());
                break;
            default:
                bson = Filters.eq(fieldName, pair.getValue());
                break;
        }
        return bson;
    }

    public static Bson toSortQuery(SortCriteria... sorts) {
        if (sorts.length == 1) {
            return convertSingleSort(sorts[0]);
        } else {
            return Sorts.orderBy(Arrays.stream(sorts).map(MongoDBFilterConverter::convertSingleSort).collect(Collectors.toList()));
        }
    }

    private static Bson convertSingleSort(SortCriteria sort) {
        return sort.isAscending() ? Sorts.ascending(sort.getFieldName()) : Sorts.descending(sort.getFieldName());
    }
}
