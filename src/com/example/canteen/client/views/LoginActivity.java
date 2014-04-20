package com.example.canteen.client.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.canteen.R;
import com.example.canteen.client.Application;
import com.example.canteen.client.api.Client;
import com.example.canteen.service.Facade;

public class LoginActivity extends CanteenActivity {
    private Client _client;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _client = ((Application)getApplication()).getClient();
        setContentView(R.layout.main);
        final TextView messageView = (TextView)findViewById(R.id.message);

        ((Button)findViewById(R.id.signIn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    _client.signIn(getInput(R.id.name), getInput(R.id.password));
                    messageView.setVisibility(View.GONE);
                    notifySucceeded();

                } catch (Facade.NotAuthorizedException ex) {
                    messageView.setText("Wrong name and/or password");
                    messageView.setVisibility(View.VISIBLE);
                }
            }
        });

        ((Button)findViewById(R.id.signUp)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    _client.signUp(getInput(R.id.name), getInput(R.id.password));
                    messageView.setVisibility(View.GONE);
                    notifySucceeded();

                } catch (Facade.DomainError ex) {
                    messageView.setText("Name already exists");
                    messageView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void notifySucceeded() {
        Intent intent = new Intent();
        setResult(RESULT_OK);
        finish();
    }

    private String getInput(int inputId) {
        return ((EditText)findViewById(inputId)).getText().toString();
    }
}