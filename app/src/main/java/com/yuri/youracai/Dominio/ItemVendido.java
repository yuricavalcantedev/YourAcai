package com.yuri.youracai.Dominio;


import android.content.ClipData;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuri on 17/10/2016.
 */

//quando for instanciado um "ItemVendido" com o id_venda = 0. É porque é o pedido que ainda não finalizou. Ou seja, o usuário ainda
//está adicionando os pedidos na venda.

@Table(name = "ItemVendido")
public class ItemVendido extends Model {

    @Column(name = "id_venda")
    private int id_venda;

    @Column(name = "nome_item")
    private String nomeItem;

    @Column(name = "preco_item")
    private double precoItem;

    @Column(name = "id_categoria_item")
    private int idCategoriaItem;

    @Column(name = "quantidade_item")
    private int quantidadeItem;

    public ItemVendido() {
        super();
    }

    public ItemVendido(int id_venda, String nomeItem, double precoItem, int idCategoriaItem, int quantidadeItem) {

        super();
        this.id_venda = id_venda;
        this.nomeItem = nomeItem;
        this.precoItem = precoItem;
        this.idCategoriaItem = idCategoriaItem;
        this.quantidadeItem = quantidadeItem;
    }

    public int getId_venda() {
        return id_venda;
    }

    public void setId_venda(int id_venda) {
        this.id_venda = id_venda;
    }

    public String getNomeItem() {
        return nomeItem;
    }

    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }

    public double getPrecoItem() {
        return precoItem;
    }

    public void setPrecoItem(double precoItem) {
        this.precoItem = precoItem;
    }

    public int getIdCategoriaItem() {
        return idCategoriaItem;
    }

    public void setIdCategoriaItem(int idCategoriaItem) {
        this.idCategoriaItem = idCategoriaItem;
    }

    public int getQuantidadeItem() {
        return quantidadeItem;
    }

    public void setQuantidadeItem(int quantidadeItem) {
        this.quantidadeItem = quantidadeItem;
    }

    public static List<ItemVendido> getItensVendidosByVenda(int id_venda) {

        return new Select().from(ItemVendido.class).where("id_venda = " + id_venda).execute();
    }

    public static void mudaIdItensAindaNaoFinalizados(int id_venda_antigo, int id_venda_novo) {

        List<ItemVendido> listItensAntigoIdVenda = new Select().from(ItemVendido.class).where("id_venda = " + id_venda_antigo).execute();

        for(ItemVendido itemVendido : listItensAntigoIdVenda) {
            itemVendido.setId_venda(id_venda_novo);
            itemVendido.save();
            itemVendido.getNomeCategoriaById();
        }
    }

    public String getNomeCategoriaById( ){

        return Categoria.getNomeById( idCategoriaItem);
    }

    public static void deletaItemSelecionado(String nomeItem){

        List<ItemVendido> itemVendidoList = new Select().from(ItemVendido.class).where("id_venda = 0 AND nome_item = '" + nomeItem+"'").execute();
        ItemVendido itemVendido = itemVendidoList.get(0);
        itemVendido.delete();

    }
}