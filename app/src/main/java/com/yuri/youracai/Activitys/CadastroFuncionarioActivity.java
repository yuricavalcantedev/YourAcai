package com.yuri.youracai.Activitys;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yuri.youracai.Dominio.Funcionario;
import com.yuri.youracai.Dominio.Produto;
import com.yuri.youracai.R;

import java.util.Date;
import java.util.List;


public class CadastroFuncionarioActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_funcionario);

        context = this;

        //mapeio todos os 'TIL'
        TextInputLayout tilCodigo = (TextInputLayout) findViewById(R.id.til_codigo);
        TextInputLayout tilLogin = (TextInputLayout) findViewById(R.id.til_login_cadastro);
        TextInputLayout tilNome = (TextInputLayout) findViewById(R.id.til_nome);
        TextInputLayout tilSenha = (TextInputLayout) findViewById(R.id.til_senha_cadastro);
        TextInputLayout tilDataNascimento = (TextInputLayout) findViewById(R.id.til_data_nascimento);

        final RadioButton rb_masculino = (RadioButton)findViewById(R.id.rb_masculino);
        rb_masculino.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                rb_masculino.setChecked(isChecked);
                RadioButton rb_feminino= (RadioButton) findViewById(R.id.rb_feminino);
                rb_feminino.setChecked(!isChecked);

            }
        });

        final RadioButton rb_feminino = (RadioButton)findViewById(R.id.rb_feminino);
        rb_feminino.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                rb_feminino.setChecked(isChecked);
                RadioButton rb_masculino= (RadioButton) findViewById(R.id.rb_masculino);
                rb_masculino.setChecked(!isChecked);

            }
        });


        Button bt_cadastrar = (Button) findViewById(R.id.bt_cadastrar_funcionario);
        bt_cadastrar.setOnClickListener(onClickCadastreAqui);


    }

    //listener "CADASTRAR"
    private View.OnClickListener onClickCadastreAqui = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            boolean infoValidadas = true;

            //mapeio todos os 'TIL'
            TextInputLayout tilCodigo = (TextInputLayout) findViewById(R.id.til_codigo);
            TextInputLayout tilLogin = (TextInputLayout) findViewById(R.id.til_login_cadastro);
            TextInputLayout tilNome = (TextInputLayout) findViewById(R.id.til_nome);
            TextInputLayout tilSenha = (TextInputLayout) findViewById(R.id.til_senha_cadastro);
            TextInputLayout tilDataNascimento = (TextInputLayout) findViewById(R.id.til_data_nascimento);

            String codigo = tilCodigo.getEditText().getText().toString();
            String nome = tilNome.getEditText().getText().toString();
            String login = tilLogin.getEditText().getText().toString();
            String senha = tilSenha.getEditText().getText().toString();
            String data_nascimento = tilDataNascimento.getEditText().getText().toString();
            String sexo;


            RadioButton rb_masculino = (RadioButton)findViewById(R.id.rb_masculino);
            if(rb_masculino.isChecked())
                sexo = "Masculino";
            else
                sexo = "Feminino";

            //validações

            if(nome.length() < 4) {
                tilNome.setError("Nome muito curto");
                TextView tvNome = (TextView)findViewById(R.id.tv_watcher_nome);
                tvNome.setText("");
                infoValidadas = false;
            }
            //#F25A - funcionário - //#A01M - administrador
            if(!codigo.equals("#F25A") && !codigo.equals("#A01M")) {
                tilCodigo.setError("Código errado");
                TextView tvCodigo = (TextView) findViewById(R.id.tv_watcher_codigo);
                tvCodigo.setText("");
                infoValidadas = false;
            }
            if(login.length() < 4){
                tilLogin.setError("Login muito curto");
                TextView tvLogin = (TextView)findViewById(R.id.tv_watcher_login);
                tvLogin.setText("");
                infoValidadas = false;
            }

            if(senha.length() < 4){
                tilSenha.setError("Senha muito curta");
                TextView tvSenha = (TextView)findViewById(R.id.tv_watcher_senha);
                tvSenha.setText("");
                infoValidadas = false;
            }


            //TODO : Verificação da data
            if(infoValidadas){

                Funcionario funcionario = new Funcionario(codigo,nome,login,senha,data_nascimento,sexo);
                funcionario.save();

                //esse shared preferences eu uso na tela de login para que eu possa mudar a mensagem depois de a pessoa ter se cadastrado
                SharedPreferences sharedPreferences =  context.getSharedPreferences(getString(R.string.preference_fez_cadastro),MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("Cadastrado",true);
                editor.commit();

                showDialogConfirmandoLogin();
            }
        }
    };

    private void showDialogConfirmandoLogin(){

        AlertDialog.Builder builder = new AlertDialog.Builder(CadastroFuncionarioActivity.this);
        builder.setTitle("Cadastro criado com sucesso.");
        builder.setMessage("Informe seu login e senha sempre que for entrar no app.");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                startActivity(new Intent(CadastroFuncionarioActivity.this,MainActivity.class));
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }



}
