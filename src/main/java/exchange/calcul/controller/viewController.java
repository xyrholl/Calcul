package exchange.calcul.controller;

import exchange.calcul.dto.CountryDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class viewController {

    @GetMapping("/")
    public String home(Model model){
        List<CountryDto> list = new ArrayList<>();
        model.addAttribute("countries", list);
        return "index";
    }
    
}
