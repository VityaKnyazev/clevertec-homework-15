package ru.clevertec.ecl.knyazev.controller.exception;

import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.clevertec.ecl.knyazev.data.http.error.ErrorMessage;
import ru.clevertec.ecl.knyazev.repository.exception.RepositoryException;
import ru.clevertec.ecl.knyazev.service.exception.ServiceException;

import java.util.Date;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(value = {JsonPatchException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleJsonPatchException(RuntimeException e) {
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(),
                ErrorMessage.defaultError() + ": " + e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {RepositoryException.class, ServiceException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleLayerException(RuntimeException e) {
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(),
                ErrorMessage.defaultError() + ": " + e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {DataAccessException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleLayerException(DataAccessException e) {
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(),
                ErrorMessage.defaultError() + ": " + e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
