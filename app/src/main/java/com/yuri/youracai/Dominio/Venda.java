package com.yuri.youracai.Dominio;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuri on 17/10/2016.
 */

// pagamento = 1 -> À vista
// pagamento = 2 -> Cartão

@Table(name = "Venda")
public class Venda extends Model {

    @Column(name = "dia")
    private int dia;

    @Column(name = "mes")
    private int mes;

    @Column(name = "ano")
    private int ano;

    @Column(name = "total")
    private double total;

    @Column(name = "pagamento")
    private int pagamento;

    private List<String> listaCategorias;

    private List<ItemVendido> listaItensVendidos;

    public Venda(){
        super();
    }

    public Venda(int dia, int mes, int ano, double total, int pagamento){

        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        this.total = total;
        this.pagamento = pagamento;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getPagamento() {
        return pagamento;
    }

    public void setPagamento(int pagamento) {
        this.pagamento = pagamento;
    }

    public List<String> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<String> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public List<ItemVendido> getListaItensVendidos() {

        //se não estanciar a lista aqui, vai dar erro dentro do for.
        listaCategorias = new ArrayList<>();

        //"cast" de long para int
        Long idNovaVendaLong =  getId();
        int iVenda = Integer.valueOf(idNovaVendaLong.toString());
        this.listaItensVendidos = ItemVendido.getItensVendidosByVenda(iVenda);

        //seta também a categoria da venda
        for(ItemVendido itemVendido : listaItensVendidos)
            if(!listaCategorias.contains(itemVendido.getNomeCategoriaById()))
                listaCategorias.add(itemVendido.getNomeCategoriaById());

        return listaItensVendidos;
    }

    public void setListaItensVendidos(List<ItemVendido> listaItensVendidos) {
        this.listaItensVendidos = listaItensVendidos;
    }

    public static List<Venda> getVendasByData(int dia, int mes, int ano){

        return new Select().from(Venda.class).where("dia = "+ dia).and("mes = "+ mes).and("ano = " + ano).execute();

    }

    //aqui na verdade é a quantidade de produtos.
    public static int getAllVendasDia(int dia, int mes, int ano){

        List<Venda> listVenda = new Select().from(Venda.class).where("dia = "+ dia).and("mes = "+ mes).and("ano = " + ano).execute();
        int qtdItens = 0;

        for(int i = 0; i < listVenda.size(); i++)
            qtdItens+= listVenda.get(i).getListaItensVendidos().size();

        return qtdItens;
    }

    public static int quantidadeVendasDiaSemProdutosDia(int dia, int mes, int ano){

        List<Venda> listVenda = new Select().from(Venda.class).where("dia = "+ dia).and("mes = "+ mes).and("ano = " + ano).execute();


        return listVenda.size();
    }

    public static int quantidadeVendasDiaSemProdutosTotal( ){

        List<Venda> listVenda = new Select().from(Venda.class).execute();


        return listVenda.size();
    }

}
