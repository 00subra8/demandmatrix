package com.eval.dmatrix.goldtrade.model;

public enum TradeType {
    BUY,
    SELL,
    UNKNOWN;

    public static TradeType fromString(String text) {
        for (TradeType tradeType : TradeType.values()) {
            if (tradeType.name().equalsIgnoreCase(text)) {
                return tradeType;
            }
        }
        return null;
    }
}
