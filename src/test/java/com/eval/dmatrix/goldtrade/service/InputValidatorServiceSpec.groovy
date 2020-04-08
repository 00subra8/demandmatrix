package com.eval.dmatrix.goldtrade.service

import com.eval.dmatrix.goldtrade.model.TradeType
import org.slf4j.Logger
import spock.lang.Specification
import spock.lang.Unroll

class InputValidatorServiceSpec extends Specification {

    private InputValidatorService unit

    void setup() {
        unit = new InputValidatorService()
        unit.logger = Mock(Logger)
    }

    @Unroll("For trade type #tradeType expected result is #result")
    def "Validate Trade type"(TradeType tradeType, boolean result) {

        expect:
        unit.validateTradeType(tradeType) == result

        where:
        tradeType         | result
        null              | false
        TradeType.BUY     | true
        TradeType.SELL    | true
        TradeType.UNKNOWN | false
    }

    @Unroll("For price #price expected result is #result")
    def "Validate price"(int price, boolean result) {

        expect:
        unit.validatePrice(price) == result

        where:
        price | result
        0     | false
        500   | false
        501   | true
        1999  | true
        2000  | false
    }

    @Unroll("For quantity #quantity expected result is #result")
    def "Validate Quantity"(int quantity, boolean result) {

        expect:
        unit.validQuantity(quantity) == result

        where:
        quantity | result
        0        | false
        1        | true
        25       | true
        26       | false
    }


}
