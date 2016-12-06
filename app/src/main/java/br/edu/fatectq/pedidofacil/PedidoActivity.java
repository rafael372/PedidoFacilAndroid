package br.edu.fatectq.pedidofacil;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.edu.fatectq.pedidofacil.pedido.Pedido;
import br.edu.fatectq.pedidofacil.pedido.PedidoDAO;
import br.edu.fatectq.pedidofacil.produtos.Produto;
import br.edu.fatectq.pedidofacil.produtos.ProdutoDAO;

public class PedidoActivity extends AppCompatActivity {

    private Context ctx;
    private Intent it;
    private ListView lstItens;
    private ArrayAdapter<String> itensArrayAdapter;
    private Handler handler;
    private Integer mesa = 0;
    private List<Pedido> lstPed = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        handler = new Handler();
        ctx = this;

        itensArrayAdapter = new ArrayAdapter<String>(this, R.layout.msg_status);
        lstItens = (ListView) findViewById(R.id.lstItens);
        lstItens.setAdapter(itensArrayAdapter);

        lstItens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            mesa = Integer.parseInt(extras.getString("MESA"));
        }
        this.setTitle("Pedido Mesa: " + mesa.toString());
    }

    protected void onClickBttIncluir(View v){
        it = new Intent(ctx, ConsultaCategoriaActivity.class);
        startActivityForResult(it, 1);
    }

    protected void onClickbttSalvar(View v){
        if (lstPed != null){
            PedidoDAO pedDAO = new PedidoDAO(ctx);
            pedDAO.begin();
            for(Pedido p : lstPed){
                pedDAO.salvar(p);
            }
            pedDAO.commit();
        }
        finish();
    }

    private void escreveTexto(final String mensagem) {
        handler.post(new Runnable() {
            public void run() {
                itensArrayAdapter.add(mensagem);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle params = data != null ? data.getExtras():null;

        switch (requestCode) {
            case 1:
                if (params != null) {
                    if (resultCode == 1) {
                        Pedido p = new Pedido();
                        p.setCateg(Integer.parseInt(params.getString("CATEG")));
                        p.setProd(Integer.parseInt(params.getString("PROD")));
                        p.setQtd(Float.parseFloat(params.getString("QTD")));
                        p.setMesa(mesa);

                        //lstPed.add(p);

                        ProdutoDAO prodDAO = new ProdutoDAO(ctx);
                        Produto prod = prodDAO.getProduto(p.getProd());
                        escreveTexto(prod.getNome() + "    - " + p.getQtd());
                    }
                }
                break;
        }
    }
}
