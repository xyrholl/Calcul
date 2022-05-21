package exchange.calcul.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import exchange.calcul.dto.JsonMessage;
import exchange.calcul.service.RemittanceService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CurrencyRateApi {

    private final RemittanceService remittanceService;

    @GetMapping("/rate/{transCountry}")
    public ResponseEntity<JsonMessage> getRate(@RequestParam(required = false, defaultValue = "short", value = "format") String format,
        @PathVariable("transCountry") String transCountry){
        JsonMessage message = new JsonMessage(remittanceService.getRate(transCountry), format);
        return ResponseEntity.status(HttpStatus.valueOf(message.getStatus().getCode()))
                            .body(message);
    }

    @GetMapping("/healthCheck")
    public ResponseEntity<JsonMessage> healthCheck(@RequestParam(required = false, defaultValue = "short", value = "format") String format){
        JsonMessage message = new JsonMessage(null, format);
        return ResponseEntity.status(HttpStatus.valueOf(message.getStatus().getCode()))
                            .body(message);
    }
    
}
