package br.edu.fatectq.pedidofacil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String CATEGORIA = "SQLiteHelper";

    private String[] scriptSQLCreate;
    private String scriptSQLDelete;

    SQLiteHelper(Context context, String nomeBanco, int versaoBanco,
                 String[] scriptSQLCreate, String scriptSQLDelete) {
        super(context, nomeBanco, null, versaoBanco);
        this.scriptSQLCreate = scriptSQLCreate;
        this.scriptSQLDelete = scriptSQLDelete;
    }

    // Criar novo banco...
    public void onCreate(SQLiteDatabase db) {
        Log.i(CATEGORIA, "Criando banco com sql");

        // Executa cada sql passado como parametro
        for (String sql : scriptSQLCreate) {
            Log.i(CATEGORIA, sql);
            // Cria o banco de dados executando o script de cria��o
            db.execSQL(sql);
        }
    }

    // Mudou a vers�o...
    public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int novaVersao) {
        Log.w(CATEGORIA, "Atualizando da vers�o " + versaoAntiga + " para "
                + novaVersao + ". Todos os registros ser�o deletados.");
        Log.i(CATEGORIA, scriptSQLDelete);
        // Deleta as tabelas...
        db.execSQL(scriptSQLDelete);
        // Cria novamente...
        onCreate(db);
    }

}