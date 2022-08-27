package lazarus.restfulapi.library.controller;

import lazarus.restfulapi.library.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class ErrorController {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorInfo handleResourceNotFoundException(ResourceNotFoundException exception) {
        return ErrorInfo.builder()
                .errorType(ErrorInfo.ErrorType.RESOURCE_NOT_FOUND)
                .resourceType(exception.getResourceType())
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(UniqueViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorInfo handleUniqueViolationException(UniqueViolationException exception) {
        return ErrorInfo.builder()
                .errorType(ErrorInfo.ErrorType.UNIQUE_VIOLATION)
                .resourceType(exception.getResourceType())
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(PasswordsDontMatchException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorInfo handlePasswordsDontMatchException(PasswordsDontMatchException exception) {
        return ErrorInfo.builder()
                .errorType(ErrorInfo.ErrorType.PASSWORDS_DONT_MATCH)
                .resourceType(exception.getResourceType())
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(InvalidRoleException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorInfo handleInvalidRoleException(InvalidRoleException exception) {
        return ErrorInfo.builder()
                .errorType(ErrorInfo.ErrorType.INVALID_ROLE)
                .resourceType(exception.getResourceType())
                .message(exception.getMessage())
                .build();
    }
}