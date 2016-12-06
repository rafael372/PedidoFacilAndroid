package br.edu.fatectq.pedidofacil;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.edu.fatectq.pedidofacil.produtos.Produto;
import br.edu.fatectq.pedidofacil.produtos.ProdutoDAO;


public class ConsultaProdutoActivity extends ListActivity {

    private List<Produto> lstProd;
    private Produto prod = null;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ctx = this;

        ProdutoDAO prodDAO = new ProdutoDAO(this);
        int categ = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            categ = Integer.parseInt(extras.getString("CATEG"));
        }

        lstProd = prodDAO.listar(categ);

        if(lstProd == null)
            lstProd = new ArrayList<Produto>();

        setListAdapter(new ListAdapterProduto(this, lstProd));
        prodDAO.fechar();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        prod = lstProd.get(position);
        Intent it = new Intent(ctx, QtdProdActivity.class);
        startActivityForResult(it,1);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle params = data != null ? data.getExtras():null;

        switch (requestCode) {
            case 1:
                if (params != null) {
                    if (resultCode == 1) {
                        Intent it = new Intent();
                        it .putExtra("CODIGO", String.valueOf(prod.getCodigo()));
                        it .putExtra("QTD", params.getString("QTD"));
                        setResult(1, it);
                        finish();
                    }
                }
                break;
        }
    }
}
