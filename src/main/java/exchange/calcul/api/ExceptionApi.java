package exchange.calcul.api;

import exchange.calcul.dto.ErrorResultForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "exchange.calcul.api")
public class ExceptionApi {

    @ExceptionHandler
    public ResponseEntity<ErrorResultForm> validHandler(MethodArgumentNotValidException e){
        log.error("[exceptionHandler] BAD_REQUEST", e);
        ErrorResultForm errorResultForm = new ErrorResultForm(
                false,
                e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return new ResponseEntity<>(errorResultForm, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResultForm> nullHandler(NullPointerException e){
        log.error("[exceptionHandler] BAD_REQUEST", e);
        ErrorResultForm errorResultForm = new ErrorResultForm(
                false,
                e.getMessage());
        return new ResponseEntity<>(errorResultForm, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResultForm> exceptionHandler(Exception e){
        log.error("[exceptionHandler] INTERNAL_SERVER_ERROR", e);
        ErrorResultForm errorResultForm = new ErrorResultForm(
                false,
                e.getMessage());
        return  new ResponseEntity<>(errorResultForm, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
