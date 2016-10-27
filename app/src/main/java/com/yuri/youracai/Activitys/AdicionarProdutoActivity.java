package com.yuri.youracai.Activitys;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.yuri.youracai.Dominio.Categoria;
import com.yuri.youracai.Dominio.Produto;
import com.yuri.youracai.R;

import java.util.List;

public class AdicionarProdutoActivity extends AppCompatActivity {

    Context context;
    int categoria_produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_produto);

        context = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Adicionar Produto");
        toolbar.setSubtitle("Açai do Alex");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner spinnerCategoria = (Spinner)findViewById(R.id.spinner_add_produto);

        List<String> listAllCategorias = Categoria.getNomeAllCategorias();
        ArrayAdapter adapter = new ArrayAdapter(AdicionarProdutoActivity.this
                , android.R.layout.simple_spinner_dropdown_item, listAllCategorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCategoria.setAdapter(adapter);

        //por padrão o primeira categoria que vai aparecer lá é a categoria do açai.
        categoria_produto = 1;
        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //só atualiza a categoria do produto para ser a categoria do produto escolhido pelo usuário.
                categoria_produto = position+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button bt_add_produto = (Button)findViewById(R.id.bt_add_produto);
        bt_add_produto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText et_nome_produto = (EditText)findViewById(R.id.et_nome_produto);
                EditText et_preco_produto = (EditText)findViewById(R.id.et_preco_produto);

                String nome_produto = et_nome_produto.getText().toString();
                Double preco_produto = Double.parseDouble(et_preco_produto.getText().toString());

                if(nome_produto.length() < 8 || nome_produto.length() == 0)
                    Toast.makeText(AdicionarProdutoActivity.this, "O nome do produto está muito curto, você esqueceu de alguma letra ?", Toast.LENGTH_SHORT).show();
                else if(et_preco_produto.length() == 0)
                    Toast.makeText(AdicionarProdutoActivity.this, "Você esqueceu de colocar o preco do produto ?", Toast.LENGTH_SHORT).show();
                else {

                    Produto produto = new Produto(nome_produto,preco_produto, categoria_produto);
                    produto.save();

                    RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_adicionar_produto);

                    Snackbar snackbar = Snackbar
                            .make(relativeLayout, "Produto adicionado com sucesso.", Snackbar.LENGTH_LONG)
                            .setAction("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(AdicionarProdutoActivity.this, MainActivity.class));
                                }
                            });

                    snackbar.show();
                }
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
