package exchange.calcul.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exchange.calcul.service.exchangeCompo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(value = exchangeCompo.class)
class exchangeCompoTest {

    @Autowired private MockRestServiceServer mockServer;
    @Autowired private exchangeCompo exchangeCompo;
    @Autowired private ObjectMapper objectMapper;

    @Value("${evn.accessKey}")
    private String key;
    private String req_url = "http://apilayer.net/api/live?currencies=KRW,JPY,PHP,&source=USD&format=1";

    @Test
    void currencyApi_호출_성공() {

        try {
            //given
            Map<String, Object> mockMap = new HashMap<>();
            mockMap.put("success", "true");
            String expectResult = objectMapper.writeValueAsString(mockMap);

            req_url += "&access_key="+ key;
            mockServer.expect(requestTo(req_url)).andRespond(withSuccess(expectResult, MediaType.APPLICATION_JSON));

            //when
            HashMap apiMap =  exchangeCompo.requestCurrencyApi();

            //then
            assertEquals(apiMap.get("success"), mockMap.get("success"));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}