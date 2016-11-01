package com.yuri.youracai.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yuri.youracai.Dominio.Caixa;
import com.yuri.youracai.Dominio.ItemVendido;
import com.yuri.youracai.Dominio.Venda;
import com.yuri.youracai.NovoPedidoAdapter;
import com.yuri.youracai.ProdutosMain;
import com.yuri.youracai.R;
import com.yuri.youracai.VendasDiaViewAdapter;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public  VendasDiaViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GregorianCalendar calendar = new GregorianCalendar();
        int dia = calendar.get(GregorianCalendar.DAY_OF_MONTH);
        int mes = calendar.get(GregorianCalendar.MONTH) + 1;
        int ano = calendar.get(GregorianCalendar.YEAR);

        //pega os pedidos realizados de hoje
        List<Venda> itensVenda = Venda.getVendasByData(dia, mes, ano);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerview);

        //logical of the "nenhum pedido adicionado ainda"
        RelativeLayout layoutNenhumPedidoAdicionado = (RelativeLayout) findViewById(R.id.relativeLayoutNenhumPedidoAdicionado);
        if(itensVenda.size() == 0)
            layoutNenhumPedidoAdicionado.setVisibility(View.VISIBLE);
        else
            layoutNenhumPedidoAdicionado.setVisibility(View.INVISIBLE);

        recyclerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        mAdapter = new VendasDiaViewAdapter(this, itensVenda);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab_main = (FloatingActionButton) findViewById(R.id.fab_main);
        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GregorianCalendar calendar = new GregorianCalendar();
                int dia = calendar.get(GregorianCalendar.DAY_OF_MONTH);
                int mes = calendar.get(GregorianCalendar.MONTH) + 1;
                int ano = calendar.get(GregorianCalendar.YEAR);

                boolean caixaJaAberto = Caixa.jaAberto(dia,mes,ano);

                //se caixa já aberto, continuo o fluxo normal do programa.
                if(caixaJaAberto) {

                    startActivity(new Intent(MainActivity.this,VendaActivity.class));

                }else{

                    Toast.makeText(MainActivity.this, "Abra o caixa antes de começar a vender.", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }


    @Override
    protected void onResume() {
        if(getIntent().getBooleanExtra("SAIR", false)){
            finish();
        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Opção não disponível ainda", Toast.LENGTH_SHORT).show();
        }
        return true;

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_abrir_caixa) {

            GregorianCalendar calendar = new GregorianCalendar();
            int dia = calendar.get(GregorianCalendar.DAY_OF_MONTH);
            int mes = calendar.get(GregorianCalendar.MONTH) + 1;
            int ano = calendar.get(GregorianCalendar.YEAR);

            boolean caixaJaAberto = Caixa.jaAberto(dia,mes,ano);

            //se caixa já aberto, continuo o fluxo normal do programa.
            //se nao abro o caixa.
            if(caixaJaAberto) {

                Toast.makeText(this, "Caixa já está aberto", Toast.LENGTH_SHORT).show();

            }else{
                Caixa caixa = new Caixa(true, dia,mes,ano);
                caixa.save();
                Toast.makeText(this, "Caixa aberto", Toast.LENGTH_SHORT).show();
            }

        }else if(id == R.id.nav_venda) {
            startActivity(new Intent(this, VendaActivity.class));

        }else if(id == R.id.nav_produtos) {
            startActivity(new Intent(this, ProdutosMain.class));

        }else if(id == R.id.nav_perfil) {
            startActivity(new Intent(this, PerfilFuncionario.class));
        }else if(id == R.id.nav_configuracoes) {
            Toast.makeText(this, "Opção não disponível ainda", Toast.LENGTH_SHORT).show();
        } else if(id == R.id.nav_fechar_caixa) {
            fecharCaixa();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void fecharCaixa(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        GregorianCalendar calendar = new GregorianCalendar();
        final int dia = calendar.get(GregorianCalendar.DAY_OF_MONTH);
        int mes = calendar.get(GregorianCalendar.MONTH) + 1;
        int ano = calendar.get(GregorianCalendar.YEAR);

        int qtdProdutosVendaDia = Caixa.quantidadeProdutosVendaDoDia(dia,mes,ano);

        builder.setTitle("Dados de hoje:");
        builder.setMessage("Quantidade de itens vendidos hoje : " + qtdProdutosVendaDia);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNeutralButton("Sair do app", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent  it = new Intent(getApplicationContext(), MainActivity.class);
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                it.putExtra("SAIR", true);
                startActivity(it);


            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();





    }
}
