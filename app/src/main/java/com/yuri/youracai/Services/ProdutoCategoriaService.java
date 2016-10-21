package com.yuri.youracai.Services;

import android.content.Context;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Yuri on 12/10/2016.
 */

public class ProdutoCategoriaService {


    public static HashMap<String, List<String>> getProdutosData(Context context){

        HashMap<String, List<String>> expandableListDetail = new HashMap<>();

//        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
//        databaseAccess.open();
//
//        List<String> categoria_acai = databaseAccess.getProdutosPorCategoria(1);
//        List<String> acai = new ArrayList<>();
//        for(int i = 0; i < categoria_acai.size(); i++)
//            acai.add(categoria_acai.get(i));
//
//        List<String> categoria_cremes = databaseAccess.getProdutosPorCategoria(2);
//        List<String> cremes = new ArrayList<>();
//        for(int i = 0; i < categoria_cremes.size(); i++)
//            cremes.add(categoria_cremes.get(i));
//
//
//        List<String> categoria_sanduiche = databaseAccess.getProdutosPorCategoria(3);
//        List<String> sanduiche = new ArrayList<>();
//        for(int i = 0; i < categoria_sanduiche.size(); i++)
//            sanduiche.add(categoria_sanduiche.get(i));
//
//        List<String> categoria_salada_fruta = databaseAccess.getProdutosPorCategoria(4);
//        List<String> salada_fruta = new ArrayList<>();
//        for(int i = 0; i < categoria_salada_fruta.size(); i++)
//            salada_fruta.add(categoria_salada_fruta.get(i));
//
//        List<String> categoria_adicional_fruta = databaseAccess.getProdutosPorCategoria(5);
//        List<String> adicional_fruta = new ArrayList<>();
//        for(int i = 0; i < categoria_adicional_fruta .size(); i++)
//            adicional_fruta.add(categoria_adicional_fruta.get(i));
//
//        List<String> categoria_adicional_calda  = databaseAccess.getProdutosPorCategoria(6);
//        List<String> adicional_calda = new ArrayList<>();
//        for(int i = 0; i < categoria_adicional_calda.size(); i++)
//            adicional_calda.add(categoria_adicional_calda.get(i));
//
//        List<String> categoria_adicional_condimentos  = databaseAccess.getProdutosPorCategoria(7);
//        List<String> adicional_condimento = new ArrayList<>();
//        for(int i = 0; i < categoria_adicional_condimentos.size(); i++)
//            adicional_condimento.add(categoria_adicional_condimentos.get(i));
//
//        expandableListDetail.put("Açais", acai);
//        expandableListDetail.put("Cremes", cremes);
//        expandableListDetail.put("Sanduíches Naturais", sanduiche);
//        expandableListDetail.put("Saladas de Fruta", salada_fruta);
//        expandableListDetail.put("Adicionais de Fruta", adicional_fruta);
//        expandableListDetail.put("Adicionais de Calda", adicional_calda);
//        expandableListDetail.put("Adicionais Condimentos", adicional_condimento);

        return expandableListDetail;
    }


}
