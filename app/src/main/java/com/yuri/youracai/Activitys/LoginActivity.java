package com.yuri.youracai.Activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.yuri.youracai.Dominio.Caixa;
import com.yuri.youracai.Dominio.Categoria;
import com.yuri.youracai.Dominio.Funcionario;
import com.yuri.youracai.Dominio.Produto;
import com.yuri.youracai.R;

import java.util.GregorianCalendar;


public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;

        //I need this line to the 'Active Android' works
        ActiveAndroid.initialize(this);

        sharedPreferences = context.getSharedPreferences(getString(R.string.preference_fez_cadastro),MODE_PRIVATE);
        boolean funcionario_cadastrado = sharedPreferences.getBoolean("Cadastrado",false);

        TextView tvCadastreAqui = (TextView) findViewById(R.id.tv_cadastre_aqui);
        tvCadastreAqui.setOnClickListener(onClickCadastreAqui);

        if(funcionario_cadastrado){
            TextView tv_cadastrado = (TextView) findViewById(R.id.tv_cadastrado_login);
            tv_cadastrado.setText("Esqueceu seus dados?");

            tvCadastreAqui.setOnClickListener(onClickEsqueceuDados);

        }

        //enche o banco na primeira vez que o app for executado.
        sharedPreferences = context.getSharedPreferences(getString(R.string.preference_fill_banco),MODE_PRIVATE);
        boolean app_executado = sharedPreferences.getBoolean("Executado",false);

        //na primeira vez que for executado, ele entra no if, depois não entra mais.
        if(!app_executado) {

            fillBancoPrimeiraVez();
            editor = sharedPreferences.edit();
            editor.putBoolean("Executado",true);
            editor.commit();

        }



        Button btLogin = (Button)findViewById(R.id.bt_login);
        btLogin.setOnClickListener(onClickLogin);

        CheckBox cbSalvarInformacoes = (CheckBox)findViewById(R.id.cb_salvar_informacoes);
        cbSalvarInformacoes.setOnCheckedChangeListener(onCheckedSalvarInformacoes);

        //se o usuário ainda não tiver alterado nenhuma vez o valor, o valor padrão é false.
        sharedPreferences = context.getSharedPreferences(getString(R.string.preference_login),MODE_PRIVATE);
        boolean opcUsuario = sharedPreferences.getBoolean("Salvar",false);

        //se não for verdade, não precisa mudar nada, por que por padrão a view já inicia desabilitada
        if(opcUsuario){

            cbSalvarInformacoes.setChecked(true);

            TextInputLayout tilLogin = (TextInputLayout) findViewById(R.id.til_login);
            TextInputLayout tilSenha = (TextInputLayout) findViewById(R.id.til_senha);

            //se não entrar no if, é porque é a primeira vez que o app é aberto e ainda não há funcionário cadastrado.
            Funcionario funcionario = Funcionario.load(Funcionario.class,1);
            if(funcionario != null) {
                tilLogin.getEditText().setText(funcionario.getLogin());
                tilSenha.getEditText().setText(funcionario.getSenha());
            }
        }

    }

    //listener "CADASTRE-SE AQUI"
    private View.OnClickListener onClickCadastreAqui = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(LoginActivity.this,CadastroFuncionarioActivity.class));
        }
    };

    //listener "ESQUECEU OS DADOS"
    private View.OnClickListener onClickEsqueceuDados = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(LoginActivity.this,EsqueceuLogin.class));
        }
    };

    //listener "LOGIN"
    private View.OnClickListener onClickLogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


                TextInputLayout tilLogin = (TextInputLayout) findViewById(R.id.til_login);
                TextInputLayout tilSenha = (TextInputLayout) findViewById(R.id.til_senha);

                String login = tilLogin.getEditText().getText().toString();
                String senha = tilSenha.getEditText().getText().toString();

                //se não entrar no if, é porque é a primeira vez que o app é aberto e ainda não há funcionário cadastrado.
                Funcionario funcionario = Funcionario.load(Funcionario.class, 1);
                if (funcionario != null) {
                    boolean login_correto = true;

                    if (!funcionario.getLogin().equals(login)) {
                        tilLogin.setError("Login errado. Tente novamente.");
                        login_correto = false;

                    }
                    if (!funcionario.getSenha().equals(senha)) {
                        tilSenha.setError("Senha está errada. Tente se lembrar :)");
                        login_correto = false;

                    }
                    if (login_correto)
                        startActivity(new Intent(context, MainActivity.class));
                } else {

                    if (login.length() == 0) {
                        tilLogin.setError("Login errado. Tente novamente.");
                    }
                    if (senha.length() == 0) {
                        tilSenha.setError("Senha está errada. Tente se lembrar :)");
                    }
            }

        }
    };

    //listener "SALVAR INFORMAÇÕES "
    private CompoundButton.OnCheckedChangeListener onCheckedSalvarInformacoes = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            sharedPreferences = context.getSharedPreferences(getString(R.string.preference_login),MODE_PRIVATE);
            editor = sharedPreferences.edit();

            if(isChecked)
                editor.putBoolean("Salvar",true);
            else
                editor.putBoolean("Salvar",false);

            editor.commit();


        }
    };

    private void fillBancoPrimeiraVez(){

        String[] listaCategoria = new String[]{
                "Açai","Creme","Sanduíche Natural","Salada de Fruta",
                "Adicional de Fruta","Adicional de Calda","Adicional Condimento" };

        for(int i = 0; i< listaCategoria.length; i++)
            new Categoria(listaCategoria[i]).save();

        String[] listaProdutosNome = new String[]{

                "COPO 400 ML",
                "COPO 500 ML SIMPLES", "COPO 500 ML COMPLETO", "COPO 500 ML ESPECIAL", "CASADINHO NO POTE 380 ML","CASADINHO NO POTE 500 ML",
                "TRIO DE SABORES POTE 380 ML", "TRIO DE SABORES POTE 500 ML", "TRIO DE SABORES POTE 1 L", "TROPICAL NO POTE 250 ML", "TROPICAL NO POTE 380 ML",
                "TROPICAL NO POTE 500 ML", "POTE 500 ML PURO","POTE 500 ML COM XAROPE",  "POTE 1 L PURO", "POTE 1 L COM XAROPE",
                "POTE COMPLETO BATIDO COM XAROPE E LEITE 1 L",

                "CREME DE NINHO 100 ML","CREME DE NINHO 140 ML","CREME DE NINHO 250 ML",
                "CREME DE NINHO 1 L","CREME DE AMENDOIM 100 ML","CREME DE AMENDOIM 140 ML","CREME DE AMENDOIM 250 ML", "CREME DE MARACUJÁ 100 ML",
                "CREME DE MARACUJÁ 140 ML", "CREME DE OVOMALTINE 100 ML","CREME DE OVOMALTINE 140 ML", "CREME DE CUPUAÇU 100 ML", "CREME DE CUPUAÇU 140 ML",
                "CREME DE CUPUAÇU 250 ML", "CREME DE CUPUAÇU + MORANGO 250 ML", "CREME DE CUPUAÇU 1 L",

                "TRAD. ATUM","TRAD. FRANGO COM MILHO", "TRAD. FRANGO COM REQUEIJÃO","TRAD. PEITO DE PERU","GOUR. FRANGO COM TOMATE",
                "GOUR. PERU COM MOLHO ITALIANO", "GOUR. PERU COM CHEDAR E QUEIJO MINAS",

                "SALADA DE POTE 250 ML", "SALADA DE POTE 380 ML",
                "SALADA DE POTE 500 ML", "SALADA DE POTE 780 ML", "SALADA DE POTE 1 L",

                "BANANA", "MORANGO", "MANGA", "MAÇA",
                "KIWI","UVA PASSA", "CEREJA","UVA SEM CAROÇO",

                "CHOCOLATE",  "MORANGO", "LEITE CONDENSADO",

                "AMENDOIM","AVEIA",
                "BIS PRETO", "BIS BRANCO", "BATOM PRETO", "BATOM BRANCO", "COCO RALADO",
                "CEREAIS", "CHOCOPOWER BALL","NUTELLA", "SERENATA DE AMOR", "DSIQUETI",
                "FLOCOS DE TAPÍOCA", "FLOCOS DE ARROZ", "PAÇOQUITA", "OVOMALTINE", "FARINHA LÁCTEA",
                "GRANOLA", "LEITE NINHO","NESTON", "GOSTAS DE CHOCOLATE", };

        double [] listaPrecosProdutos = new double[]{

                6,7,8,9,10,
                12,11,14,28,6.5,
                9,13,8,8,15,
                15,19,

                2.5,3.5,6.5,28,2.5,
                3.5,6.5,2.5,3.5,2.5,
                3.5,2.5,3.5,6.5,9.5,
                28,

                5.5,5.5,5.5,5.5,6,
                6,6,

                5.5,8,10.5,16,27,

                1.5,3,1.5,1.5,3,
                2,2.5,3,

                1.5,1.5,2,

                1.5,1,1.5, 1.5,
                1.5, 1.5,1,1.5,1.5, 2.5,
                1.5,1.5,1.5,1.5,1,2,
                1.5,1.5, 2, 1.5,1.5,

        };

        int [] listaIdCategoriaProdutos = new int[]{
                1,1,1,1,1,
                1,1,1,1,1,
                1,1,1,1,1,
                1,1,

                2,2,2,
                2,2,2,2,2,
                2,2,2,2,2,
                2,2,2,

                3,3,3,3,3,
                3,3,

                4,4,4,4,4,

                5,5,5,5,5,
                5,5,5,

                6,6,6,

                7,7,
                7,7,7,7,7,
                7,7,7,7,7,
                7,7,7,7,7,
                7,7,7,7 };

        // using transaction increase the speed of inserts a lot.
        ActiveAndroid.beginTransaction();
        try {

            for (int i = 0; i < listaProdutosNome.length; i++)
                new Produto(listaProdutosNome[i], listaPrecosProdutos[i], listaIdCategoriaProdutos[i]).save();

            ActiveAndroid.setTransactionSuccessful();

        }catch (Exception ex){

            Toast.makeText(context, "Um ou mais itens não foram inseridos corretamente. Adicione-os novamente.", Toast.LENGTH_SHORT).show();

        }finally {

            ActiveAndroid.endTransaction();
        }

    }

    private void aumentaLayoutLogin(){

        RelativeLayout relativeLayoutLogin = (RelativeLayout)findViewById(R.id.activity_login);
        relativeLayoutLogin.setMinimumHeight(390);
    }

    private void diminuiLayoutLogin(){

        RelativeLayout relativeLayoutLogin = (RelativeLayout)findViewById(R.id.activity_login);
        relativeLayoutLogin.setMinimumHeight(330);

    }
}
