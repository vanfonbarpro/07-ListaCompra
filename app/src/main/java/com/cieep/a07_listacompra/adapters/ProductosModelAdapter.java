package com.cieep.a07_listacompra.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cieep.a07_listacompra.R;
import com.cieep.a07_listacompra.modelos.ProductModel;

import java.text.NumberFormat;
import java.util.List;

public class ProductosModelAdapter extends RecyclerView.Adapter<ProductosModelAdapter.ProductoVH> {

    private List<ProductModel> objects;
    private int resource;
    private Context context;

    private NumberFormat numberFormat;



    public ProductosModelAdapter(List<ProductModel> objects, int resource, Context context) {
        this.objects = objects;
        this.resource = resource;
        this.context = context;
        this.numberFormat = NumberFormat.getCurrencyInstance();
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

    }


    @Override
    public int getItemCount() {
        return objects.size();
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
