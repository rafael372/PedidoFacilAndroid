package br.edu.fatectq.pedidofacil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MesasActivity extends AppCompatActivity {

    Intent it;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesas);

        ctx = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    protected void onClickMesa(View v){
        String tag = v.getTag().toString();
        it = new Intent(ctx, PedidoActivity.class);
        it.putExtra("MESA", tag);
        startActivity(it);
    }
}
