package com.yuri.youracai;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.yuri.youracai.Activitys.AdicionarProdutoActivity;
import com.yuri.youracai.Activitys.VerProdutosActivity;

public class ProdutosMain extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Produtos");
        toolbar.setSubtitle("Açai do Alex");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                startActivity(new Intent(context,VerProdutosActivity.class));
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
