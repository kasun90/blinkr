package com.blink.core.database.mongodb;

import com.blink.core.database.Filter;
import com.blink.core.database.FilterPair;
import com.blink.core.database.SimpleDBObject;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MongoDBFilterConverter {
    public static Bson fromBlink(SimpleDBObject dbObject) {
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
}
