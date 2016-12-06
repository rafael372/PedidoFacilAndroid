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

public class CategoriaDAO {

    protected SQLiteDatabase db;
    private static final String[] colunas = new String[]{"codigo", "nome"};

    public CategoriaDAO(Context ctx){
        db = ctx.openOrCreateDatabase(CreateBanco.NOME_BANCO, Context.MODE_PRIVATE, null);
    }

    public void begin(){
        exec("BEGIN IMMEDIATE  TRANSACTION");
    }

    public void limparCadastros() {
        exec("delete from tbCategoria");
    }

    public long salvar(Categoria categ) {
        ContentValues values = new ContentValues();
        values.put("codigo", categ.getCodigo());
        values.put("nome", categ.getNome());
        return db.insert("tbCategoria", "", values);
    }

    public void commit(){
        exec("COMMIT TRANSACTION");
    }

    public List<Categoria> listar() {
        try {
            Cursor c = db.query("tbCategoria", colunas, null, null, null, null, null, null);
            List<Categoria> categs = new ArrayList<>();
            if (c.moveToFirst()) {
                int idxCodigo = c.getColumnIndex("codigo");
                int idxNome = c.getColumnIndex("nome");
                do {
                    Categoria categ = new Categoria();
                    categs.add(categ);
                    categ.setCodigo(c.getInt(idxCodigo));
                    categ.setNome(c.getString(idxNome));
                } while (c.moveToNext());
            }
            c.close();
            return categs;
        } catch (SQLException e) {
            Log.e("CategoriaDAO", "Erro ao buscar as categorias: " + e.toString());
            return null;
        }
    }

    public Categoria getCategoria(int codigo) {
        Categoria categ = null;
        try {
            Cursor c = db.query("tbCategoria", colunas,
                    "codigo = " + codigo, null, null, null, null);
            if (c.moveToNext()) {
                categ = new Categoria();
                categ.setCodigo(c.getInt(0));
                categ.setNome(c.getString(1));
            }
            c.close();
        } catch (SQLException e) {
            Log.e("CategoriaDAO", "Erro ao buscar categoria por código: " + e.toString());
        }
        return categ;
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
