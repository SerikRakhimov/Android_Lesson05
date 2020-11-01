package com.example.products;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        EditText productName = findViewById(R.id.productNameEditText);
        EditText productDescription = findViewById(R.id.productDescriptionEditText);
        EditText productPrice = findViewById(R.id.productPriceEditText);

        Button button = findViewById(R.id.sendButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String name = productName.getText().toString();
                String description = productDescription.getText().toString();
                String price = productPrice.getText().toString();
                if (TextUtils.isEmpty(price)) {
                    price = "0";
                }
                intent.putExtra("name", name);
                intent.putExtra("description", description);
                intent.putExtra("price", price);

                if (TextUtils.isDigitsOnly(price)) {
                    setResult(Activity.RESULT_OK, intent);
                } else {
                    setResult(Activity.RESULT_CANCELED, intent);
                }
                onBackPressed();
            }
        });
    }

}