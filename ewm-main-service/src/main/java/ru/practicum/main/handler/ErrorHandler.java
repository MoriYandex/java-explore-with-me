package ru.practicum.main.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.exception.ValidationException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice("ru.practicum.main")
public class ErrorHandler {
    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleBadRequest(ConflictException e) {
        String message = e.getMessage();
        log.error(e.getMessage());
        return ErrorResponse.builder()
                .message(message)
                .status(HttpStatus.CONFLICT.toString())
                .reason("For the requested operation the conditions are not met")
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(MethodArgumentNotValidException e) {
        String message = e.getMessage();
        log.error(e.getMessage());
        return ErrorResponse.builder()
                .errors(e.getBindingResult().getAllErrors().stream()
                        .map(err -> String.format("Field: %s. Message: %s", ((FieldError) err).getField(),
                                err.getDefaultMessage()
                        )).collect(Collectors.toList()))
                .message(message)
                .status(HttpStatus.BAD_REQUEST.toString())
                .reason("Field validation error")
                .build();
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(ValidationException e) {
        String message = e.getMessage();
        log.error(e.getMessage());
        return ErrorResponse.builder()
                .message(message)
                .status(HttpStatus.BAD_REQUEST.toString())
                .reason("Field validation error")
                .build();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(MissingServletRequestParameterException e) {
        String message = e.getMessage();
        log.error(e.getMessage());
        return ErrorResponse.builder()
                .message(message)
                .status(HttpStatus.BAD_REQUEST.toString())
                .reason("Field validation error")
                .build();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleBadRequest(DataIntegrityViolationException e) {
        String message = e.getMessage();
        log.error(e.getMessage());
        return ErrorResponse.builder()
                .message(message)
                .status(HttpStatus.CONFLICT.toString())
                .reason("Integrity constraint has been violated.")
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException e) {
        String message = e.getMessage();
        log.error(e.getMessage());
        return ErrorResponse.builder()
                .message(message)
                .status(HttpStatus.NOT_FOUND.toString())
                .reason("The required object was not found.")
                .build();
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInternalServerError(Throwable e) {
        String message = e.getMessage();
        log.error(message);
        log.error(String.valueOf(e.getClass()));
        return ErrorResponse.builder()
                .errors(Collections.singletonList(getStackTrace(e)))
                .message(message)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .reason("Server error")
                .build();
    }

    private String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }

    @Getter
    @Setter
    @Builder
    private static class ErrorResponse {
        /**
         * Список стектрейсов или описания ошибок
         */
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Builder.Default
        private List<String> errors = new ArrayList<>();

        /**
         * Сообщение об ошибке
         */
        private String message;

        /**
         * Общее описание причины ошибки
         */
        private String reason;

        /**
         * Код статуса HTTP-ответа
         */
        private String status;

        /**
         * Дата и время когда произошла ошибка
         */
        @Builder.Default
        private LocalDateTime timestamp = LocalDateTime.now();
    }
}
