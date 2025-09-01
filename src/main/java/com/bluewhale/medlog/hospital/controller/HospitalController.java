package com.bluewhale.medlog.hospital.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hospital")
public class HospitalController {

    @GetMapping("")
    public String hospital() {
        return "hospital/main";
    }
}
