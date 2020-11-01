package com.example.products;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        TextView priceTextView = findViewById(R.id.priceTextView);
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            nameTextView.setText(arguments.get("name").toString());
            descriptionTextView.setText(arguments.get("description").toString());
            priceTextView.setText("Цена - " + arguments.get("price").toString() + " тенге");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Boolean result = false;
        if (item.getItemId() == R.id.exit) {
            finish();
            result = true;

        }
        return result;
    }
}