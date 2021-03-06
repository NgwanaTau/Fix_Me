package com.thmotaun.market.request_process_chain;

import com.thmotaun.market.Request;
import com.thmotaun.market.RequestValidation;

public class ValidationProcess extends ProcessChain {

    @Override
    public void execute(Request request) {
        String  requestType;

        if (RequestValidation.isListMarkets(request.getRequest())) {
            requestType = Request.LIST_MARKETS;
        }
        else if (RequestValidation.isListMarket(request.getRequest())) {
            requestType = Request.LIST_MARKET_ID;
        }
        else if (RequestValidation.isBuyOrder(request.getRequest())) {
            requestType = Request.BUY_ORDER;
        }
        else if (RequestValidation.isSellOrder(request.getRequest())) {
            requestType = Request.SELL_ORDER;
        }
        else {
            requestType = Request.UNKNOWN;
        }
        request.setRequestType(requestType);
        if (this.getNextProcess() != null)
            this.getNextProcess().execute(request);
    }

}
