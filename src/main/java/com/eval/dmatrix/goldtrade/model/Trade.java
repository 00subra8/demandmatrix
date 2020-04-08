package com.eval.dmatrix.goldtrade.model;

import lombok.Data;

@Data
public class Trade {

    private TradeType tradeType;
    private int price;
    private int quantity;
    //accepting as timestamp to ease json input
    private String orderTimeStamp;

}
