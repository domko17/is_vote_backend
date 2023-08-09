package com.tuke.fei.kpi.isvote.modules;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

    private final String errorMsg;
    private final FaultType faultType;

    public ApiException(FaultType faultType, String errorMsg) {
        super(errorMsg);
        this.faultType = faultType;
        this.errorMsg = errorMsg;
    }

    @Getter
    @AllArgsConstructor
    public enum FaultType {
        GENERAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
        DB_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
        ACTION_NOT_ALLOWED(HttpStatus.UNAUTHORIZED),
        ACCESS_DENIED(HttpStatus.FORBIDDEN),
        WRONG_CREDENTIALS(HttpStatus.BAD_REQUEST),
        WRONG_REFRESH_TOKEN(HttpStatus.BAD_REQUEST),
        INVALID_PARAMS(HttpStatus.BAD_REQUEST),
        INVALID_PASSWORD(HttpStatus.BAD_REQUEST),
        OBJECT_NOT_FOUND(HttpStatus.BAD_REQUEST),
        OBJECT_ALREADY_EXISTS(HttpStatus.BAD_REQUEST),
        EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST),
        USER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST);

        private final HttpStatus httpStatus;
    }

    public static ApiException createUserNotLoggedIn() {
        return new ApiException(FaultType.ACTION_NOT_ALLOWED, "User is not logged in.");
    }

    public static ApiException createUserAlreadyExists() {
        return new ApiException(FaultType.USER_ALREADY_EXISTS, "User with given email already exists.");
    }

    public static ApiException createObjectNotFound(String errorMsg) {
        return new ApiException(FaultType.OBJECT_NOT_FOUND, errorMsg);
    }

    public static ApiException createObjectNotFound() {
        return createObjectNotFound("Object not found in DB");
    }
}
