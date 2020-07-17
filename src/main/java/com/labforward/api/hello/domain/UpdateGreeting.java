package com.labforward.api.hello.domain;

import com.labforward.api.core.validation.Entity;

public class UpdateGreeting  {

    private String oldGreetingMsg;

    private String newGreetingMsg;

    public UpdateGreeting(String oldGreetingMsg, String newGreetingMsg) {
        this.oldGreetingMsg = oldGreetingMsg;
        this.newGreetingMsg = newGreetingMsg;
    }

    public UpdateGreeting() {

    }

    public String getOldGreetingMsg() {
        return oldGreetingMsg;
    }

    public void setOldGreetingMsg(String oldGreetingMsg) {
        this.oldGreetingMsg = oldGreetingMsg;
    }

    public String getNewGreetingMsg() {
        return newGreetingMsg;
    }

    public void setNewGreetingMsg(String newGreetingMsg) {
        this.newGreetingMsg = newGreetingMsg;
    }
}
