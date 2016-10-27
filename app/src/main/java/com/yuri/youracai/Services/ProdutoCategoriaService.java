package com.yuri.youracai.Services;

import android.content.Context;

import com.yuri.youracai.Dominio.Produto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Yuri on 12/10/2016.
 */

public class ProdutoCategoriaService {


    public static HashMap<String, List<String>> getProdutosData(Context context){

        HashMap<String, List<String>> expandableListDetail = new HashMap<>();

        //pega todos os produtos de todas as categorias
        List<String> produtos_acai = Produto.getAllNameProdutosByCategoria(1);
        List<String> produtos_creme = Produto.getAllNameProdutosByCategoria(2);
        List<String> produtos_sanduiche = Produto.getAllNameProdutosByCategoria(3);
        List<String> produtos_salada_fruta = Produto.getAllNameProdutosByCategoria(4);
        List<String> produtos_adicional_fruta = Produto.getAllNameProdutosByCategoria(5);
        List<String> produtos_adicional_calda = Produto.getAllNameProdutosByCategoria(6);
        List<String> produtos_adicional_condimentos = Produto.getAllNameProdutosByCategoria(7);


        expandableListDetail.put("Açais", produtos_acai);
        expandableListDetail.put("Cremes", produtos_creme);
        expandableListDetail.put("Sanduíches Naturais", produtos_sanduiche);
        expandableListDetail.put("Saladas de Fruta", produtos_salada_fruta);
        expandableListDetail.put("Adicionais de Fruta", produtos_adicional_fruta);
        expandableListDetail.put("Adicionais de Calda", produtos_adicional_calda);
        expandableListDetail.put("Adicionais Condimentos", produtos_adicional_condimentos);

        return expandableListDetail;
    }


}
