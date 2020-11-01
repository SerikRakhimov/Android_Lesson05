package com.example.products;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private MyAdapter myAdapter;
    private final int REQUEST_CODE = 100;
    private final String CHANNEL_ID = "CHANNEL_ID";
    private final String GROUP_KEY = "GROUP_KEY";
    private int NOTIFY_ID = 101;
    private NotificationManager notificationManager;
    private Basket basket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        createChannel();

        basket = new Basket();

        myAdapter = new MyAdapter(getProductsList());

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(myAdapter);

        myAdapter.setOnClickListener(new MyAdapter.MyOnClickListener() {

            @Override
            public void onClick(Product product) {
                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                intent.putExtra("name", product.getName());
                intent.putExtra("description", product.getDescription());
                intent.putExtra("price", product.getPrice());
                startActivity(intent);
            }

            @Override
            public void onLongClick(Product product) {
                int price = product.getPrice();
                basket.addToBasket(product.getPrice());
                Toast.makeText(MainActivity.this,
                        "Цена " + price + " тенге добавлена в Корзину, общая сумма в Корзине = " + basket.getTotalSum() + " тенге.",
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(MainActivity.this, "Нажатие - подробное описание, Удержание - добавление в Корзину", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_item:
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                return true;
            case R.id.basket:
                pressBasket();
                return true;
            case R.id.exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String name = data.getStringExtra("name");
            String description = data.getStringExtra("description");
            String price = data.getStringExtra("price");

            myAdapter.addItem(new Product(R.drawable.ic_food,name, description, Integer. parseInt(price)));
        }
    }

    @SuppressLint("WrongConstant")
    public void pressBasket() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        int totalSum = basket.getTotalSum();
        String message = "Общая сумма в Корзине = " + totalSum + " тенге.";
        if (totalSum == 0) {
            Toast.makeText(MainActivity.this, "Товары не выбраны!", Toast.LENGTH_SHORT).show();
        } else {
            Snackbar
                    .make(
                            recyclerView,
                            message,
                            Snackbar.ANIMATION_MODE_SLIDE | Snackbar.LENGTH_LONG
                    )
                    .setAction(
                            "Купить",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    createNotification(totalSum);
                                    Toast.makeText(MainActivity.this, "Покупка совершена.", Toast.LENGTH_SHORT).show();
                                }
                            }
                    )
                    .show();
        }
    }

    private void createChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID, "First channel", NotificationManager.IMPORTANCE_HIGH
            );
            notificationChannel.setDescription("First channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void createNotification(int totalSum) {

        // Текущее дата/время
        Date currentDate = new Date();
        // Форматирование времени как "день.месяц.год"
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID);
        builder
                .setSmallIcon(R.drawable.ic_notification)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_notification_large))
                .setAutoCancel(true)
                .setContentTitle("Уведомление об оплате")
                .setContentText("Дата оплаты = " + dateText + ", общая сумма = " + Integer.toString(totalSum) + " тенге.")
                .setContentInfo("Text")
                .setColor(Color.BLACK)
                .setGroup(GROUP_KEY)
                .setGroupSummary(true);

        Notification notification = builder.build();
        notificationManager.notify(NOTIFY_ID, notification);
        NOTIFY_ID++;
    }

    private ArrayList<Product> getProductsList() {
        ArrayList<Product> products = new ArrayList<Product>();
        products.add(new Product(R.drawable.ic_food, "Пицца", "Пицца Маргарита d30см", 1000));
        products.add(new Product(R.drawable.ic_food, "Коктейль", "Молочный коктейль", 500));
        products.add(new Product(R.drawable.ic_food, "Мороженное", "Сливочное мороженное", 300));

        return products;
    }

}