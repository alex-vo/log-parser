package com.ef;

import com.google.inject.Guice;
import com.google.inject.Injector;

public abstract class IntegrationTest {

    private static Injector injector;

    protected static void setUpInjector(Object instance) {
        if (injector == null) {
            AccessLogParserModule module = new AccessLogParserModule();
            injector = Guice.createInjector(module);
        }

        injector.injectMembers(instance);
    }

}
