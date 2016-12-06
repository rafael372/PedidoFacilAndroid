package br.edu.fatectq.pedidofacil.webservice;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import br.edu.fatectq.pedidofacil.LoginActivity;

public class WebService {
    private static final String TAG = WebService.class.getSimpleName();

    public final String[] get(String urlString) {

        String[] result = new String[2];
        URI uri = null;
        try {
            URL url = new URL(urlString);
            uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            url = uri.toURL();
        } catch (Exception e) {
            Log.e("get", e.toString());
        }

        HttpGet httpget = new HttpGet(uri);
        HttpResponse response;

        try {
            response = HttpClientSingleton.getHttpClientInstace().execute(httpget);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                result[0] = String.valueOf(response.getStatusLine().getStatusCode());
                InputStream instream = entity.getContent();
                result[1] = toString(instream);
                instream.close();
                Log.i("get", "Result from post JsonPost: " + result[0] + " : " + result[1]);
            }
        } catch (Exception e) {
            Log.e(TAG, "Falha ao acessar Web service", e);
            result[0] = "0";
            result[1] = "Falha de rede!\nProcure o Departamento de Inform�tica.";
        }
        return result;
    }

    public final String[] post(String url, String json) {
        String[] result = new String[2];
        try {

            HttpPost httpPost = new HttpPost(new URI(url));
            httpPost.setHeader("Content-type", "application/json");
            StringEntity sEntity = new StringEntity(json, "UTF-8");
            httpPost.setEntity(sEntity);

            HttpResponse response = HttpClientSingleton.getHttpClientInstace().execute(httpPost);

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                result[0] = String.valueOf(response.getStatusLine().getStatusCode());
                InputStream instream = entity.getContent();
                result[1] = toString(instream);
                instream.close();
                Log.d("post", "Result from post JsonPost: " + result[0] + " : " + result[1]);
            }

        } catch (Exception e) {
            Log.e(TAG, "Falha ao acessar Web service", e);
            result[0] = "0";
            result[1] = "Falha de rede!\nProcure o Departamento de Inform�tica.";
        }
        return result;
    }

    private String toString(InputStream is) throws IOException {

        byte[] bytes = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int lidos;
        while ((lidos = is.read(bytes)) > 0) {
            baos.write(bytes, 0, lidos);
        }
        return new String(baos.toByteArray());
    }

    public String getCadastros(String tipoCadastro) throws Exception {
        String[] resposta = new WebService().get(LoginActivity.URL_WS_CADASTROS + tipoCadastro);
        if (resposta[0].equals("200")) {
            return resposta[1];
        } else {
            throw new Exception(resposta[1]);
        }
    }

    public String postApontamentos(String apontamentos, String ws, Context ctx, int quantidade) throws Exception {
        /*
        String[] resposta = new WebService().post(LoginActivity.URL_WS_APONTAMENTOS + ws, apontamentos);

        if (resposta[0].equals("200")) {
            String[] chaves = resposta[1].split(";");

            if (chaves != null) {
                if (chaves.length > 0) {
                    if (chaves[0].equals("OK")) {
                        String data_envio = F_Tempo.retornaData(0, F_Tempo.YYYY_MM_DD_HH_MM_SS);

                        if (ws.contains("Pontos")) {
                            Repositorio_Ponto  repPontos = new Repositorio_Ponto(ctx);

                            for (String i : chaves) {
                                if (! i.equalsIgnoreCase("OK")) {
                                    C_Ponto ponto = repPontos.get("SELECT * "
                                            + " FROM " + Repositorio_Ponto.NOME_TABELA
                                            + " WHERE " + C_Ponto.CHAVE
                                            + " = '" + i.toString() + "'");

                                    ponto.data_envio = data_envio;
                                    repPontos.salvar(ponto);
                                }
                            }

                            repPontos.fechar();

                            if (quantidade == (chaves.length - 1)) {
                                return "Pontos enviados: " + quantidade;
                            } else {
                                return "Pontos enviados: " + quantidade
                                        + "\nPontos recebidos: " + (chaves.length - 1);
                            }
                        } else if (ws.contains("Producoes")) {
                            Repositorio_Producao  repProducoes = new Repositorio_Producao(ctx);

                            for (String i : chaves) {
                                if (! i.equalsIgnoreCase("OK")) {
                                    C_Producao producao = repProducoes.get("SELECT * "
                                            + " FROM " + Repositorio_Producao.NOME_TABELA
                                            + " WHERE " + C_Producao._ID
                                            + " = " + i.toString());

                                    producao.data_envio = data_envio;
                                    repProducoes.salvar(producao);
                                }
                            }

                            repProducoes.fechar();

                            if (quantidade == (chaves.length - 1)) {
                                return "Produ��es enviados: " + quantidade;
                            } else {
                                return "Produ��es enviados: " + quantidade
                                        + "\nProdu��es recebidos: " + (chaves.length - 1);
                            }
                        }
                    } else if (chaves[0].equals("ERRO")) {
                        if (ws.contains("Pontos")) {
                            return "Falha no envio dos pontos";
                        } else if (ws.contains("Producoes")) {
                            return "Falha no envio das produ��es";
                        }
                    } else if (chaves[0].equals("")) {
                        if (ws.contains("Pontos")) {
                            return "Pontos enviados: 0";
                        } else if (ws.contains("Producoes")) {
                            return "Produ��es enviadas: 0";
                        }
                    }
                }
            }
            return resposta[1];
        } else {
            throw new Exception(resposta[1]);
        }
        */
        return "";
    }
}