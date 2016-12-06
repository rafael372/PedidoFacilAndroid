package br.edu.fatectq.pedidofacil;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.edu.fatectq.pedidofacil.pessoas.Pessoa;
import br.edu.fatectq.pedidofacil.pessoas.PessoaDAO;

public class ConsultaLoginActivity extends ListActivity {
    private List<Pessoa> lstPess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PessoaDAO pessDAO = new PessoaDAO(this);
        lstPess = pessDAO.listar();

        if(lstPess == null)
            lstPess = new ArrayList<Pessoa>();

        setListAdapter(new ListAdapterPessoa(this, lstPess));
        pessDAO.fechar();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Pessoa pes = lstPess.get(position);

        Intent it = new Intent();
        it .putExtra("CODIGO", pes.getCodigo().toString());

        setResult(1, it);
        finish();
    }
}
