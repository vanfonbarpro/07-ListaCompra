package com.cieep.a07_listacompra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cieep.a07_listacompra.databinding.ActivityAddProductoBinding;
import com.cieep.a07_listacompra.modelos.ProductModel;

public class AddProductoActivity extends AppCompatActivity {

    private ActivityAddProductoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnAgregarProductoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = binding.txtNombreProductoAdd.getText().toString();
                String cantidadS = binding.txtCantidadProductoAdd.getText().toString();
                String precioS = binding.txtPrecioProductoAdd.getText().toString();

                if(!nombre.isEmpty() && !cantidadS.isEmpty() && !precioS.isEmpty()){
                    ProductModel p = new ProductModel(nombre, Integer.parseInt(cantidadS), Float.parseFloat(precioS));
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("PROD", p);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}
