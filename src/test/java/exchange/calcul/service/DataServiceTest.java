package exchange.calcul.service;

import exchange.calcul.domain.CurrencyRate;
import exchange.calcul.domain.Remittance;
import exchange.calcul.dto.CurrencyRateForm;
import exchange.calcul.dto.RemittanceForm;
import exchange.calcul.repository.RemittanceRepository;
import exchange.calcul.util.ApiConnect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class DataServiceTest {

    @Autowired private CurrencyRateRepository currencyRateRepository;
    @Autowired private RemittanceRepository remittanceRepository;
    @Autowired private DataService dataService;
    @Autowired private ApiConnect apiConnect;

    @Test
    void api데이터_DB저장데이터_동일() {
        //given
        List<CurrencyRate> list = apiConnect.currencyRatesExtraction(apiConnect.requestCurrencyApi());
        dataService.currencyRateSave(list);
        Optional<CurrencyRate> currencyRate = currencyRateRepository.findById(1L);
        CurrencyRateForm form = new CurrencyRateForm(currencyRate.get());

        //when
        CurrencyRate findOne = dataService.findOneCurrencyRate(list, form);

        //then
        assertEquals(findOne, currencyRate.get());
    }

    @Test
    void Remittance_수취금_확인(){
        //given
        List<CurrencyRate> list = apiConnect.currencyRatesExtraction(apiConnect.requestCurrencyApi());
        dataService.currencyRateSave(list);
        Optional<CurrencyRate> currencyRate = currencyRateRepository.findById(1L);
        String remittancePrice = "10";

        //when
        RemittanceForm form = new RemittanceForm("USD", "KRW", "", remittancePrice,"");
        Remittance remittance = Remittance.createRemittance(form, currencyRate.get());

        //then
        assertEquals(remittance.getReceptionPrice(), currencyRate.get().getRate()*Double.parseDouble(remittancePrice));
    }

    @Test
    void Remittance_예외_확인(){
        //given
        List<CurrencyRate> list = apiConnect.currencyRatesExtraction(apiConnect.requestCurrencyApi());
        dataService.currencyRateSave(list);
        Optional<CurrencyRate> currencyRate = currencyRateRepository.findById(1L);
        String remittancePrice = "0";

        //when
        RemittanceForm form = new RemittanceForm("USD", "KRW", "", remittancePrice,"");
        RuntimeException thrown = assertThrows(RuntimeException.class, ()->{
            Remittance.createRemittance(form, currencyRate.get());
        });

        //then
        assertEquals("송금액이 0이하 입니다.", thrown.getMessage());
    }

}