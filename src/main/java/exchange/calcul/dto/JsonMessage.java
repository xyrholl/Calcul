package exchange.calcul.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonMessage {

    private StatusEnum status;
    private String currentTime;
    private String data;

    public JsonMessage(String format){
        String form = format.toLowerCase();
        if(form.equals("full")){
            this.status = StatusEnum.OK;
            this.currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
        }else if(form.equals("short")){
            this.status = StatusEnum.OK;
        }else{
            this.status = StatusEnum.BAD_REQUEST;
        }
    }
    
}
