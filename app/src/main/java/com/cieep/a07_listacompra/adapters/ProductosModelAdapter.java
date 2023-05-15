package com.cieep.a07_listacompra.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cieep.a07_listacompra.MainActivity;
import com.cieep.a07_listacompra.R;
import com.cieep.a07_listacompra.modelos.ProductModel;

import java.text.NumberFormat;
import java.util.List;

public class ProductosModelAdapter extends RecyclerView.Adapter<ProductosModelAdapter.ProductoVH> {

    private List<ProductModel> objects;
    private int resource;
    private Context context;

    private NumberFormat numberFormat;

    private MainActivity main;



    public ProductosModelAdapter(List<ProductModel> objects, int resource, Context context) {
        this.objects = objects;
        this.resource = resource;
        this.context = context;
        this.numberFormat = NumberFormat.getCurrencyInstance();
        this.main = (MainActivity) context;
    }

    @NonNull
    @Override
    public ProductoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productoView = LayoutInflater.from(context).inflate(resource, null);
        productoView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ProductoVH(productoView);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductoVH holder, int position) {
        ProductModel p = objects.get(position);
        holder.lbNombre.setText(p.getNombre());
        holder.lbCantidad.setText(String.valueOf(p.getCantidad()));
        holder.lbPrecio.setText(numberFormat.format(p.getPrecio()));

        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete(p, holder.getAdapterPosition()).show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProducto(p, holder.getAdapterPosition()).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return objects.size();
    }


    private AlertDialog editProducto(ProductModel p, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(p.getNombre());

        View productoView = LayoutInflater.from(context).inflate(R.layout.activity_add_producto, null);

        EditText txtNombre = productoView.findViewById(R.id.txtNombreProductoAdd);
        // ni esta visible ni ocupa el espacio
        txtNombre.setVisibility(View.GONE);

        Button btn = productoView.findViewById(R.id.btnAgregarProductoAdd);
        btn.setVisibility(View.GONE);

        EditText txtCantidad = productoView.findViewById(R.id.txtCantidadProductoAdd);
        txtCantidad.setText(String.valueOf(p.getCantidad()));

        EditText txtPrecio = productoView.findViewById(R.id.txtPrecioProductoAdd);
        txtPrecio.setText(String.valueOf(p.getPrecio()));

        builder.setView(productoView);

        builder.setNegativeButton("CANCELAR", null);
        builder.setPositiveButton("MODIFICAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!txtCantidad.getText().toString().isEmpty()
                && !txtPrecio.getText().toString().isEmpty()){
                    p.setCantidad(Integer.parseInt(txtCantidad.getText().toString()));
                    p.setPrecio(Float.parseFloat(txtPrecio.getText().toString()));
                    notifyItemChanged(position);
                    main.calculaValores();
                }
            }
        });

        return builder.create();
    }


    private AlertDialog confirmDelete(ProductModel p, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Estas seguro?");
        builder.setCancelable(false);
        builder.setNegativeButton("NO", null);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                objects.remove(p);
                notifyItemRemoved(position);
                main.calculaValores();
            }
        });

        return builder.create();
    }



    public class ProductoVH extends RecyclerView.ViewHolder{
        TextView lbNombre, lbCantidad, lbPrecio;ImageButton btnEliminar;


        public ProductoVH(@NonNull View itemView) {
            super(itemView);

           lbNombre = itemView.findViewById(R.id.lbNombreProductoCard);
            lbCantidad = itemView.findViewById(R.id.lbCantidadProductoCard);
            lbPrecio = itemView.findViewById(R.id.lbPrecioProductoCard);
            btnEliminar = itemView.findViewById(R.id.btnEliminarProductoCard);
        }
    }


}
