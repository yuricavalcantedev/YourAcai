package com.yuri.youracai.Dominio;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Yuri on 28/10/2016.
 */

@Table(name = "caixa")
public class Caixa extends Model{

    @Column(name = "caixa_aberto")
    private boolean caixa_aberto;

    @Column(name = "dia")
    private int dia;

    @Column(name = "mes")
    private int mes;

    @Column(name = "ano")
    private int ano;

    public Caixa(){}

    public Caixa(boolean caixa_aberto, int dia, int mes, int ano){

        this.caixa_aberto = caixa_aberto;
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;

    }

    public static boolean jaAberto(int dia, int mes, int ano){

        List<Caixa> objetoCaixa = new Select().from(Caixa.class).where("dia = " + dia+ " AND mes = "+ mes + " AND ano = "+ano).execute();

        //se for nulo, é porque o caixa não foi aberto ainda, então retorno false e deixo abrir o caixa.
        if(objetoCaixa == null)
            return false;

        return true;
    }

    public static int quantidadeProdutosVendaDoDia(int dia, int mes, int ano){

        return Venda.getAllVendasDia(dia,mes,ano);
    }

    public static int quantidadeVendaDia(int dia, int mes, int ano){

        return Venda.quantidadeVendasDiaSemProdutosDia(dia,mes,ano);
    }

    public static int quantidadeVendaTotal( ){

        return Venda.quantidadeVendasDiaSemProdutosTotal( );
    }
}
