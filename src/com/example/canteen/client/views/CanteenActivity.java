package com.example.canteen.client.views;

import android.app.Activity;
import android.content.Intent;
import com.example.canteen.service.Facade;

public class CanteenActivity extends Activity {
    private Runnable _retryableRequest;

    protected void performRequest(Runnable request) {
        _retryableRequest = request;
        retryRequest();
    }

    private void retryRequest() {
        try {
            _retryableRequest.run();
            _retryableRequest = null;

        } catch (Facade.NotAuthorizedException ex) {
            // todo: start login intent
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (RESULT_OK == resultCode) {
            retryRequest();
        }
    }
}
