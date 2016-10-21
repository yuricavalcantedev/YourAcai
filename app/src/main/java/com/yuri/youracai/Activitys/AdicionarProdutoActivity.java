package com.yuri.youracai.Activitys;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.yuri.youracai.R;

public class AdicionarProdutoActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_produto);

        context = this;


        Button bt_add_produto = (Button)findViewById(R.id.bt_add_produto);
        bt_add_produto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText et_nome_produto = (EditText)findViewById(R.id.et_nome_produto);
                EditText et_preco_produto = (EditText)findViewById(R.id.et_preco_produto);
                Spinner spinner_categoria = (Spinner) findViewById(R.id.spinner_add_produto);

                String nome_produto = et_nome_produto.getText().toString();
                Double preco_produto = Double.parseDouble(et_preco_produto.getText().toString());
                String categoria_produto = spinner_categoria.toString();

                if(nome_produto.length() < 8 || nome_produto.length() == 0)
                    Toast.makeText(AdicionarProdutoActivity.this, "O nome do produto está muito curto, você esqueceu de alguma letra ?", Toast.LENGTH_SHORT).show();
                else if(et_preco_produto.length() == 0)
                    Toast.makeText(AdicionarProdutoActivity.this, "Você esqueceu de colocar o preco do produto ?", Toast.LENGTH_SHORT).show();
                else {


                   // boolean resultado = ProdutoCategoriaService.addProduto(context,nome_produto, preco_produto, categoria_produto);

                   // if(resultado)
                        Toast.makeText(AdicionarProdutoActivity.this, "Produto adicionado!", Toast.LENGTH_SHORT).show();
                   // else
                        Toast.makeText(AdicionarProdutoActivity.this, "Produto não adicionado, check as informações e tente novamente.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
