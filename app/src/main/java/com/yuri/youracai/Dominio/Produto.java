package com.yuri.youracai.Dominio;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuri on 13/10/2016.
 */

@Table(name = "Produto")
public class Produto extends Model {

    @Column(name = "nome")
    private String nome;

    @Column(name = "preco")
    private double preco;

    @Column(name = "id_categoria")
    private int id_categoria;

    @Column(name = "quantidade")
    private int quantidade;

    public Produto(){
        super();
    }

    public Produto(String nome, double preco, int id_categoria){
        super();
        this.nome = nome;
        this.preco = preco;
        this.quantidade = 1;
        this.id_categoria = id_categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public static List<Produto> getAllProdutos(){

        return new Select().from(Produto.class).execute();

    }

    public static List<Produto> getAllProdutosByCategoria(int id_categoria){

        List<Produto> listaProdutos = new Select().from(Produto.class).where("id_categoria = "+id_categoria).execute();

        return listaProdutos;

    }

    public static List<String> getAllNameProdutosByCategoria(int id_categoria){

        List<Produto> listaProdutos = new Select().from(Produto.class).where("id_categoria = "+id_categoria).execute();
        List<String> listaNomeProdutos = new ArrayList<>();

        for(Produto p : listaProdutos)
            listaNomeProdutos.add(p.getNome());

        return listaNomeProdutos;

    }

    public static Produto getProdutoByName(String productName){

        List<Produto> produto = new Select().from(Produto.class).where("nome = '"+productName+"'").execute();

        return produto.get(0);
    }

}
