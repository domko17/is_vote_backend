package com.tuke.fei.kpi.isvote.modules.auth.security.token;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Getter
@AllArgsConstructor
public enum TokenType {
    ACCESS_TOKEN("AT"),
    REFRESH_TOKEN("RT");

    private static final Map<String, TokenType> map =
            Arrays.stream(values()).collect(toMap(o -> o.abbreviation, o -> o));

    private final String abbreviation;

    @JsonCreator
    public static TokenType deserialize(String value) {
        return map.get(value);
    }

    @JsonValue
    public String serialize() {
        return abbreviation;
    }
}
