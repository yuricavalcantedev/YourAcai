package com.yuri.youracai.Activitys;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuri.youracai.Dominio.Funcionario;
import com.yuri.youracai.R;

import org.w3c.dom.Text;

import static com.activeandroid.Cache.getContext;

public class EsqueceuLogin extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueceu_login);

        Button btRecuperar = (Button) findViewById(R.id.bt_recuperar);
        btRecuperar.setOnClickListener(onClickRecuperar);


    }

    private View.OnClickListener onClickRecuperar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String codigo = ((EditText) findViewById(R.id.et_codigo_esqueceu_login)).getText().toString();

            Funcionario funcionario = Funcionario.load(Funcionario.class, 1);
            //se o código for correto, eu mostro um dialog personalizado com os dados dele.

                if (codigo.equals(funcionario.getCodigo())) {

                    LayoutInflater layoutInflater = EsqueceuLogin.this.getLayoutInflater();
                    final View dialog_customer_layout = layoutInflater.inflate(R.layout.dialog_esqueceu_dados,null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EsqueceuLogin.this);
                    alertDialogBuilder.setView(dialog_customer_layout);

                    //eu coloco o login e senha nos campos de texto para o usuário ver.
                    TextView tvLogin = (TextView) dialog_customer_layout.findViewById(R.id.tv_login_esquecido);
                    TextView tvSenha = (TextView) dialog_customer_layout.findViewById(R.id.tv_senha_esquecida);

                    tvLogin.setText(funcionario.getLogin());
                    tvSenha.setText(funcionario.getSenha());

                    alertDialogBuilder.setPositiveButton("Ir Tela de Login", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            startActivity(new Intent(EsqueceuLogin.this, LoginActivity.class));
                        }
                    });

                    AlertDialog dialog = alertDialogBuilder.create();
                    dialog.show();

                    //se não, mostro um snackbar falando pra ele tentar novamente
                } else {
                    final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_esqueceu_login);

                    Snackbar snackbar = Snackbar
                            .make(relativeLayout, "Você inseriu um código errado.", Snackbar.LENGTH_LONG)
                            .setAction("Tentar novamente", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    EditText et_codigo = (EditText) findViewById(R.id.et_codigo_esqueceu_login);
                                    et_codigo.setText("");
                                }
                            });

                    snackbar.show();
                }
        }
    };

}