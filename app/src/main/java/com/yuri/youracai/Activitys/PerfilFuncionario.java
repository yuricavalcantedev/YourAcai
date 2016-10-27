package com.yuri.youracai.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.yuri.youracai.Dominio.Funcionario;
import com.yuri.youracai.R;

public class PerfilFuncionario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_funcionario);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Perfil Funcionário");
        toolbar.setSubtitle("Açai do Alex");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView imgFuncionario = (ImageView) findViewById(R.id.img_default_funcionario);

        //TODO: pegar a quantidade de vendas de hoje e no total
        Funcionario funcionario = Funcionario.load(Funcionario.class, 1);

        //só precisa desse if, pois por padrão, a imagem já é masculina
        if(funcionario.getSexo().equals("Feminino"))
            imgFuncionario.setImageResource(R.drawable.ic_female);

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
