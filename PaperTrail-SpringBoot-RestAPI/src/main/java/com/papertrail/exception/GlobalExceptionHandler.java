package com.papertrail.exception;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.mail.MessagingException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.HashSet;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException exp) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .buisnessErrorCode(BuisnessErrorCodes.ACCOUNT_LOCKED.getCode())
                                .buisnessErrorDescription(BuisnessErrorCodes.ACCOUNT_LOCKED.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException exp) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .buisnessErrorCode(BuisnessErrorCodes.ACCOUNT_DISABLED.getCode())
                                .buisnessErrorDescription(BuisnessErrorCodes.ACCOUNT_DISABLED.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException exp) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .buisnessErrorCode(BuisnessErrorCodes.BAD_CREDENTIALS.getCode())
                                .buisnessErrorDescription(BuisnessErrorCodes.BAD_CREDENTIALS.getDescription())
                                .error(BuisnessErrorCodes.BAD_CREDENTIALS.getDescription())
                                .build()
                );
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleException(MessagingException exp) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(OperationNotPermittedException.class)
    public ResponseEntity<ExceptionResponse> handleException(OperationNotPermittedException exp) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException exp) {
        Set<String> errors = new HashSet<>();
        exp.getBindingResult().getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .validationErrors(errors)
                                .build()
                );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponse> handleException(DataIntegrityViolationException exp) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .buisnessErrorCode(BuisnessErrorCodes.USERNAME_ALREADY_EXISTS.getCode())
                                .buisnessErrorDescription(BuisnessErrorCodes.USERNAME_ALREADY_EXISTS.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(OtpInvalidException.class)
    public ResponseEntity<ExceptionResponse> handleException(OtpInvalidException exp) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .buisnessErrorDescription(exp.getMessage())
                                .error("Invalid OTP, Please try again")
                                .build()
                );
    }

    @ExceptionHandler(OtpExpiredException.class)
    public ResponseEntity<ExceptionResponse> handleException(OtpExpiredException exp) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .buisnessErrorDescription("OTP has expired")
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ExceptionResponse> handleException(ExpiredJwtException exp) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .buisnessErrorDescription(BuisnessErrorCodes.JWT_TOKEN_EXPIRED.getDescription())
                                .buisnessErrorCode(BuisnessErrorCodes.JWT_TOKEN_EXPIRED.getCode())
                                .error(exp.getMessage())
                                .build()
                );
    }
//    @ExceptionHandler(MissingServletRequestPartException.class)
//    public ResponseEntity<ExceptionResponse> handleException(MissingServletRequestPartException exp) {
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST) // todo book cover not uploaded error fix
//                .body(
//                        ExceptionResponse.builder()
//                                .buisnessErrorDescription(BuisnessErrorCodes.JWT_TOKEN_EXPIRED.getDescription())
//                                .buisnessErrorCode(BuisnessErrorCodes.JWT_TOKEN_EXPIRED.getCode())
//                                .error(exp.getMessage())
//                                .build()
//                );
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exp) {
        // todo: log the exception
        System.out.println("Handled Exception occurred in the application ❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌ ");
        exp.printStackTrace();
        System.out.println("Handled Exception occurred in the application ❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌ ");
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .buisnessErrorDescription("Internal Server Error, Please contact support")
                                .error(exp.getMessage())
                                .build()
                );
    }

}
