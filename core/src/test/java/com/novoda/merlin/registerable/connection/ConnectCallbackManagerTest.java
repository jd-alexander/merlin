package com.novoda.merlin.registerable.connection;

import com.novoda.merlin.registerable.Register;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ConnectCallbackManagerTest {

    private Register<Connectable> connectables;

    private ConnectCallbackManager connectCallbackManager;

    @Before
    public void setUp() {
        connectables = new Register<>();
        connectCallbackManager = new ConnectCallbackManager(connectables);
    }

    @Test
    public void givenRegisteredConnectable_whenCallingOnConnect_thenCallsConnectForConnectable() {
        Connectable connectable = givenRegisteredConnectable();

        connectCallbackManager.onConnect();

        verify(connectable).onConnect();
    }

    @Test
    public void givenMultipleRegisteredConnectables_whenCallingOnConnect_thenCallsConnectForAllConnectables() {
        List<Connectable> connectables = givenMultipleRegisteredConnectables();

        connectCallbackManager.onConnect();

        for (Connectable connectable : connectables) {
            verify(connectable).onConnect();
        }
    }

    private List<Connectable> givenMultipleRegisteredConnectables() {
        List<Connectable> connectables = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Connectable connectable = mock(Connectable.class);
            connectables.add(connectable);
            this.connectables.register(connectable);
        }
        return connectables;
    }

    private Connectable givenRegisteredConnectable() {
        Connectable connectable = mock(Connectable.class);
        connectables.register(connectable);
        return connectable;
    }

}
