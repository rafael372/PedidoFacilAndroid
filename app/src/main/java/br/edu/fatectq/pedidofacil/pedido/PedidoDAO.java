package br.edu.fatectq.pedidofacil.pedido;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.edu.fatectq.pedidofacil.CreateBanco;

public class PedidoDAO {

    protected SQLiteDatabase db;
    private static final String[] colunas = new String[]{"categ", "prod", "qtd", "mesa"};

    public PedidoDAO(Context ctx){
        db = ctx.openOrCreateDatabase(CreateBanco.NOME_BANCO, Context.MODE_PRIVATE, null);
    }

    public void begin(){
        exec("BEGIN IMMEDIATE  TRANSACTION");
    }

    public void limparCadastros() {
        exec("delete from tbPedido");
    }

    public long salvar(Pedido pedido) {
        ContentValues values = new ContentValues();
        values.put("categ", pedido.getCateg());
        values.put("prod", pedido.getProd());
        values.put("qtd", pedido.getQtd());
        values.put("mesa", pedido.getMesa());
        return db.insert("tbPedido", "", values);
    }

    public void commit(){
        exec("COMMIT TRANSACTION");
    }

    public List<Pedido> listar() {
        try {
            Cursor c = db.query("tbPedido", colunas, null, null, null, null, null, null);
            List<Pedido> pedidos = new ArrayList<>();
            if (c.moveToFirst()) {
                int idxCateg = c.getColumnIndex("codigo");
                int idxProd = c.getColumnIndex("nome");
                int idxQtd = c.getColumnIndex("qtd");
                int idxMesa = c.getColumnIndex("mesa");
                do {
                    Pedido pedido = new Pedido();
                    pedidos.add(pedido);
                    pedido.setCateg(c.getInt(idxCateg));
                    pedido.setProd(c.getInt(idxProd));
                    pedido.setQtd(c.getFloat(idxQtd));
                    pedido.setMesa(c.getInt(idxMesa));
                } while (c.moveToNext());
            }
            c.close();
            return pedidos;
        } catch (SQLException e) {
            Log.e("CategoriaDAO", "Erro ao buscar os pedidos: " + e.toString());
            return null;
        }
    }

    public Pedido getPedido(int codigo) {
        Pedido pedido = null;
        try {
            Cursor c = db.query("tbPedido", colunas,
                    "codigo = " + codigo, null, null, null, null);
            if (c.moveToNext()) {
                pedido = new Pedido();
                pedido.setCateg(c.getInt(0));
                pedido.setProd(c.getInt(1));
                pedido.setQtd(c.getFloat(2));
                pedido.setMesa(c.getInt(3));
            }
            c.close();
        } catch (SQLException e) {
            Log.e("CategoriaDAO", "Erro ao buscar pedido por código: " + e.toString());
        }
        return pedido;
    }

    public boolean exec(String vsql){
        try{
            db.execSQL(vsql);
            return true;
        } catch (SQLException e){
            return false;
        }
    }

    public Cursor getCursorSQL(String vSql) {
        try {
            return db.rawQuery(vSql, null);
        } catch (SQLException e) {
            Log.e("", "Erro ao executar sql: " + e.toString());
            return null;
        }
    }

    public void fechar() {
        if (db != null)	db.close();
    }

}
