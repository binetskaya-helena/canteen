package com.example.canteen.client;

import com.example.canteen.client.api.Client;
import com.example.canteen.service.Facade;
import com.example.canteen.service.impl.DemoFacade;

public class Application extends android.app.Application {
    private Facade _server;
    private Client _client;

    @Override public void onCreate() {
        super.onCreate();

        _server = new DemoFacade();
    }

    public Client getClient() {
        if (null == _client) {
            _client = new Client(_server);
        }
        return _client;
    }
}
