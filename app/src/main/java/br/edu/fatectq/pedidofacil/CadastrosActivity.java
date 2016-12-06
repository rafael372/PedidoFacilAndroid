package br.edu.fatectq.pedidofacil;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import br.edu.fatectq.pedidofacil.pessoas.Pessoa;
import br.edu.fatectq.pedidofacil.pessoas.PessoaDAO;
import br.edu.fatectq.pedidofacil.produtos.Categoria;
import br.edu.fatectq.pedidofacil.produtos.CategoriaDAO;
import br.edu.fatectq.pedidofacil.produtos.Produto;
import br.edu.fatectq.pedidofacil.produtos.ProdutoDAO;
import br.edu.fatectq.pedidofacil.webservice.WebService;

public class CadastrosActivity extends AppCompatActivity {

    private static Context ctx;
    private Handler handler;
    private ArrayAdapter<String> statusArrayAdapter;
    private ListView lstGeral;
    private ProgressDialog progressDialog;
    private Map<String, String> listaArquivos;

    private static final String ARQUIVO_PESSOA = Environment.getExternalStorageDirectory().getAbsolutePath() +
            "/pedidofacil/pessoa.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastros);

        ctx = this;

        handler = new Handler();

        statusArrayAdapter = new ArrayAdapter<String>(this, R.layout.msg_status);
        lstGeral = (ListView) findViewById(R.id.tela_atualizar_Cadastros_lstStatus);
        lstGeral.setAdapter(statusArrayAdapter);

        this.setTitle("Web Service pela Internet");

        if (verificarConexaoInternet()) {
            this.setTitle(getTitle() + " - Conectado à internet");
        } else {
            this.setTitle(getTitle() + " - Desconectado da internet");
        }

