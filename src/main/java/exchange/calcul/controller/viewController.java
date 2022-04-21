package exchange.calcul.controller;

import exchange.calcul.dto.CountryDto;
import exchange.calcul.dto.RemittanceForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class viewController {

    @GetMapping("/")
    public String home(Model model){

        List<CountryDto> list = new ArrayList<>();
        list.add(new CountryDto("미국", "USD", "미국(USD)"));
        list.add(new CountryDto("한국", "KRW", "한국(KRW)"));
        list.add(new CountryDto("일본", "JYP", "일본(JYP)"));
        list.add(new CountryDto("필리핀", "PHP", "필리핀(PHP)"));
        model.addAttribute("countries", list);
        return "index";
    }

    @PostMapping("/remittance")
    public String remittance(@Valid RemittanceForm form){
        log.info("from :::: " + form.toString());
        return "redirect:/";
    }

    
}
