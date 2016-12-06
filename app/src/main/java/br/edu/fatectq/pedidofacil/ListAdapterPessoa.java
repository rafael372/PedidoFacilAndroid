package br.edu.fatectq.pedidofacil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.fatectq.pedidofacil.pessoas.Pessoa;

public class ListAdapterPessoa extends BaseAdapter{

    private Context ctx;
    private List<Pessoa> lst;

    public ListAdapterPessoa(Context ctx, List<Pessoa> lst){
        this.ctx = ctx;
        this.lst = lst;
    }

    public int getCount(){
        return lst.size();
    }

    public Object getItem(int position){
        return lst.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Pessoa p = lst.get(position);
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.activity_consulta_login, null);

        TextView txtMatricula = (TextView) view.findViewById(R.id.txtMatricula);
        txtMatricula.setText(p.getCodigo().toString());

        TextView txtNome = (TextView) view.findViewById(R.id.txtNome);
        txtNome.setText(p.getNome());

        return view;
    }
}
