package com.example.canteen.client.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.canteen.R;
import com.example.canteen.client.Application;
import com.example.canteen.client.api.Client;
import com.example.canteen.service.Facade;
import com.example.canteen.service.data.Dish;
import com.example.canteen.service.data.Order;
import com.example.canteen.service.data.PublishingDetails;

import java.math.BigDecimal;

public class MenuActivity extends CanteenActivity {
    private Client _client;
    private Order _order;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _client = ((Application)getApplication()).getClient();
        setContentView(R.layout.menu_view);

        performRequest(new Runnable() {
            @Override
            public void run() {
                PublishingDetails menu = _client.getCurrentMenu();
                // todo: display the menu
            }
        });

        _order = new Order();
        _order.addItem(new Dish("Potatoes", "Delicious potatoes", new BigDecimal(3.95)), 2);
        _order.addItem(new Dish("Chicken", "Yong chicken", new BigDecimal(4.50)), 4);

        ((Button)findViewById(R.id.order)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickOrderNow();
            }
        });
    }

    private void onClickOrderNow() {
        performRequest(new Runnable() {
            @Override
            public void run() {
                Order orderDetails = _client.submitOrder(_order);

                Intent intent = new Intent(MenuActivity.this, OrderDetailsActivity.class);
                intent.putExtra(OrderDetailsActivity.ORDER_KEY, orderDetails);
                startActivity(intent);
            }
        });
    }
}