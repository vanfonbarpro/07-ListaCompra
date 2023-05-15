package com.cieep.a07_listacompra;

import android.content.Intent;
import android.os.Bundle;

import com.cieep.a07_listacompra.modelos.ProductModel;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import com.cieep.a07_listacompra.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private ArrayList<ProductModel> productoModelsList;

    private ActivityResultLauncher<Intent> launcherAddProducto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        productoModelsList = new ArrayList<>();

        inicializarLaunchers();


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcherAddProducto.launch(new Intent(MainActivity.this, AddProductoActivity.class));
            }
        });
    }

    private void inicializarLaunchers() {
        launcherAddProducto = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        launcherAddProducto = registerForActivityResult(
                                new ActivityResultContracts.StartActivityForResult(),
                                new ActivityResultCallback<ActivityResult>() {
                                    @Override
                                    public void onActivityResult(ActivityResult result) {
                                        if (result.getResultCode() == RESULT_OK) {
                                            if (result.getData() != null && result.getData().getExtras() != null) {
                                                ProductModel p = (ProductModel) result.getData().getExtras().getSerializable("PROD");
                                                productoModelsList.add(p);
                                                Toast.makeText(MainActivity.this, p.toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }
                        );

                    }
                }
        );
    }
}