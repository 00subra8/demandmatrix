package com.eval.dmatrix.goldtrade.configuration;


import com.eval.dmatrix.goldtrade.dao.GoldTradeDAO;
import com.eval.dmatrix.goldtrade.model.TradeState;
import com.eval.dmatrix.goldtrade.service.InputValidatorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class GoldTradeConfiguration {

    @Bean
    public InputValidatorService inputValidatorService() {
        return new InputValidatorService();
    }

    @Bean
    public GoldTradeDAO goldTradeDAO() {
        return new GoldTradeDAO();
    }


}

