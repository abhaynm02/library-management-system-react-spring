package com.abhaynm.library_management.exceptions.globalexceptions;


import com.abhaynm.library_management.dto.ResponseModel;
import com.abhaynm.library_management.exceptions.coustomexceptions.BookAddingFailedException;
import com.abhaynm.library_management.exceptions.coustomexceptions.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
    @ExceptionHandler(BookAddingFailedException.class)
    public ResponseEntity<ResponseModel<?>>handleBookAddingFailedException(BookAddingFailedException ex){
        ResponseModel<?>responseModel=new ResponseModel<>("failure",ex.getMessage(),null);
        return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ResponseModel<?>>handleBookNotFoundException(BookNotFoundException ex){
        ResponseModel<?>responseModel=new ResponseModel<>("failure",ex.getMessage(),null);
        return new ResponseEntity<>(responseModel,HttpStatus.NOT_FOUND);
    }
}
