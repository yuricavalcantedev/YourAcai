package com.yuri.youracai;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.yuri.youracai.Dominio.Categoria;
import com.yuri.youracai.Dominio.ItemVendido;
import com.yuri.youracai.Dominio.Venda;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuri on 15/10/2016.
 */

public class VendasDiaViewAdapter extends RecyclerView.Adapter<VendasDiaViewAdapter.VendaDiaViewHolder> {

    private List<Venda> listaVendas;
    private Context mContext;


    //HOLDER DE CADA PEDIDO
    public class VendaDiaViewHolder extends RecyclerView.ViewHolder {

        TextView categoriaVenda;
        TextView descricaoVenda;
        TextView totalVenda;


        public VendaDiaViewHolder(View itemView) {

            super(itemView);

            categoriaVenda = (TextView) itemView.findViewById(R.id.tv_categoria_produtos_main);
            descricaoVenda = (TextView) itemView.findViewById(R.id.tv_descricao_pedido_main);
            totalVenda = (TextView) itemView.findViewById(R.id.tv_total_venda_main);
        }

    }


    public VendasDiaViewAdapter(Context context, List<Venda> listaVendas) {

        mContext = context;
        this.listaVendas = listaVendas;

    }

    @Override
    public  VendaDiaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.list_item_main, viewGroup, false);
        return new  VendaDiaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( VendaDiaViewHolder holder, int position) {

        Venda venda = listaVendas.get(position);

        //holder descrição venda
        List<ItemVendido> itemVendidoList = venda.getListaItensVendidos();
        if(itemVendidoList.size() == 1)
            holder.descricaoVenda.setText(itemVendidoList.get(0).getNomeItem());
        else if(itemVendidoList.size() > 1)
            holder.descricaoVenda.setText(itemVendidoList.get(0).getNomeItem()+ " ...");

        //holder categorias venda
        List<String> listCategorias = venda.getListaCategorias();
        String textoCategorias = "";

        //ajeita o texto da categoria
        if (listCategorias.size() == 1)
            textoCategorias = listCategorias.get(0);
        else{

            for(int i = 0; i < listCategorias.size(); i++) {
                if (i == listCategorias.size())
                    textoCategorias += listCategorias.get(i);
                else
                    textoCategorias += listCategorias.get(i) + ", ";
            }
        }
                holder.categoriaVenda.setText(textoCategorias);

                //holder valor venda
                holder.totalVenda.setText("R$ "+venda.getTotal());
            }

            @Override
            public int getItemCount() {
                return listaVendas.size();
            }

        }
