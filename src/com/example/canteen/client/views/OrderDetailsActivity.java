package com.example.canteen.client.views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import com.example.canteen.R;
import com.example.canteen.service.data.Dish;
import com.example.canteen.service.data.Order;

import java.util.Map;

public class OrderDetailsActivity extends Activity {
    public static final String ORDER_KEY = "order";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.order_details_view);
        Order order = (Order)getIntent().getSerializableExtra(ORDER_KEY);

        ((TextView)findViewById(R.id.code)).setText(order.ID());

        String itemsDescription = "";
        for (Map.Entry<Dish, Integer> item : order.items().entrySet()) {
            itemsDescription = itemsDescription + "- " + item.getValue() + "x " + item.getKey().name() + "\n";
        }
        ((TextView)findViewById(R.id.items)).setText(itemsDescription);

        ((TextView)findViewById(R.id.price)).setText(String.format("$%.2f", order.calculateTotalPrice().doubleValue()));
    }
}