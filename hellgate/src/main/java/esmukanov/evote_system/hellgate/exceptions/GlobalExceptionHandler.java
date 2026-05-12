package esmukanov.evote_system.hellgate.exceptions;

import esmukanov.evote_system.blockchain.exceptions.BlockchainVerificationException;
import esmukanov.evote_system.election_management.exceptions.*;
import esmukanov.evote_system.hellgate.models.response.ApiErrorResponse;
import esmukanov.evote_system.user_management.exceptions.UserAlreadyExistsException;
import esmukanov.evote_system.user_management.exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ApiErrorResponse handleBadCredentials(BadCredentialsException exception,
                                                 HttpServletRequest request) {
        return new ApiErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "UNAUTHORIZED",
                "Invalid login or password",
                request.getRequestURI(),
                Instant.now()
        );
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(DisabledException.class)
    public ApiErrorResponse handleDisabled(DisabledException exception,
                                           HttpServletRequest request) {
        return new ApiErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "UNAUTHORIZED",
                "User account is disabled",
                request.getRequestURI(),
                Instant.now()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErrorResponse handleValidation(MethodArgumentNotValidException exception,
                                             HttpServletRequest request) {
        return new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "BAD_REQUEST",
                "Validation error",
                request.getRequestURI(),
                Instant.now()
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            UserNotFoundException.class,
            ElectionNotFoundException.class,
            ElectionOptionNotFoundException.class
    })
    public ApiErrorResponse handleNotFound(RuntimeException exception,
                                           HttpServletRequest request) {
        return new ApiErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "NOT_FOUND",
                exception.getMessage(),
                request.getRequestURI(),
                Instant.now()
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({
            UserAlreadyExistsException.class,
            VoteAlreadyExistsException.class
    })
    public ApiErrorResponse handleConflict(RuntimeException exception,
                                           HttpServletRequest request) {
        return new ApiErrorResponse(
                HttpStatus.CONFLICT.value(),
                "CONFLICT",
                exception.getMessage(),
                request.getRequestURI(),
                Instant.now()
        );
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({
            VoteNotAllowedException.class,
            ElectionResultNotAvailableException.class,
            ElectionResultCalculateException.class,
            BlockchainVerificationException.class
    })
    public ApiErrorResponse handleBusinessError(RuntimeException exception,
                                                HttpServletRequest request) {
        return new ApiErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "UNPROCESSABLE_ENTITY",
                exception.getMessage(),
                request.getRequestURI(),
                Instant.now()
        );
    }
}
