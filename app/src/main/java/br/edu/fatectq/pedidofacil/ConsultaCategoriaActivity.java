package br.edu.fatectq.pedidofacil;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.edu.fatectq.pedidofacil.produtos.Categoria;
import br.edu.fatectq.pedidofacil.produtos.CategoriaDAO;

public class ConsultaCategoriaActivity extends ListActivity {

    private List<Categoria> lstCateg;
    private Categoria categ = null;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ctx = this;

        CategoriaDAO categDAO = new CategoriaDAO(this);
        lstCateg = categDAO.listar();

        if(lstCateg == null)
            lstCateg = new ArrayList<Categoria>();

        setListAdapter(new ListAdapterCategoria(this, lstCateg));
        categDAO.fechar();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        categ = lstCateg.get(position);
        Intent it = new Intent(ctx, ConsultaProdutoActivity.class);
        it.putExtra("CATEG", categ.getCodigo().toString());
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
                        it.putExtra("CATEG", categ.getCodigo().toString());
                        it.putExtra("PROD", params.getString("CODIGO"));
                        it.putExtra("QTD", params.getString("QTD"));

                        setResult(1, it);
                        finish();
                    }
                }
                break;
        }
    }
}
