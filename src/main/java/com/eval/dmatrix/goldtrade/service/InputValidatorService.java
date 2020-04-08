package com.eval.dmatrix.goldtrade.service;

import com.eval.dmatrix.goldtrade.model.TradeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputValidatorService {

    private Logger logger = LoggerFactory.getLogger(InputValidatorService.class);


    public boolean validateTradeType(TradeType tradeType) {
        if (tradeType == null) {
            logger.error("tradeType is blank - input validation fail");
            return false;
        }

        if (tradeType == TradeType.UNKNOWN) {
            logger.error("tradeType is invalid: " + tradeType + " - input validation fail");
            return false;
        }

        return true;

    }

    public boolean validatePrice(int price) {
        if (price >= 2000 || price <= 500) {
            logger.error("As per fairtrade policy price quoted should be between 500 and 2000 please.");
            return false;
        }

        return true;
    }


    public boolean validQuantity(int quantity) {
        if (quantity > 25 || quantity < 1) {
            logger.error("As per fairtrade policy quantity should be between 1 and 25 please.");
            return false;
        }

        return true;
    }
}
