package com.yuri.youracai.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yuri.youracai.Dominio.ItemVendido;
import com.yuri.youracai.Dominio.Produto;
import com.yuri.youracai.Dominio.Venda;
import com.yuri.youracai.NovoPedidoAdapter;
import com.yuri.youracai.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FragmentVendaListaItens extends Fragment {

    public static NovoPedidoAdapter mAdapter;
    double total;
    double dinheiroCliente;
    TextView tvTotalPedidoDialog;
    boolean disableRadioButtonByCheckBox = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        final View layoutView = inflater.inflate(R.layout.fragment_lista_itens_venda, container, false);

        //0 é o id da venda que ainda não finalizou.
        List<ItemVendido> itensList = ItemVendido.getItensVendidosByVenda(0);
        RecyclerView recyclerView = (RecyclerView) layoutView.findViewById(R.id.recyclerView);

        mAdapter = new NovoPedidoAdapter(itensList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        //TODO : perguntar se quer excluir o item selecionado.
        //TODO : procurar o listener do recyclerView

        final TextView tvNenhumItemAdicionado = (TextView) layoutView.findViewById(R.id.tv_nenhum_item_adicionado);

        if (itensList.size() == 0)
            tvNenhumItemAdicionado.setVisibility(View.VISIBLE);
        else
            tvNenhumItemAdicionado.setVisibility(View.INVISIBLE);

        //total = 0;

        for (int i = 0; i < itensList.size(); i++)
            total += itensList.get(i).getPrecoItem() * itensList.get(i).getQuantidadeItem();

        final TextView tvSubTotal = (TextView) layoutView.findViewById(R.id.tv_subtotal_lista_itens_venda);
        tvSubTotal.setText("R$ " + total);

        Button bt_finalizar_compra = (Button) layoutView.findViewById(R.id.bt_finalizar_pedido);
        bt_finalizar_compra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

                LayoutInflater layoutInflater = getActivity().getLayoutInflater();

                final View dialog_customer_layout = layoutInflater.inflate(R.layout.dialog_finalizar_compra, null);

                tvTotalPedidoDialog = (TextView) dialog_customer_layout.findViewById(R.id.tv_dialog_total);

                //para atualizar o total do dialog.
                String totalTxt = tvSubTotal.getText().toString();
                totalTxt = totalTxt.substring(3,totalTxt.length());

                tvTotalPedidoDialog.setText(totalTxt);

                final RadioButton rbValorEntrega1 = (RadioButton) dialog_customer_layout.findViewById(R.id.rb_taxa_entrega_valor_1);
                final RadioButton rbValorEntrega2 = (RadioButton) dialog_customer_layout.findViewById(R.id.rb_taxa_entrega_valor_2);
                final RadioButton rb_forma_pagamento_avista = (RadioButton) dialog_customer_layout.findViewById(R.id.rb_pagamento_avista);
                final RadioButton rb_forma_pagamento_cartao = (RadioButton) dialog_customer_layout.findViewById(R.id.rb_pagamento_cartao);
                final EditText et_troco_para = (EditText) dialog_customer_layout.findViewById(R.id.et_dialog_troco_para);
                final TextView tv_troco_para = (TextView) dialog_customer_layout.findViewById(R.id.tv_troco_para);
                final TextView tv_texto_cliente = (TextView) dialog_customer_layout.findViewById(R.id.tv_texto_dialog_troco_cliente);
                final TextView tv_troco_cliente = (TextView) dialog_customer_layout.findViewById(R.id.tv_dialog_troco_cliente);


                et_troco_para.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        if(!rb_forma_pagamento_cartao.isChecked()){

                            try{


                                String dinheiroTexto =  et_troco_para.getText().toString();
                                if(dinheiroTexto.length() > 0){

                                    dinheiroCliente = Double.parseDouble(dinheiroTexto);
                                    tv_troco_cliente.setVisibility(View.VISIBLE);

                                    if (dinheiroCliente < total) {

                                        TextView tv_aviso = (TextView) dialog_customer_layout.findViewById(R.id.tv_dialog_aviso);
                                        tv_aviso.setText("Dinheiro insuficiente");
                                        tv_troco_cliente.setText("");
                                    } else {

                                        tvTotalPedidoDialog = (TextView) dialog_customer_layout.findViewById(R.id.tv_dialog_total);

                                        //para atualizar o total do dialog.

                                        String totalTxt = tvSubTotal.getText().toString();
                                        totalTxt = totalTxt.substring(3,totalTxt.length());
                                        tv_troco_cliente.setText((dinheiroCliente - Double.parseDouble(totalTxt)) + "");
                                    }
                                }


                            }catch (Exception ex){
                                Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                });

                CheckBox cbTaxaEntrega = (CheckBox) dialog_customer_layout.findViewById(R.id.cb_taxa_entrega);
                cbTaxaEntrega.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        //criei um método, já que uso isso pelo menos umas 3 vezes.
                        atualizaTrocoCliente(rb_forma_pagamento_avista,dialog_customer_layout,et_troco_para,tv_troco_cliente,0);

                        if (isChecked) {
                            rbValorEntrega1.setVisibility(View.VISIBLE);
                            rbValorEntrega2.setVisibility(View.VISIBLE);
                            disableRadioButtonByCheckBox = false;

                        } else {

                            TextView tvSubTotal = (TextView) dialog_customer_layout.findViewById(R.id.tv_dialog_total);
                            total = Double.parseDouble(tvSubTotal.getText().toString());

                            if (rbValorEntrega1.isChecked())
                                total -= 1;
                            else
                                total -= 2;


                            tvSubTotal.setText(total+"");

                            rbValorEntrega1.setVisibility(View.INVISIBLE);
                            rbValorEntrega2.setVisibility(View.INVISIBLE);
                            rbValorEntrega1.setChecked(false);
                            rbValorEntrega2.setChecked(false);
                            disableRadioButtonByCheckBox = true;

                        }

                    }
                });

                rbValorEntrega1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked && !disableRadioButtonByCheckBox) {
                            rbValorEntrega2.setChecked(false);
                            tvTotalPedidoDialog.setText((total + 1) + "");
                            atualizaTrocoCliente(rb_forma_pagamento_avista,dialog_customer_layout,et_troco_para,tv_troco_cliente,1);
                        }
                    }
                });

                rbValorEntrega2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked && !disableRadioButtonByCheckBox) {
                            rbValorEntrega1.setChecked(false);
                            tvTotalPedidoDialog.setText((total + 2) + "");
                            atualizaTrocoCliente(rb_forma_pagamento_avista,dialog_customer_layout,et_troco_para,tv_troco_cliente,2);
                        }
                    }
                });


                //Sempre que um dos dois for clickado, vai deixar o outro  ao contrario
                //alem de deixar visível (a vista) ou invisível (cartão) as textviews acima.
                rb_forma_pagamento_avista.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {

                            try{
                                tv_troco_para.setVisibility(View.VISIBLE);
                                et_troco_para.setText("");
                                et_troco_para.setVisibility(View.VISIBLE);
                                tv_texto_cliente.setVisibility(View.VISIBLE);
                                tv_troco_cliente.setVisibility(View.VISIBLE);
                                rb_forma_pagamento_cartao.setChecked(false);

                            }catch (Exception ex){
                                Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                            }


                        }
                    }
                });

                rb_forma_pagamento_cartao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        try{

                            if (isChecked) {

                                tv_troco_para.setVisibility(View.INVISIBLE);
                                et_troco_para.setVisibility(View.INVISIBLE);
                                tv_texto_cliente.setVisibility(View.INVISIBLE);
                                tv_troco_cliente.setVisibility(View.INVISIBLE);
                                rb_forma_pagamento_avista.setChecked(false);
                                et_troco_para.setText("");

                            }

                        }catch (Exception ex){

                            Toast.makeText(getContext(),ex.getMessage(), Toast.LENGTH_LONG).show();

                        }

                    }
                });

                alertDialogBuilder.setView(dialog_customer_layout);

                alertDialogBuilder.setPositiveButton("Concluir Pedido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            int forma_pagamento;
                            int dia, mes, ano;

                            if (rb_forma_pagamento_avista.isChecked())
                                forma_pagamento = 1;
                            else
                                forma_pagamento = 2;

                            GregorianCalendar calendar = new GregorianCalendar();
                            dia = calendar.get(GregorianCalendar.DAY_OF_MONTH);
                            mes = calendar.get(GregorianCalendar.MONTH) + 1;
                            ano = calendar.get(GregorianCalendar.YEAR);

                            Venda newVenda = new Venda(dia, mes, ano, total, forma_pagamento);
                            newVenda.save();

                            //"cast" de long para int
                            Long idNovaVendaLong = newVenda.getId();
                            int idNovaVenda = Integer.valueOf(idNovaVendaLong.toString());

                            //muda o id da venda de todos os itens, porque agora eles fazem parte de uma venda realizada completamente.
                            ItemVendido.mudaIdItensAindaNaoFinalizados(0, idNovaVenda);

                            Toast.makeText(getContext(), "Venda Concluída com sucesso", Toast.LENGTH_SHORT).show();
                            //volta para main activity
                            startActivity(new Intent(getContext(), MainActivity.class));

                        } catch (Exception ex) {

                            Toast.makeText(getContext(), "Ocorreu algum erro :( Contate o administrador", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

                alertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        final RelativeLayout relativeLayout = (RelativeLayout) layoutView.findViewById(R.id.relativeLayout);

                        //pergunta se quer cancelar a venda, e então cancela
                        Snackbar snackbar = Snackbar
                                .make(relativeLayout, "Você deseja cancelar essa venda?", Snackbar.LENGTH_LONG)
                                .setAction("Sim", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        //0 é o id da venda que ainda não finalizou.
                                        List<ItemVendido> itensList = ItemVendido.getItensVendidosByVenda(0);

                                        //deleta os itens
                                        for(ItemVendido itemVendido : itensList)
                                            itemVendido.delete();

                                        Toast.makeText(getContext(), "Venda cancelada com sucesso.", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getContext(), MainActivity.class));
                                    }
                                });

                        snackbar.show();


                    }
                });

                AlertDialog dialog = alertDialogBuilder.create();
                dialog.show();

            }
        });

        return layoutView;
    }

    private void atualizaTrocoCliente(RadioButton rb_forma_pagamento_avista, View dialog_customer_layout, EditText et_troco_para, TextView tv_troco_cliente, int taxaEntrega ){


        //quando a pessoa apertar na taxa de entrega, ela vai estar deixando a caixa de texto, então eu faço a conta do cliente
        //se o radiobutton "À vista" estiver clicado.

        //a taxaEntrega é utilizada para atualizar a parte do troco do cliente também.
        if (rb_forma_pagamento_avista.isChecked()) {
            TextView tv_aviso = (TextView) dialog_customer_layout.findViewById(R.id.tv_dialog_aviso);

            try {

                dinheiroCliente = Double.parseDouble(et_troco_para.getText().toString());
                tv_troco_cliente.setVisibility(View.VISIBLE);

                if ((dinheiroCliente + taxaEntrega) < total) {

                    tv_aviso.setText("Dinheiro insuficiente");
                } else {

                    tv_troco_cliente.setText((dinheiroCliente - total) - taxaEntrega + "");
                }
            } catch (Exception ex) {

                tv_aviso.setText("Coloque apenas números.");
            }
        }

    }


}
