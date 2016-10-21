package com.yuri.youracai;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yuri.youracai.Activitys.AdicionarProdutoActivity;
import com.yuri.youracai.Activitys.VerProdutosActtivity;

public class ProdutosMain extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos_main);

        context = this;

        ImageView img_add_produtos = (ImageView) findViewById(R.id.img_add_produto);
        img_add_produtos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,AdicionarProdutoActivity.class));
            }
        });

        ImageView img_ver_produtos = (ImageView) findViewById(R.id.img_ver_produtos);
        img_ver_produtos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,VerProdutosActtivity.class));
            }
        });


    }
}
