package exchange.calcul.exception;

import exchange.calcul.dto.JsonMessage;
import exchange.calcul.dto.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class RestApiException {

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

    @ExceptionHandler ResponseEntity<JsonMessage> methodNotHandler(HttpRequestMethodNotSupportedException e){
        log.info("[exceptionHandler] METHOD_NOT_ALLOWED", e);
        JsonMessage message = new JsonMessage(
                e.getMessage(),
                StatusEnum.METHOD_NOT_ALLOWED
        );
        return ResponseEntity.status(405).body(message);
    }

    @ExceptionHandler ResponseEntity<JsonMessage> notFoundHandler(NoHandlerFoundException e){
        log.info("[exceptionHandler] NOT_FOUND_EXCEPTION", e);
        JsonMessage message = new JsonMessage(
                e.getMessage(),
                StatusEnum.NOT_FOUND
        );
        return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus().getCode()));
    }

//    @ExceptionHandler
//    public ResponseEntity<JsonMessage> exceptionHandler(Exception e){
//        log.error("[exceptionHandler] INTERNAL_SERVER_ERROR", e);
//        JsonMessage message = new JsonMessage(
//            e.getMessage(),
//            StatusEnum.INTERNAL_SERVER_ERROR
//        );
//        return ResponseEntity.internalServerError().body(message);
//    }

}
