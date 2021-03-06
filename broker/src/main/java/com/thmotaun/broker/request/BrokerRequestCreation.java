package com.thmotaun.broker.request;

import java.io.IOException;

import com.thmotaun.broker.BrokerData;
import com.thmotaun.broker.validation.BrokerInputValidation;
import com.thmotaun.core.FixMsg;
import com.thmotaun.core.FixMsgFactory;
import com.thmotaun.core.FixMsgOrder;

public class BrokerRequestCreation {

    private String                  brokerInput;
    private static final String     ERROR_TYPE_MSG = "Error: Invalid input.";
    private static final String     USAGE_MSG = "Broker Commands:" + System.lineSeparator()
                                                + "list markets - lists market IDs of all available markets." + System.lineSeparator()
                                                + "list market_ID - lists details of market with specified ID." + System.lineSeparator()
                                                + "buy|sell qty ticker_symbol market_ID - place buy or sell order." + System.lineSeparator();
    private static final String     ERROR_MSG = ERROR_TYPE_MSG + System.lineSeparator()
            + USAGE_MSG;

    public BrokerRequestCreation(String brokerInput) {
        this.brokerInput = brokerInput;
    }

    public String createBrokerRequest() throws IOException {
        String  brokerRequest;

        if (!BrokerInputValidation.isValidStr(brokerInput))
            throw new IOException(ERROR_MSG);

        if (BrokerInputValidation.isListMarkets(brokerInput)) {
            brokerRequest = BrokerData.getBrokerId() + " " + brokerInput;
            return (brokerRequest);
        }
        else if (BrokerInputValidation.isListMarket(brokerInput)) {
            brokerRequest = BrokerData.getBrokerId() + " " + brokerInput;
            return (brokerRequest);
        }
        else if (BrokerInputValidation.isBuyOrder(brokerInput) || BrokerInputValidation.isSellOrder(brokerInput)) {
            String[]        tokens;
            String          brokerId;
            String          marketId;
            String          cliOrdId;
            String          handlInst;
            String          tickerSymbol;
            String          side;
            String          quantityStr;
            int             quantity;
            String          orderType;
            FixMsgOrder     fixMsgOrder;

            tokens = brokerInput.split(" ");
            brokerId = BrokerData.getBrokerId();
            marketId = tokens[3];
            cliOrdId = BrokerData.getCliOrdId();
            handlInst = FixMsg.AUTO_EXEC_PRIVATE_NO_BROKER;
            tickerSymbol = tokens[2];
            if (BrokerInputValidation.isBuyOrder(brokerInput))
                side = FixMsg.BUY;
            else
                side = FixMsg.SELL;
            quantityStr = tokens[1];
            quantity = Integer.parseInt(quantityStr);
            orderType = FixMsg.MARKET_ORDER_TYPE;
            fixMsgOrder = FixMsgFactory.newOrderMsg(brokerId, marketId, cliOrdId, handlInst, tickerSymbol, side, quantity, orderType);
            fixMsgOrder.setFixMsgOrder();
            brokerRequest = fixMsgOrder.getFixMsgOrder();
            return (brokerRequest);
        }
        else if (BrokerInputValidation.isQuit(brokerInput)) {
            brokerRequest = brokerInput;
            return (brokerRequest);
        }
        else {
            throw new IOException(ERROR_MSG);
        }

    }

}
