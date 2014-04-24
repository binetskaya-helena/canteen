package com.example.canteen.client.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import com.example.canteen.R;
import com.example.canteen.client.Application;
import com.example.canteen.client.api.Client;
import com.example.canteen.service.data.Dish;
import com.example.canteen.service.data.Order;
import com.example.canteen.service.data.PublishingDetails;

public class MenuActivity extends CanteenActivity {
    private Client _client;
    private Order _orderBuilder;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _client = ((Application)getApplication()).getClient();
        _orderBuilder = new Order();

        setContentView(R.layout.menu_view);

        ((Button)findViewById(R.id.order)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickOrderNow();
            }
        });

        updateMenu();
    }

    public void updateMenu() {
        performRequest(new Runnable() {
            @Override
            public void run() {
                final PublishingDetails details = _client.getCurrentMenu();

                ViewGroup view = (ViewGroup)findViewById(R.id.menu);
                view.removeAllViews();
                if (null != details.menu) {
                    for (final Dish dish : details.menu.dishes()) {
                        CheckBox dishView = new CheckBox(view.getContext());
                        dishView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        dishView.setText(dish.name());
                        dishView.setChecked(_orderBuilder.items().containsKey(dish));
                        dishView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                                if (isChecked) {
                                    _orderBuilder.addItem(dish, 1);
                                } else {
                                    _orderBuilder.removeItem(dish, 1);
                                }
                                updateSubmittingButton(details);
                            }
                        });
                        view.addView(dishView);
                    }
                }
                updateSubmittingButton(details);
            }
        });
    }

    private void updateSubmittingButton(PublishingDetails details) {
        Button orderButton = (Button)findViewById(R.id.order);
        if (!details.orderingEnabled) {
            orderButton.setEnabled(false);
            orderButton.setText("Sorry. To late for pre-ordering.");

        } else if (_orderBuilder.items().size() == 0) {
            orderButton.setEnabled(false);
            orderButton.setText("Nothing selected");

        } else {
            orderButton.setEnabled(true);
            orderButton.setText("Order Now");
        }
    }

    private void onClickOrderNow() {
        performRequest(new Runnable() {
            @Override
            public void run() {
                Order orderDetails = _client.submitOrder(_orderBuilder);

                Intent intent = new Intent(MenuActivity.this, OrderDetailsActivity.class);
                intent.putExtra(OrderDetailsActivity.ORDER_KEY, orderDetails);
                startActivity(intent);
            }
        });
    }
}