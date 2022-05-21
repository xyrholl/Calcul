package exchange.calcul.exception;

import exchange.calcul.dto.JsonMessage;
import exchange.calcul.dto.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice(basePackages = "exchange.calcul.api")
public class ExceptionApi {

    @ExceptionHandler
    public ResponseEntity<JsonMessage> noSuchHandler(NoSuchElementException e){
        log.error("[exceptionHandler] BAD_REQUEST", e);
        JsonMessage message = new JsonMessage(
                e.getMessage(),
                StatusEnum.BAD_REQUEST
        );
        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler
    public ResponseEntity<JsonMessage> validHandler(MethodArgumentNotValidException e){
        log.error("[exceptionHandler] BAD_REQUEST", e);
        JsonMessage message = new JsonMessage(
            e.getBindingResult().getAllErrors().get(0).getDefaultMessage(),
            StatusEnum.BAD_REQUEST
        );
        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler
    public ResponseEntity<JsonMessage> nullHandler(NullPointerException e){
        log.error("[exceptionHandler] BAD_REQUEST", e);
        JsonMessage message = new JsonMessage(
            e.getMessage(),
            StatusEnum.BAD_REQUEST
        );
        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler
    public ResponseEntity<JsonMessage> apiLayerHandler(ApiLayerException e){
        log.error("[exceptionHandler] INTERNAL_SERVER_ERROR", e);
        JsonMessage message = new JsonMessage(
                e.getMessage(),
                StatusEnum.INTERNAL_SERVER_ERROR
        );
        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler
    public ResponseEntity<JsonMessage> exceptionHandler(Exception e){
        log.error("[exceptionHandler] INTERNAL_SERVER_ERROR", e);
        JsonMessage message = new JsonMessage(
            e.getMessage(),
            StatusEnum.INTERNAL_SERVER_ERROR
        );
        return ResponseEntity.internalServerError().body(message);
    }
}