        escreverStatus(getTitle().toString());
    }

    public void onClickBttAtualizar(View view) {
        escreverStatus("Iniciando atualização de cadastros");
        new thReceberCadastros().start();
    }

    public static boolean verificarConexaoInternet() {
        try {
            ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) {
                if (activeNetwork.isConnected()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private void escreverStatus(final String mensagem) {
        handler.post(new Runnable() {
            public void run() {
                statusArrayAdapter.add(mensagem);
            }
        });
    }

    private void iniciarProgressDialog(final String title, final String message) {
        encerrarProgressDialog();

        handler.post(new Runnable() {

            @Override
            public void run() {
                progressDialog = new ProgressDialog(ctx);
                progressDialog.setCancelable(false);
                progressDialog.setTitle(title);
                progressDialog.setMessage(message);

                progressDialog.show();
            }
        });
    }

    private void iniciarProgressDialogHorizontal(final String title, final String message,
                                                 final int max) {

        encerrarProgressDialog();
        handler.post(new Runnable() {

            @Override
            public void run() {
                progressDialog = new ProgressDialog(ctx);
                progressDialog.setCancelable(false);
                progressDialog.setTitle(title);
                progressDialog.setMessage(message);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setProgress(0);
                progressDialog.setMax(max);

                progressDialog.show();
            }
        });
    }

    private void incrementarProgressDialogHorizontal() {
        handler.post(new Runnable() {

            @Override
            public void run() {
                progressDialog.incrementProgressBy(1);
            }
        });
    }

    private void encerrarProgressDialog() {

        try {
            handler.post(new Runnable() {

                @Override
                public void run() {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                }
            });
        } catch (Exception e) {
            Log.e("encerrarProgressDialog", e.toString());
        }
    }

    private class thReceberCadastros extends Thread {

        @Override
        public void run() {
            super.run();

            boolean atualizar = true;
            WebService webService = new WebService();
            Gson gson = new Gson();
            String strJSon;

            //Categoria
            escreverStatus("");
            escreverStatus("Recebendo categorias de produtos");
            iniciarProgressDialog("Recebendo cadastros", "Recebendo cadastro de categorias de produtos...");
            Categoria[] lstCateg;
            try{
                strJSon = webService.getCadastros("getCategoria");
                strJSon = strJSon.substring(strJSon.indexOf("\": ") + 2);
                strJSon = strJSon.substring(0,strJSon.length() -1);

                lstCateg = gson.fromJson(strJSon, Categoria[].class);
                CategoriaDAO cDAO = new CategoriaDAO(ctx);
                cDAO.begin();
                cDAO.limparCadastros();
                for(Categoria c : lstCateg){
                    cDAO.salvar(c);
                }
                cDAO.commit();
                cDAO.fechar();
                escreverStatus("Cadastro de categorias de produtos atualizado com sucesso!");
            }catch (Exception ex){
                Log.e("getCategoria", ex.getMessage());
                atualizar = false;
                escreverStatus("Não foi possível receber o cadastro de categorias de produto");
            }
            encerrarProgressDialog();

            escreverStatus("");
            escreverStatus("Recebendo produtos");
            iniciarProgressDialog("Recebendo cadastros", "Recebendo cadastro de produtos...");
            Produto[] lstProd;
            try{
                strJSon = webService.getCadastros("getProduto");
                strJSon = strJSon.substring(strJSon.indexOf("\": ") + 2);
                strJSon = strJSon.substring(0,strJSon.length() -1);

                lstProd = gson.fromJson(strJSon, Produto[].class);
                ProdutoDAO prodDAO = new ProdutoDAO(ctx);
                prodDAO.begin();
                prodDAO.limparCadastros();
                for(Produto p : lstProd){
                    prodDAO.salvar(p);
                }
                prodDAO.commit();
                prodDAO.fechar();
                escreverStatus("Cadastro de produtos atualizado com sucesso!");
            }catch (Exception ex){
                Log.e("getProduto", ex.getMessage());
                atualizar = false;
                escreverStatus("Não foi possível receber o cadastro de produtos");
            }
            encerrarProgressDialog();

            escreverStatus("");
            escreverStatus("Recebendo pessoas");
            iniciarProgressDialog("Recebendo cadastros", "Recebendo cadastro de pessoas...");
            Pessoa[] lstPess;
            try{
                strJSon = webService.getCadastros("getPessoa");
                strJSon = strJSon.substring(strJSon.indexOf("\": ") + 2);
                strJSon = strJSon.substring(0,strJSon.length() -1);

                lstPess = gson.fromJson(strJSon, Pessoa[].class);
                PessoaDAO pessDAO = new PessoaDAO(ctx);
                pessDAO.begin();
                pessDAO.limparCadastros();
                for(Pessoa p : lstPess){
                    pessDAO.salvar(p);
                }
                pessDAO.commit();
                pessDAO.fechar();
                escreverStatus("Cadastro de pessoas atualizado com sucesso!");
            }catch (Exception ex){
                Log.e("getPessoa", ex.getMessage());
                atualizar = false;
                escreverStatus("Não foi possível receber o cadastro de pessoas");
            }
            encerrarProgressDialog();

            if (! atualizar) {
                escreverStatus("Cadastros não foram atualizados");
                escreverStatus("--------------------------------------");
            }
        }
    }

    private boolean receber(String getWS, String nomeArquivo) {
        try {
            WebService webService = new WebService();
            String texto = webService.getCadastros(getWS);
            gravarArquivo(nomeArquivo, texto);
        } catch (Exception e) {
            Log.e("receber", e.toString());
            return false;
        }
        return true;
    }

    private void gravarArquivo(String nomeArquivo, String texto) {
        excluirArquivo(nomeArquivo);
        try {
            //F_Exportacao.escreverArquivoTexto(nomeArquivo, texto);
        } catch (Exception e) {
            Log.e("gravarArquivo", e.toString());
        }
    }

    private int contarLinhasArquivo(File nomeArquivo) {
        int linhas = 0;

        try {
            Scanner scanner = new Scanner(nomeArquivo);

            while (scanner.hasNextLine()) {
                scanner.nextLine();
                linhas ++;
            }

            return linhas;
        } catch (Exception e) {
            Log.e("contarLinhasArquivo", e.toString());
            return -1;
        }
    }

    private void atualizarCadastros() {

        adicionarElementosLista();

        for (Map.Entry<String, String> map : listaArquivos.entrySet()) {
            //Repositorio_AtualizaBanco repositorio = new Repositorio_AtualizaBanco(ctx);
            File file = new File(map.getValue());

            if (! file.exists()) {
                continue;
            }

            int linhas = contarLinhasArquivo(file);
            if (linhas < 0) {
                continue;
            }

            escreverStatus("Atualizando " + map.getKey());
            iniciarProgressDialogHorizontal("Atualizando arquivos",
                    "Atualizando " + map.getKey(), linhas);

            try {
                Scanner scanner = new Scanner(file);
                String sqlValor = "", texto = "";

                //Inicia a leitura do arquivo
                while (scanner.hasNextLine()) {
                    texto = scanner.nextLine();

                    boolean exeSQL = false;
                    if (texto.contains("sql|")) {
                        try {
                            //repositorio.exec(texto.replace("sql|", ""));
                            exeSQL = true;
                        } catch (Exception e) {
                            Log.e("atualizarCadastros", e.toString());
                        }
                    } else if (texto.contains("sqlValor|")) {
                        sqlValor = texto.replace("sqlValor|", "");
                    } else {
                        while (texto != null && ! texto.contains("tabela|")
                                && ! texto.contains("tabelaAndroid|") && ! texto.contains("sql|")
                                && ! texto.contains("sqlValor|") ) {

                            StringBuffer sql = new StringBuffer();
                            sql.append(sqlValor.replace("@valores", texto));

                            try {
                                //repositorio.exec(sql.toString());
                            } catch (Exception e) {
                                Log.e("atualizarCadastros", e.toString());
                            } finally {
                                incrementarProgressDialogHorizontal();
                            }

                            if (! scanner.hasNextLine()) {
                                break;
                            }
                            texto = scanner.nextLine();
                        }
                    }

                    if (! exeSQL) {
                        if (texto.contains("sql|")) {
                            try {
                                //repositorio.exec(texto.replace("sql|", ""));
                            } catch (Exception e) {
                                Log.e("atualizarCadastros", e.toString());
                            }
                        }
                    }

                    incrementarProgressDialogHorizontal();
                }
                scanner.close();

            } catch (Exception e) {
                Log.e("atualizarCadastros", e.toString());
                escreverStatus("Erro ao atualizar " + map.getKey());
            } finally {
                //repositorio.fechar();
            }

            encerrarProgressDialog();
        }
    }

    public static boolean excluirArquivo(String nomeArquivo) {
        try {
            File file = new File(nomeArquivo);
            if (file.exists()) {
                if (file.delete()) {
                    Log.i("excluirArquivo", nomeArquivo + " apagado");
                    return true;
                } else {
                    Log.e("excluirArquivo", nomeArquivo + " não apagado");
                }
            }
        } catch (Exception e) {
            Log.e("excluirArquivo", e.toString());
        }

        return false;
    }

    private void adicionarElementosLista() {
        listaArquivos = new HashMap<String, String>();

        listaArquivos.put("Pessoa", ARQUIVO_PESSOA);
    }
}

