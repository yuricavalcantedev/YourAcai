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

@Table(name = "Categoria")
public class Categoria extends Model{

    @Column(name = "nome")
    private String nome;

    public Categoria(){
        super();
    }

    public Categoria(String nome){
        super();
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static List<Categoria> getAllCategorias(){

        return new Select().from(Categoria.class).execute();
    }

    public static List<String> getNomeAllCategorias(){

        List<Categoria> list = new Select().from(Categoria.class).execute();
        List<String> listNomes = new ArrayList<>();

        for(int i = 0; i < list.size(); i++)
            listNomes.add(list.get(i).getNome());

        return listNomes;
    }

    public static String getNomeById(int id_categoria){

        List<Categoria> list = new Select().from(Categoria.class).where("id = "+id_categoria).execute();
        return list.get(0).getNome();

    }
}
