package com.yuri.youracai.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.yuri.youracai.CustomExpandableListAdapter;
import com.yuri.youracai.Dominio.Categoria;
import com.yuri.youracai.Dominio.Produto;
import com.yuri.youracai.R;
import com.yuri.youracai.Services.ProdutoCategoriaService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VerProdutosActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_produtos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Lista de Produtos");
        toolbar.setSubtitle("Açai do Alex");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListDetail = ProdutoCategoriaService.getProdutosData(this);
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                Bundle bundle = new Bundle();

                //carrega na memória aquele respectivo produto.
                Produto produto = Produto.getProdutoByName(expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition));

                //As 4 posições do array são: 1 - nome, 2 - id da categoria - usado no spinner, 3 - preco, 4 - id
                bundle.putStringArray("InfoProduto", new String[]{produto.getNome(), produto.getId_categoria()+"",produto.getPreco()+"",produto.getId()+""});

                Intent intent = new Intent(VerProdutosActivity.this,VerDetalhesProduto.class);
                intent.putExtras(bundle);

                startActivity(intent);

                return false;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return true;

    }
}
