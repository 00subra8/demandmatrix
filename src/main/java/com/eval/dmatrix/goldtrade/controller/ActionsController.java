package com.eval.dmatrix.goldtrade.controller;

import com.eval.dmatrix.goldtrade.configuration.GoldTradeConfiguration;
import com.eval.dmatrix.goldtrade.dao.GoldTradeDAO;
import com.eval.dmatrix.goldtrade.exception.GoldTradeInputException;
import com.eval.dmatrix.goldtrade.model.Trade;
import com.eval.dmatrix.goldtrade.model.TradeState;
import com.eval.dmatrix.goldtrade.service.InputValidatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Import({GoldTradeConfiguration.class, GoldTradeDAO.class})
public class ActionsController {

    private Logger logger = LoggerFactory.getLogger(ActionsController.class);

    @Autowired
    private InputValidatorService inputValidatorService;

    @Autowired
    private GoldTradeDAO goldTradeDAO;

    @RequestMapping(method = RequestMethod.POST, value = "/doTrade")
    public List<TradeState> doTrade(@RequestBody Trade trade) {

        if (trade == null) {
            logAndThrowGoldTradeInputException("No Trade Input received");
        }
        if (!inputValidatorService.validateTradeType(trade.getTradeType())) {
            logAndThrowGoldTradeInputException("Invalid tradeType: " + trade.getTradeType());
        }
        if (!inputValidatorService.validatePrice(trade.getPrice())) {
            logAndThrowGoldTradeInputException("Quoted price invalid: " + trade.getPrice());
        }
        if (!inputValidatorService.validQuantity(trade.getQuantity())) {
            logAndThrowGoldTradeInputException("Invalid quantity: " + trade.getQuantity());
        }

        goldTradeDAO.addTrade(trade);

        return goldTradeDAO.getTradeStates();

    }

    @RequestMapping(method = RequestMethod.GET, value = "/getState")
    public List<TradeState> getState() {
        return goldTradeDAO.getTradeStates();
    }

    private void logAndThrowGoldTradeInputException(String inputExceptionMessage) {
        logger.error(inputExceptionMessage);
        throw new GoldTradeInputException(inputExceptionMessage);
    }

}
