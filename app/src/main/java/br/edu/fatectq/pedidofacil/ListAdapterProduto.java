package br.edu.fatectq.pedidofacil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.fatectq.pedidofacil.produtos.Produto;


public class ListAdapterProduto extends BaseAdapter{

    private Context ctx;
    private List<Produto> lst;

    public ListAdapterProduto(Context ctx, List<Produto> lst){
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
        Produto p = lst.get(position);
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.activity_consulta_produto, null);

        TextView txtCodigo = (TextView) view.findViewById(R.id.txtCodigo);
        txtCodigo.setText(String.valueOf(p.getCodigo()));

        TextView txtPreco = (TextView) view.findViewById(R.id.txtPreco);
        txtPreco.setText(String.valueOf(p.getPreco()));

        TextView txtDescricao = (TextView) view.findViewById(R.id.txtDescricao);
        txtDescricao.setText(p.getNome());

        return view;
    }
}
