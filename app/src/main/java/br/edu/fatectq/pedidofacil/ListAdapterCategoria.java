package br.edu.fatectq.pedidofacil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.fatectq.pedidofacil.produtos.Categoria;

public class ListAdapterCategoria extends BaseAdapter{

    private Context ctx;
    private List<Categoria> lst;

    public ListAdapterCategoria(Context ctx, List<Categoria> lst){
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
        Categoria c = lst.get(position);
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.activity_consulta_categoria, null);

        TextView txtCodigo = (TextView) view.findViewById(R.id.txtCodigo);
        txtCodigo.setText(c.getCodigo().toString());

        TextView txtDescricao = (TextView) view.findViewById(R.id.txtDescricao);
        txtDescricao.setText(c.getNome());

        return view;
    }
}
