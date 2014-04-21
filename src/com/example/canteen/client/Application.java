package com.example.canteen.client;

import android.os.Handler;
import com.example.canteen.client.api.Client;
import com.example.canteen.service.Facade;
import com.example.canteen.service.TimeService;
import com.example.canteen.service.impl.Canteen;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class Application extends android.app.Application {
    private Facade _server;
    private Client _client;

    @Override public void onCreate() {
        super.onCreate();

        _server = new Canteen(new TimeService() {
            private Handler _handler = new Handler();

            @Override
            public Date now() {
                return new Date(System.currentTimeMillis());
            }

            @Override
            public void schedule(Date when, Runnable action) {
                _handler.postDelayed(action, when.getTime() - now().getTime());
            }

            @Override
            public void cancel(Runnable action) {
                _handler.removeCallbacks(action);
            }
        });
    }

    public Client getClient() {
        if (null == _client) {
            _client = new Client(_server);
        }
        return _client;
    }
}
