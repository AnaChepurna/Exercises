package com.javarush.task.task37.task3709.connectors;

import com.javarush.task.task37.task3709.security.SecurityChecker;
import com.javarush.task.task37.task3709.security.SecurityCheckerImpl;

/**
 * Created by Ana on 16.07.2017.
 */
public class SecurityProxyConnector implements Connector {
    private SimpleConnector original;
    private SecurityChecker checker;

    public SecurityProxyConnector(String string) {
        original = new SimpleConnector(string);
        checker = new SecurityCheckerImpl();
    }

    @Override
    public void connect() {
        if (checker.performSecurityCheck()){
            original.connect();
        }
    }
}
