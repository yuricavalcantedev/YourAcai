package com.yuri.youracai;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.yuri.youracai.Dominio.Categoria;
import com.yuri.youracai.Dominio.ItemVendido;
import com.yuri.youracai.Dominio.Produto;

import java.util.List;

/**
 * Created by Yuri on 15/10/2016.
 */


public class NovoPedidoAdapter extends RecyclerView.Adapter<NovoPedidoAdapter.MyViewHolder> {

private List<ItemVendido> produtoList;


public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView tvNomeItem;
    public TextView tvCategoria;
    public TextView tvPrecoTotal;

    public MyViewHolder(View view) {
        super(view);
        tvNomeItem = (TextView) view.findViewById(R.id.tv_nome_item);
        tvCategoria = (TextView) view.findViewById(R.id.tv_categoria_produto);
        tvPrecoTotal = (TextView) view.findViewById(R.id.tv_subtotal_item);
    }

}

    public NovoPedidoAdapter(List<ItemVendido> produtoList) {
        this.produtoList = produtoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_adicionado_venda, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        //esse if é necessário, porque se não há pedidos abertos ainda, a consulta retorna um objeto nulo.
        if(produtoList.get(0).getNomeItem() != null) {

            ItemVendido itemVendido = produtoList.get(position);

            holder.tvNomeItem.setText(itemVendido.getNomeItem());
            holder.tvCategoria.setText(Categoria.getNomeById(itemVendido.getIdCategoriaItem()));
            holder.tvPrecoTotal.setText("R$ " + (itemVendido.getPrecoItem() * itemVendido.getQuantidadeItem()) + "");
        }

    }

    @Override
    public int getItemCount() {
        return produtoList.size();
    }
}