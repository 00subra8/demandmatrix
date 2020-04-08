package com.eval.dmatrix.goldtrade.dao;

import com.eval.dmatrix.goldtrade.exception.GoldTradeDAOException;
import com.eval.dmatrix.goldtrade.model.Trade;
import com.eval.dmatrix.goldtrade.model.TradeState;
import com.eval.dmatrix.goldtrade.model.TradeType;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GoldTradeDAO {

    //Using internal data structure instead of in-memory DB
    private List<TradeState> tradeStates = new ArrayList<>();
    private int tradeId = 1;

    //defensive copy to maintain integrity of DAO data
    public List<TradeState> getTradeStates() {
        return Optional.of(tradeStates)
                .map(List::stream)
                .orElseGet(Stream::empty)
                .collect(Collectors.toList());
    }


    public void addTrade(Trade trade) {
        if (trade == null) {
            throw new GoldTradeDAOException("Trade not received");
        }

        processTradeStates(trade);

    }

    private void processTradeStates(Trade trade) {
        if (CollectionUtils.isEmpty(tradeStates)) {
            addCurrentTrade(trade);
        } else {
            if (trade.getTradeType() == TradeType.BUY) {
                processBuyTrade(trade);
            } else if (trade.getTradeType() == TradeType.SELL) {
                processSellTrade(trade);
            }

        }
        sortTradeStates();
    }


    private void processBuyTrade(Trade buyTrade) {
        List<TradeState> sellTrades = getAllSellTrades();

        List<TradeState> aptSellStates = CollectionUtils.emptyIfNull(sellTrades).stream()
                .filter(sellTrade -> sellTrade.getTrade().getPrice() <= buyTrade.getPrice())
                .collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(aptSellStates)) {
            aptSellStates.forEach(currentSellState -> {
                int newBuyTradeQuantity = buyTrade.getQuantity() - currentSellState.getTrade().getQuantity();
                newBuyTradeQuantity = newBuyTradeQuantity > 0 ? newBuyTradeQuantity : 0;

                int newSellTradeQuantity = currentSellState.getTrade().getQuantity() - buyTrade.getQuantity();
                newSellTradeQuantity = newSellTradeQuantity > 0 ? newSellTradeQuantity : 0;

                buyTrade.setQuantity(newBuyTradeQuantity);

                removeFromTradeStates(currentSellState.getTradeId());

                addModifiedState(currentSellState, newSellTradeQuantity, TradeType.SELL);

            });

        }
        addCurrentTrade(buyTrade);
    }

    private void processSellTrade(Trade sellTrade) {
        List<TradeState> buyTrades = getAllBuyTrades();

        List<TradeState> aptBuyStates = CollectionUtils.emptyIfNull(buyTrades).stream()
                .filter(buyTrade -> buyTrade.getTrade().getPrice() >= sellTrade.getPrice())
                .collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(aptBuyStates)) {
            aptBuyStates.forEach(currentSellState -> {
                int newSellTradeQuantity = sellTrade.getQuantity() - currentSellState.getTrade().getQuantity();
                newSellTradeQuantity = newSellTradeQuantity > 0 ? newSellTradeQuantity : 0;

                int newBuyTradeQuantity = currentSellState.getTrade().getQuantity() - sellTrade.getQuantity();
                newBuyTradeQuantity = newBuyTradeQuantity > 0 ? newBuyTradeQuantity : 0;

                sellTrade.setQuantity(newSellTradeQuantity);

                removeFromTradeStates(currentSellState.getTradeId());

                addModifiedState(currentSellState, newBuyTradeQuantity, TradeType.BUY);

            });

        }
        addCurrentTrade(sellTrade);
    }

    private void addModifiedState(TradeState currentSellState, int newSellTradeQuantity, TradeType tradeType) {
        TradeState tradeState = new TradeState();
        Trade trade = new Trade();
        trade.setQuantity(newSellTradeQuantity);
        trade.setTradeType(tradeType);
        trade.setPrice(currentSellState.getTrade().getPrice());
        trade.setOrderTimeStamp(currentSellState.getTrade().getOrderTimeStamp());
        tradeState.setTradeId(currentSellState.getTradeId());
        tradeState.setTrade(trade);
        tradeStates.add(tradeState);
    }


    private void removeFromTradeStates(int tradeId) {
        tradeStates = tradeStates.stream()
                .filter(currentTradeState -> currentTradeState.getTradeId() != tradeId)
                .collect(Collectors.toList());

    }


    private void addCurrentTrade(Trade trade) {
        TradeState tradeState = new TradeState();
        tradeState.setTradeId(tradeId++);
        tradeState.setTrade(trade);
        tradeStates.add(tradeState);
    }


    private List<TradeState> getAllSellTrades() {
        return tradeStates.stream()
                .filter(this::isaSellTrade)
                .collect(Collectors.toList());
    }

    private List<TradeState> getAllBuyTrades() {
        return tradeStates.stream()
                .filter(this::isaBuyTrade)
                .collect(Collectors.toList());
    }

    private boolean isaBuyTrade(TradeState tradeState) {
        return tradeState.getTrade().getTradeType() == TradeType.BUY;
    }

    private boolean isaSellTrade(TradeState tradeState) {
        return tradeState.getTrade().getTradeType() == TradeType.SELL;
    }

    private void sortTradeStates() {
        tradeStates = tradeStates.stream()
                .sorted(Comparator.comparingInt(TradeState::getTradeId))
                .collect(Collectors.toList());
    }


}

