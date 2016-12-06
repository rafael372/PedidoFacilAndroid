package br.edu.fatectq.pedidofacil.produtos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatectq.pedidofacil.CreateBanco;

public class ProdutoDAO {

    protected SQLiteDatabase db;
    public static final String[] colunas = new String[] {"codigo", "nome",
            "tipo", "preco", "qtd", "categ"};

    public ProdutoDAO(Context ctx){
        db = ctx.openOrCreateDatabase(CreateBanco.NOME_BANCO, Context.MODE_PRIVATE, null);
    }

    public void begin(){
        exec("BEGIN IMMEDIATE  TRANSACTION");
    }

    public void limparCadastros() {
        exec("delete from tbProduto");
    }

    public long salvar(Produto prod) {
        ContentValues values = new ContentValues();
        values.put("codigo", prod.getCodigo());
        values.put("nome", prod.getNome());
        values.put("tipo", prod.getTipo());
        values.put("preco", prod.getPreco());
        values.put("qtd", prod.getQtd());
        values.put("categ", prod.getCateg());
        return db.insert("tbProduto", "", values);
    }

    public void commit(){
        exec("COMMIT TRANSACTION");
    }

    public List<Produto> listar() {
        try {
            Cursor c = db.query("tbProduto", colunas, null, null, null, null, null, null);
            List<Produto> produtos = new ArrayList<>();
            if (c.moveToFirst()) {
                int idxCodigo = c.getColumnIndex("codigo");
                int idxNome = c.getColumnIndex("nome");
                int idxTipo = c.getColumnIndex("tipo");
                int idxPreco = c.getColumnIndex("preco");
                int idxQtd = c.getColumnIndex("qtd");
                int idxCateg = c.getColumnIndex("categ");
                do {
                    Produto prod = new Produto();
                    produtos.add(prod);
                    prod.setCodigo(c.getInt(idxCodigo));
                    prod.setNome(c.getString(idxNome));
                    prod.setTipo(c.getString(idxTipo));
                    prod.setPreco(c.getFloat(idxPreco));
                    prod.setQtd(c.getFloat(idxQtd));
                    prod.setCateg(c.getInt(idxCateg));
                } while (c.moveToNext());
            }
            c.close();
            return produtos;
        } catch (SQLException e) {
            Log.e("ProdutoDAO", "Erro ao buscar os produtos: " + e.toString());
            return null;
        }
    }

    public List<Produto> listar(int categ) {
        try {
            Cursor c = db.query("tbProduto", colunas, "categ = " + String.valueOf(categ), null, null, null, null, null);
            List<Produto> produtos = new ArrayList<>();
            if (c.moveToFirst()) {
                int idxCodigo = c.getColumnIndex("codigo");
                int idxNome = c.getColumnIndex("nome");
                int idxTipo = c.getColumnIndex("tipo");
                int idxPreco = c.getColumnIndex("preco");
                int idxQtd = c.getColumnIndex("qtd");
                int idxCateg = c.getColumnIndex("categ");
                do {
                    Produto prod = new Produto();
                    produtos.add(prod);
                    prod.setCodigo(c.getInt(idxCodigo));
                    prod.setNome(c.getString(idxNome));
                    prod.setTipo(c.getString(idxTipo));
                    prod.setPreco(c.getFloat(idxPreco));
                    prod.setQtd(c.getFloat(idxQtd));
                    prod.setCateg(c.getInt(idxCateg));
                } while (c.moveToNext());
            }
            c.close();
            return produtos;
        } catch (SQLException e) {
            Log.e("ProdutoDAO", "Erro ao buscar os produtos: " + e.toString());
            return null;
        }
    }

    public Produto getProduto(int codigo) {
        Produto prod = null;
        try {
            Cursor c = db.query("tbProduto", colunas,
                    "codigo = " + codigo, null, null, null, null);
            if (c.moveToNext()) {
                prod = new Produto();
                prod.setCodigo(c.getInt(0));
                prod.setNome(c.getString(1));
                prod.setTipo(c.getString(2));
                prod.setPreco(c.getFloat(3));
                prod.setQtd(c.getFloat(4));
                prod.setCateg(c.getInt(5));
            }
            c.close();
        } catch (SQLException e) {
            Log.e("ProdutoDAO", "Erro ao buscar produto por código: " + e.toString());
        }
        return prod;
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
