package Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.DuplicateMemberException;
import spring.MemberRegisterService;
import spring.RegisterRequest;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private MemberRegisterService memberRegisterService;

    public void setMemberRegisterService(
        MemberRegisterService memberRegisterService) {

        this.memberRegisterService = memberRegisterService;
    }

    @RequestMapping("/step1")
    public String handleStep1() {
        return "register/step1";
    }


    @PostMapping("/step2")
    public String handleStep2(
        @RequestParam(value = "agree", defaultValue = "false") Boolean agree,
        Model model) {
        if (agree == false)
        {
            return "register/step1";
        }

        RegisterRequest r = new RegisterRequest();
        r.setName("Name");

        model.addAttribute("registerRequest", r);
        return "register/step2";
    }

    @GetMapping("/step2")
    public String handleStep2Get() {
        return "redirect:/register/step1";
    }

    @PostMapping("/step3")
    public String handleStep3(RegisterRequest registerRequest) {
        try {
            memberRegisterService.regist(registerRequest);
            return "register/step3";
        } catch (DuplicateMemberException ex) {
            return "register/step2";
        }
    }
}