package exchange.calcul.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResultForm {

    private boolean success;
    private String message;

}
