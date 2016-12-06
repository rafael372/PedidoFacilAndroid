package br.edu.fatectq.pedidofacil;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class QtdProdActivity extends AppCompatActivity {

    private EditText edtQtd;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qtd_prod);

        ctx = this;

        edtQtd = (EditText) findViewById(R.id.edtQtd);

    }

    protected void onClickbttAdd (View v){
        edtQtd.setError(null);

        String qtd = edtQtd.getText().toString();

        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(qtd)) {
            edtQtd.setError("Informe a quantidade!");
            focusView = edtQtd;
            cancel = true;
        }

        Intent it = new Intent();
        it .putExtra("QTD", qtd);
        setResult(1, it);
        finish();

        if (cancel) {
            focusView.requestFocus();
        }
    }
}
