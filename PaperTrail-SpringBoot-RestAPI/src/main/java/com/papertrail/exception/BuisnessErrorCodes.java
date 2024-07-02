package com.papertrail.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum BuisnessErrorCodes {

    NO_CODE(0, HttpStatus.NOT_IMPLEMENTED, "No code Not Implemented"),
    INCORRECT_CURRENT_PASSWORD(300, HttpStatus.BAD_REQUEST, "Incorrect current password"),
    NEW_PASSWORD_DOES_NOT_MATCH(301, HttpStatus.BAD_REQUEST, "New password does not match"),
    ACCOUNT_LOCKED(302,HttpStatus.FORBIDDEN, "User account is locked"),
    ACCOUNT_DISABLED(303,HttpStatus.FORBIDDEN, "User account is disabled"),
    BAD_CREDENTIALS(304,HttpStatus.FORBIDDEN, "Username or password is incorrect"),
    USERNAME_ALREADY_EXISTS(305,HttpStatus.CONFLICT, "Username already exists try with different username"),
    JWT_TOKEN_EXPIRED(306,HttpStatus.UNAUTHORIZED, "JWT Token has expired"),
    ;

    @Getter
    private final int code;

    @Getter
    private final String description;

    @Getter
    private final HttpStatus httpStatus;

    BuisnessErrorCodes(int code, HttpStatus httpStatus, String description ) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
