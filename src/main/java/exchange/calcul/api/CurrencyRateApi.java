package exchange.calcul.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import exchange.calcul.dto.JsonMessage;
import exchange.calcul.service.RemittanceService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class CurrencyRateApi {

    private final RemittanceService remittanceService;

    @GetMapping("/rate/{transCountry}")
    public ResponseEntity<JsonMessage> getRate(@PathVariable("transCountry") String transCountry){
        JsonMessage message = new JsonMessage(remittanceService.getRate(transCountry));
        return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus().getCode()));
    }

    @GetMapping("/test")
    public ResponseEntity<JsonMessage> healthcheck(@RequestParam("format") String format){
        JsonMessage message = new JsonMessage(null, format);
        return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus().getCode()));
    }
    
}
