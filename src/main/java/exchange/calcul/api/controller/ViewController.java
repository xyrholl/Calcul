package exchange.calcul.api.controller;

import exchange.calcul.dto.CountryForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ViewController {

    @GetMapping("/")
    public String home(Model model){

        List<CountryForm> list = new ArrayList<>();
        list.add(new CountryForm("한국", "KRW", "한국(KRW)"));
        list.add(new CountryForm("일본", "JPY", "일본(JYP)"));
        list.add(new CountryForm("필리핀", "PHP", "필리핀(PHP)"));
        model.addAttribute("countries", list);
        return "index";
    }
    
}
