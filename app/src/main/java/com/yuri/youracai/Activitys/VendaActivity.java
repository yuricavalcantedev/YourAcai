package com.yuri.youracai.Activitys;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yuri.youracai.Dominio.ItemVendido;
import com.yuri.youracai.Dominio.Venda;
import com.yuri.youracai.NovoPedidoAdapter;
import com.yuri.youracai.R;

import java.util.ArrayList;
import java.util.List;

import static com.activeandroid.Cache.getContext;
import static com.yuri.youracai.Activitys.FragmentVendaListaItens.mAdapter;
import static com.yuri.youracai.R.id.recyclerView;

public class VendaActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venda);

          // TODO: vou deixar sem Toolbar por enquanto, até achar uma forma de ficar mais bonito
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//
//        toolbar.setTitle("Nova Venda");
//        toolbar.setSubtitle("Açai do Alex");
//        setSupportActionBar(toolbar);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        // tabLayout tem o seu listener.
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {


            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(tab.getPosition() == 1){

                    notifyDataChangeMyRecylerView();

                    List<ItemVendido> itensList = ItemVendido.getItensVendidosByVenda(0);
                    double total = 0;

                    for (int i = 0; i < itensList.size(); i++)
                        total += itensList.get(i).getPrecoItem() * itensList.get(i).getQuantidadeItem();

                    TextView tvSubTotal = (TextView) findViewById(R.id.tv_subtotal_lista_itens_venda);
                    tvSubTotal.setText("R$ " + total);

                    if(itensList.size() > 0){

                        TextView tvNenhumItem = (TextView) findViewById(R.id.tv_nenhum_item_adicionado);
                        tvNenhumItem.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

    }

    private void notifyDataChangeMyRecylerView(){

        //0 é o id da venda que ainda não finalizou.
        List<ItemVendido> itensList = ItemVendido.getItensVendidosByVenda(0);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new NovoPedidoAdapter(itensList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id ==  android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentVendaAdicionarItem(), "Adicionar Item");
        adapter.addFragment(new FragmentVendaListaItens(), "Ver Itens");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);

        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

}
