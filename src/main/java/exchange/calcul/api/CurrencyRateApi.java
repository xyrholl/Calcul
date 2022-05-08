package exchange.calcul.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import exchange.calcul.dto.CurrencyRateForm;
import exchange.calcul.dto.JsonMessage;
import exchange.calcul.service.RemittanceService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class CurrencyRateApi {

    private final RemittanceService remittanceService;

    @GetMapping("/rate/{transCountry}")
    public CurrencyRateForm getRate(@PathVariable("transCountry") String transCountry){
        return remittanceService.getRate(transCountry);
    }

    @GetMapping("/test")
    public ResponseEntity<JsonMessage> healthcheck(@RequestParam("format") String format){
        JsonMessage message = new JsonMessage(format);
        return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus().getCode()));
    }
    
}
