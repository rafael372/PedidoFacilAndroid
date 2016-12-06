package br.edu.fatectq.pedidofacil;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.edu.fatectq.pedidofacil.pessoas.Pessoa;
import br.edu.fatectq.pedidofacil.pessoas.PessoaDAO;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsuario;
    private EditText edtSenha;
    private Context ctx;
    private Intent it;
    public static final String URL_WS_CADASTROS = "http://192.168.110.182:8081/PedidoFacilWeb/rest/cadastros/";
    public static final String URL_WS_APONTAMENTOS = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ctx = this;

        CreateBanco cb = new CreateBanco(ctx);
        cb.fechar();


        edtUsuario = (EditText) findViewById(R.id.edtUsuario);

        edtSenha = (EditText) findViewById(R.id.edtSenha);
        edtSenha.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button bttEntrar = (Button) findViewById(R.id.bttEntrar);
        bttEntrar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button bttAtuCad = (Button) findViewById(R.id.btt_atu_cad);
        bttAtuCad.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                it = new Intent(ctx, CadastrosActivity.class);
                startActivity(it);
            }
        });
    }

    protected void onClickBttPesquisaUsuario(View v){
        it = new Intent(ctx, ConsultaLoginActivity.class);
        startActivityForResult(it,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle params = data != null ? data.getExtras():null;

        switch (requestCode){
            case 1:
                if(params != null){
                    if(resultCode == 1){
                        edtUsuario.setText(params.getString("CODIGO"));
                    }
                }
                break;
        }
    }

    private void attemptLogin() {
        edtUsuario.setError(null);
        edtSenha.setError(null);

        // Store values at the time of the login attempt.
        String usr = edtUsuario.getText().toString();
        String pwd = edtSenha.getText().toString();

        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(pwd)) {
            edtSenha.setError(getString(R.string.erro_campo_obrigatorio));
            focusView = edtSenha;
            cancel = true;
        }
        if (TextUtils.isEmpty(usr)) {
            edtUsuario.setError(getString(R.string.erro_campo_obrigatorio));
            focusView = edtUsuario;
            cancel = true;
        }
        try {
            PessoaDAO pesDAO = new PessoaDAO(ctx);
            Pessoa p = pesDAO.getPessoa(Integer.parseInt(usr));
            if (p != null){
                if (p.getSenha().equals(pwd)){
                    Intent it = new Intent(ctx, MesasActivity.class);
                    startActivity(it);
                } else {
                    edtSenha.setError(getString(R.string.erro_senha_invalida));
                    focusView = edtSenha;
                    cancel = true;
                }
            }
            pesDAO.fechar();
        }catch (NumberFormatException nEx){
            edtUsuario.setError(getString(R.string.erro_usuario_invalido));
            focusView = edtUsuario;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        }
    }
}

