package com.thmotaun.market.request_process_chain;

import com.thmotaun.market.Request;

public abstract class ProcessChain {

    private ProcessChain    nextProcess = null;

    public abstract void execute(Request request);

    public ProcessChain getNextProcess() {
        return nextProcess;
    }

    public void setNextProcess(ProcessChain nextProcess) {
        this.nextProcess = nextProcess;
    }

}
