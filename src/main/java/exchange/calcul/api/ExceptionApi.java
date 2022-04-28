package exchange.calcul.api;

import exchange.calcul.dto.ErrorResult;
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
    public ResponseEntity<ErrorResult> validHandler(MethodArgumentNotValidException e){
        log.error("[exceptionHandler] BAD_REQUEST", e);
        ErrorResult errorResult = new ErrorResult(
                false,
                e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> nullHandler(NullPointerException e){
        log.error("[exceptionHandler] BAD_REQUEST", e);
        ErrorResult errorResult = new ErrorResult(
                false,
                e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> exceptionHandler(Exception e){
        log.error("[exceptionHandler] INTERNAL_SERVER_ERROR", e);
        ErrorResult errorResult = new ErrorResult(
                false,
                e.getMessage());
        return  new ResponseEntity<>(errorResult, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
