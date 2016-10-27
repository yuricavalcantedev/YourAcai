package com.yuri.youracai.Activitys;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.yuri.youracai.Dominio.ItemVendido;
import com.yuri.youracai.Dominio.Produto;
import com.yuri.youracai.R;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class FragmentVendaAdicionarItem extends Fragment {

    Spinner spinner_produto;
    Spinner spinner_quantidade;
    List<String> listNomeProdutos;
    List<Double> listPrecoProdutos;
    List<Produto> listProdutosByCategoria;
    String nomeProduto;
    int quantidadeProduto;
    double precoProduto;
    double valorTotalPoduto;
    int idCategoria;
    View layout_view;
    public static int flag;

    public FragmentVendaAdicionarItem() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes

        layout_view = inflater.inflate(R.layout.fragment_venda_adicionar_item, container, false);
        spinner_produto = (Spinner)layout_view.findViewById(R.id.spinner_item);
        spinner_quantidade = (Spinner)layout_view.findViewById(R.id.spinner_quantidade_item);
        listNomeProdutos = new ArrayList<>();

        //seta o spinner dos produtos primeiro para a categoria "Açai" porque é a primeira categoria.
        listProdutosByCategoria = Produto.getAllProdutosByCategoria(1);
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, listWithNamesAndPricesOfItems());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_produto.setAdapter(adapter);

        Spinner spinner_categoria = (Spinner)layout_view.findViewById(R.id.spinner_categoria_item);
        spinner_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                idCategoria = position+1;
                listProdutosByCategoria = Produto.getAllProdutosByCategoria(position+1);
                listNomeProdutos = listWithNamesOfItems();
                listPrecoProdutos = listWithPricesOfItems();

                ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, listWithNamesAndPricesOfItems());
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_produto = (Spinner)layout_view.findViewById(R.id.spinner_item);
                spinner_produto.setAdapter(adapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_produto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                List<String> listQuantidade = fillSpinnerQuantidade();
                ArrayAdapter adapterQuantidade = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listQuantidade);
                adapterQuantidade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_quantidade = (Spinner)layout_view.findViewById(R.id.spinner_quantidade_item);
                spinner_quantidade.setAdapter(adapterQuantidade);

                nomeProduto = listNomeProdutos.get(position);
                quantidadeProduto = 1;
                precoProduto =  listPrecoProdutos.get(position);
                valorTotalPoduto = precoProduto * quantidadeProduto;

                TextView tv_nome_produto = (TextView) layout_view.findViewById(R.id.tv_nome_item);
                TextView tv_quantidade_produto = (TextView) layout_view.findViewById(R.id.tv_quantidade_item);
                TextView tv_valor_total_produto = (TextView) layout_view.findViewById(R.id.tv_total_item);

                tv_nome_produto.setText(nomeProduto);
                tv_quantidade_produto.setText(quantidadeProduto+"");
                tv_valor_total_produto.setText("R$ "+valorTotalPoduto + "");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_quantidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                quantidadeProduto = position+1;
                valorTotalPoduto = precoProduto * quantidadeProduto;


                TextView tv_quantidade_produto = (TextView) layout_view.findViewById(R.id.tv_quantidade_item);
                TextView tv_valor_total_produto = (TextView) layout_view.findViewById(R.id.tv_total_item);

                tv_quantidade_produto.setText(quantidadeProduto+"");
                tv_valor_total_produto.setText(valorTotalPoduto + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button bt_adicionar_item = (Button)layout_view.findViewById(R.id.bt_adicionar_item);
        bt_adicionar_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemVendido itemVendido = new ItemVendido(0,nomeProduto,precoProduto,idCategoria,quantidadeProduto);
                itemVendido.save();

                Toast.makeText(getContext(), "Item adicionado", Toast.LENGTH_SHORT).show();
            }
        });

        return layout_view;
    }

    private List<String> fillSpinnerQuantidade(){

        List<String> listQuantidade = new ArrayList< >() ;
        for(int i = 1; i <= 10; i ++)
            listQuantidade.add(i+"");

        return listQuantidade;
    }

    private List<String> listWithNamesAndPricesOfItems(){

        List<String> listWithNamesAndPrices = new ArrayList<>();
        for(int i = 0; i < listProdutosByCategoria.size(); i++)
            listWithNamesAndPrices.add(listProdutosByCategoria.get(i).getNome()+" - R$ " + listProdutosByCategoria.get(i).getPreco());

        return listWithNamesAndPrices;
    }

    private List<String> listWithNamesOfItems(){

        List<String> listWithNames  = new ArrayList<>();
        for(int i = 0; i < listProdutosByCategoria.size(); i++)
            listWithNames.add(listProdutosByCategoria.get(i).getNome());

        return listWithNames;
    }

    private List<Double> listWithPricesOfItems(){

        List<Double> listWithPrices  = new ArrayList<>();
        for(int i = 0; i < listProdutosByCategoria.size(); i++)
            listWithPrices.add(listProdutosByCategoria.get(i).getPreco());

        return listWithPrices;

    }

}