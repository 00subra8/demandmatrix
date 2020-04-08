package com.eval.dmatrix.goldtrade.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    @RequestMapping("/")
    public String displayWelcomeMessage() {
        return "Welcome to Demand Matrix Gold Trade Brokers, please use extension /doTrade withh appropriate parameters to complete a trade";
    }

    //todo:add global error mapping
}
