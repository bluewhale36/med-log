package com.bluewhale.medlog.appuser.controller;

import com.bluewhale.medlog.appuser.dto.AppUserSignInDTO;
import com.bluewhale.medlog.appuser.service.AppUserService;
import com.bluewhale.medlog.appuser.enums.Gender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/appuser")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("/login")
    public String login() {
        return "appuser/login";
    }

    @GetMapping("/signin")
    public String signin(Model model) {
        model.addAttribute("genderList", Gender.values());
        return "appuser/signin";
    }

    @PostMapping("/signin")
    public String registerNewUser(@ModelAttribute AppUserSignInDTO dto) {
        System.out.println(dto);
        appUserService.registerNewUser(dto);
        return "redirect:/";
    }


}
