package com.tuke.fei.kpi.isvote.modules.common.enums;


import lombok.Getter;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public enum UserType {

    ADMIN(1, "Administrator", "ADMIN"),
    VOTER(2, "Hlasujúci", "VOTER"),
    VOTING_ADMIN(3,"Správca hlasovania","VOTING_ADMIN");

    private static final Map<String, UserType> userTypeNameMap = Arrays.stream(UserType.values())
            .collect(Collectors.toMap(UserType::getName, userType -> userType));

    private final Integer id;
    private final String name;
    private final String code;

    UserType(Integer id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public static UserType getUserTypeByUsername(String username) {
        return userTypeNameMap.get(username);
    }

    public static class UserTypeConverter implements AttributeConverter<UserType, Integer> {

        private final Map<Integer, UserType> userTypeMap;

        public UserTypeConverter() {
            userTypeMap = Arrays.stream(UserType.values()).collect(Collectors.toMap(UserType::getId, userType -> userType));
        }

        @Override
        public Integer convertToDatabaseColumn(UserType attribute) {
            return attribute.id;
        }

        @Override
        public UserType convertToEntityAttribute(Integer dbData) {
            return Optional.ofNullable(userTypeMap.get(dbData))
                    .orElseThrow(() -> new IllegalArgumentException("UserType with id='" + dbData + "' not defined!"));
        }
    }
}
