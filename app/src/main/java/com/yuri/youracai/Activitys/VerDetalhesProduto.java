package com.yuri.youracai.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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

import com.yuri.youracai.Dominio.Categoria;
import com.yuri.youracai.Dominio.Produto;
import com.yuri.youracai.R;

import java.util.List;

public class VerDetalhesProduto extends AppCompatActivity {

    String[] infoProduto;
    EditText etNome;
    EditText etPreco;
    int novoIdProduto = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_detalhes_produto);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Editar | Excluir Produto");
        toolbar.setSubtitle("Açai do Alex");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();

        infoProduto = bundle.getStringArray("InfoProduto");

        etNome = (EditText)findViewById(R.id.et_nome_detalhes);
        etPreco = (EditText)findViewById(R.id.et_preco_detalhes);

        //infoProduto = 0 - Nome do produto
        //infoProduto = 1 - Id da Categoria do Produto
        etNome.setText(infoProduto[0]);
        etPreco.setText(infoProduto[2]);

        Button btSalvar = (Button)findViewById(R.id.bt_salvar_produto);
        Button btExcluir = (Button)findViewById(R.id.bt_excluir_produto);

        btSalvar.setOnClickListener(onClickSalvarProduto);
        btExcluir.setOnClickListener(onClickExcluirProduto);

        Spinner spinnerCategoria = (Spinner)findViewById(R.id.spinner_categoria_detalhes);

        List<String> listAllCategorias = Categoria.getNomeAllCategorias();
        ArrayAdapter adapter = new ArrayAdapter(VerDetalhesProduto.this
                , android.R.layout.simple_spinner_dropdown_item, listAllCategorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCategoria.setAdapter(adapter);

        //o '-1' é porque as categorias começam de 1 e o spinner começa do 0.
        //como eu alimento o spinner, ele começa do 0.
        //então a categoria 1 corresponde a posição 0 do spinner por exemplo.
        spinnerCategoria.setSelection(Integer.parseInt(infoProduto[1]) - 1);

        novoIdProduto = Integer.parseInt(infoProduto[1]) - 1;

        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                novoIdProduto = position+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private View.OnClickListener onClickSalvarProduto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Produto produto = Produto.getProdutoByName(infoProduto[0]);
            produto.setNome(etNome.getText().toString());
            produto.setPreco(Double.parseDouble(etPreco.getText().toString()));
            produto.setId_categoria(novoIdProduto);

            produto.save();

            final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_ver_detalhes_produto);

            Snackbar snackbar = Snackbar
                    .make(relativeLayout, "Produto atualizado com sucesso.", Snackbar.LENGTH_LONG)
                    .setAction("Ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           startActivity(new Intent(VerDetalhesProduto.this, MainActivity.class));
                        }
                    });

            snackbar.show();


        }
    };

    private View.OnClickListener onClickExcluirProduto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            final Produto produto = Produto.getProdutoByName(infoProduto[0]);

            AlertDialog.Builder builder = new AlertDialog.Builder(VerDetalhesProduto.this);

            builder.setTitle("Excluir Produto");
            builder.setMessage("Você tem certeza que deseja excluir esse produto?");
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_ver_detalhes_produto);

                    produto.delete();

                    Snackbar snackbar = Snackbar
                            .make(relativeLayout, "Produto deletado com sucesso.", Snackbar.LENGTH_LONG)
                            .setAction("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(VerDetalhesProduto.this, MainActivity.class));
                                }
                            });

                    snackbar.show();


                }
            });

            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
    };


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
