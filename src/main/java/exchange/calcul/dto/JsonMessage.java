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
    private Object data;
    private String message;

    public JsonMessage(String message, StatusEnum status){
        this.message = message;
        this.currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
        this.status = status;
    }

    public JsonMessage(Object data){
        this.status = StatusEnum.OK;
        this.currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
        this.data = data;
    }

    public JsonMessage(Object data, String format){
        String form = format.toLowerCase();
        if(form.equals("full")){
            this.status = StatusEnum.OK;
            this.currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
            this.data = data;
        }else if(form.equals("short")){
            this.status = StatusEnum.OK;
            this.data = data;
        }
    }


    
}
