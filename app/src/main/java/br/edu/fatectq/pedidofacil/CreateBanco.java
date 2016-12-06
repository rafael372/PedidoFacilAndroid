package br.edu.fatectq.pedidofacil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CreateBanco {

    private static final String SCRIPT_DATABASE_DELETE = "DROP TABLE IF EXISTS [" +
            "tbPessoa, tbCategoria, tbProduto, tbPedido]";

    private static final String[] SCRIPT_DATABASE_CREATE = new String[] {
            "CREATE TABLE IF NOT EXISTS tbPessoa (" +
                    "codigo INTEGER NOT NULL ON CONFLICT IGNORE, " +
                    "nome VARCHAR2(50) NOT NULL ON CONFLICT IGNORE, " +
                    "tipo VARCHAR2(25) NOT NULL ON CONFLICT IGNORE, " +
                    "senha VARCHAR2(50) NOT NULL ON CONFLICT IGNORE, " +
                    "email VARCHAR2(255) NOT NULL ON CONFLICT IGNORE, " +
                    "telefone VARCHAR2(20) NOT NULL ON CONFLICT IGNORE);",
            "CREATE TABLE IF NOT EXISTS tbCategoria (" +
                    "codigo INTEGER PRIMARY KEY NOT NULL ON CONFLICT IGNORE, " +
                    "nome VARCHAR2(50) NOT NULL ON CONFLICT IGNORE);",
            "CREATE TABLE IF NOT EXISTS tbProduto (" +
                    "codigo INTEGER NOT NULL ON CONFLICT IGNORE, " +
                    "nome VARCHAR2(50) NOT NULL ON CONFLICT IGNORE, " +
                    "tipo VARCHAR2(25) NOT NULL ON CONFLICT IGNORE, " +
                    "preco NUMERIC(8,2) NOT NULL ON CONFLICT IGNORE, " +
                    "qtd NUMERIC(8,2) NOT NULL ON CONFLICT IGNORE, " +
                    "categ INTEGER NOT NULL ON CONFLICT IGNORE);",
            "CREATE TABLE IF NOT EXISTS tbPedido (" +
                    "categ INTEGER NOT NULL ON CONFLICT IGNORE, " +
                    "prod INTEGER NOT NULL ON CONFLICT IGNORE, " +
                    "qtd NUMERIC(8,2) NOT NULL ON CONFLICT IGNORE," +
                    "mesa INTEGER NOT NULL ON CONFLICT IGNORE);"
    };

    public static final String NOME_BANCO = "dbPedidoFacil";
    private static final int VERSAO_BANCO = 1;

    protected SQLiteDatabase db;

    public CreateBanco(Context ctx) {
        try {
            SQLiteHelper dbHelper = new SQLiteHelper(ctx,
                    NOME_BANCO, VERSAO_BANCO,
                    SCRIPT_DATABASE_CREATE,
                    SCRIPT_DATABASE_DELETE);
            db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE,
                    null);
            db = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            Log.e("CreateBanco", e.toString());
        }
    }

    public void fechar() {
        if (db != null)
            db.close();
    }

}