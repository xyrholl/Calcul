package exchange.calcul.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import exchange.calcul.repository.CurrencyRateRepository;
import exchange.calcul.repository.RemittanceRepository;

@SpringBootTest
class DataServiceTest {

    @Autowired private CurrencyRateRepository currencyRateRepository;
    @Autowired private RemittanceRepository remittanceRepository;
    @Autowired private RemittanceService remittanceService;
    @Autowired private ExchangeRateService ExchangeRateService;

//    @Test
//    void api데이터_DB저장데이터_동일() {
//        //given
//        List<CurrencyRate> list = exchangeCompo.currencyRatesExtraction(exchangeCompo.requestCurrencyApi());
//        dataService.currencyRateSave(list);
//        Optional<CurrencyRate> currencyRate = currencyRateRepository.findById(1L);
//        CurrencyRateForm form = new CurrencyRateForm(currencyRate.get());
//
//        //when
//        CurrencyRate findOne = dataService.findOneCurrencyRate(list, form);
//
//        //then
//        assertEquals(findOne, currencyRate.get());
//    }
//
//    @Test
//    void Remittance_수취금_확인(){
//        //given
//        List<CurrencyRate> list = exchangeCompo.currencyRatesExtraction(exchangeCompo.requestCurrencyApi());
//        dataService.currencyRateSave(list);
//        Optional<CurrencyRate> currencyRate = currencyRateRepository.findById(1L);
//        String remittancePrice = "10";
//
//        //when
//        RemittanceForm form = new RemittanceForm("USD", "KRW", "", remittancePrice,"");
//        Remittance remittance = Remittance.createRemittance(form, currencyRate.get());
//
//        //then
//        assertEquals(remittance.getReceptionPrice(), currencyRate.get().getRate()*Double.parseDouble(remittancePrice));
//    }
//
//    @Test
//    void Remittance_예외_확인(){
//        //given
//        List<CurrencyRate> list = exchangeCompo.currencyRatesExtraction(exchangeCompo.requestCurrencyApi());
//        dataService.currencyRateSave(list);
//        Optional<CurrencyRate> currencyRate = currencyRateRepository.findById(1L);
//        String remittancePrice = "0";
//
//        //when
//        RemittanceForm form = new RemittanceForm("USD", "KRW", "", remittancePrice,"");
//        RuntimeException thrown = assertThrows(RuntimeException.class, ()->{
//            Remittance.createRemittance(form, currencyRate.get());
//        });
//
//        //then
//        assertEquals("송금액이 0이하 입니다.", thrown.getMessage());
//    }

}