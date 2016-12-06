package br.edu.fatectq.pedidofacil.pessoas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.edu.fatectq.pedidofacil.CreateBanco;

public class PessoaDAO {

    public static final String[] colunas = new String[] {"codigo", "nome", "tipo", "senha", "email", "telefone"};

    protected SQLiteDatabase db;

    public PessoaDAO(Context ctx){
        db = ctx.openOrCreateDatabase(CreateBanco.NOME_BANCO, Context.MODE_PRIVATE, null);
    }

    public void begin(){
        exec("BEGIN IMMEDIATE  TRANSACTION");
    }

    public void limparCadastros() {
        exec("delete from tbPessoa");
    }

    public long salvar(Pessoa pessoa) {
        ContentValues values = new ContentValues();
        values.put("codigo", pessoa.getCodigo());
        values.put("nome", pessoa.getNome());
        values.put("tipo", pessoa.getTipo());
        values.put("senha", pessoa.getSenha());
        values.put("email", pessoa.getEmail());
        values.put("telefone", pessoa.getFone());
        return db.insert("tbPessoa", "", values);
    }

    public void commit(){
        exec("COMMIT TRANSACTION");
    }

    public List<Pessoa> listar() {
        try {
            Cursor c = db.query("tbPessoa", colunas, null, null, null, null, null, null);
            List<Pessoa> pessoas = new ArrayList<>();
            if (c.moveToFirst()) {
                int idxCodigo = c.getColumnIndex("codigo");
                int idxNome = c.getColumnIndex("nome");
                int idxTipo = c.getColumnIndex("tipo");
                int idxSenha = c.getColumnIndex("senha");
                int idxEmail = c.getColumnIndex("email");
                int idxTelefone = c.getColumnIndex("telefone");
                do {
                    Pessoa pessoa = new Pessoa();
                    pessoas.add(pessoa);
                    pessoa.setCodigo(c.getInt(idxCodigo));
                    pessoa.setNome(c.getString(idxNome));
                    pessoa.setTipo(c.getString(idxTipo));
                    pessoa.setSenha(c.getString(idxSenha));
                    pessoa.setEmail(c.getString(idxEmail));
                    pessoa.setFone(c.getString(idxTelefone));
                } while (c.moveToNext());
            }
            c.close();
            return pessoas;
        } catch (SQLException e) {
            Log.e("PessoaDAO", "Erro ao buscar pessoas: " + e.toString());
            return null;
        }
    }

    public Pessoa getPessoa(int codigo) {
        Pessoa pessoa = null;
        try {
            Cursor c = db.query("tbPessoa", colunas,
                    "codigo = " + codigo, null, null, null, null);
            if (c.moveToNext()) {
                pessoa = new Pessoa();
                pessoa.setCodigo(c.getInt(0));
                pessoa.setNome(c.getString(1));
                pessoa.setTipo(c.getString(2));
                pessoa.setSenha(c.getString(3));
                pessoa.setEmail(c.getString(4));
                pessoa.setFone(c.getString(5));
            }
            c.close();
        } catch (SQLException e) {
            Log.e("PessoaDAO", "Erro ao buscar Pesssoa pelo codigo: " + e.toString());
        }
        return pessoa;
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
