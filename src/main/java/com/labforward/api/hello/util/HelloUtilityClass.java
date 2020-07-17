package com.labforward.api.hello.util;

import com.labforward.api.hello.domain.Greeting;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class HelloUtilityClass {
    public boolean checkIfGreetingExists(String oldGreetingMsg, Map<String,Greeting> map)
    {
        if(map.size()==0 || map.values()
                                 .stream()
                .anyMatch(e -> e.getMessage().equals(oldGreetingMsg)))
            return  true;
        else
            return false;
    }
}
