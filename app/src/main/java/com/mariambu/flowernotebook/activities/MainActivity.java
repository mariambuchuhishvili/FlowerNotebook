package com.mariambu.flowernotebook.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mariambu.flowernotebook.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openNomenclatureActivity(View view){
        Intent intent = new Intent(this,NomenclatureActivity.class);
        startActivity(intent);
    }
    public void openSalesActivity(View view){
        Intent intent = new Intent(this,SalesActivity.class);
        startActivity(intent);
    }
    public void openProviderActivity(View view){
        Intent intent = new Intent(this,ProviderActivity.class);
        startActivity(intent);
    }
}