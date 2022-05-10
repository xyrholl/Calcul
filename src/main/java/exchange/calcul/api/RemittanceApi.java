package exchange.calcul.api;

import exchange.calcul.dto.CurrencyRateForm;
import exchange.calcul.dto.JsonMessage;
import exchange.calcul.dto.RemittanceForm;
import exchange.calcul.service.RemittanceService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RemittanceApi {

    private final RemittanceService remittanceService;

    @PostMapping("/currencyRate")
    public ResponseEntity<JsonMessage> currencyRate(@RequestParam(required = false, defaultValue = "short", value = "format") String format,
        @RequestBody CurrencyRateForm changeCurrency){
        JsonMessage message = new JsonMessage(remittanceService.reqCurrencyRateForm(changeCurrency), format);
        return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus().getCode()));
    }

    @PostMapping("/remittance")
    public RemittanceForm remittance(@Validated @RequestBody RemittanceForm form){
        return remittanceService.reqRemittance(form);
    }

}
