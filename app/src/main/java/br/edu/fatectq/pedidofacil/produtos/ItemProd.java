package br.edu.fatectq.pedidofacil.produtos;

import android.provider.BaseColumns;

public class ItemProd {

    private int _id;
    private int codigo;
    private Produto prod;
    private float qtd;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Produto getProd() {
        return prod;
    }

    public void setProd(Produto prod) {
        this.prod = prod;
    }

    public float getQtd() {
        return qtd;
    }

    public void setQtd(float qtd) {
        this.qtd = qtd;
    }

    public static final class ItensProd implements BaseColumns{
        public ItensProd(){}

        public static final String CODIGO = "codigo";
        public static final String PROD = "prod";
        public static final String QTD = "qtd";
    }

    @Override
    public String toString() {
        return "ItemProd{" +
                "_id=" + _id +
                ", codigo=" + codigo +
                ", prod=" + prod +
                ", qtd=" + qtd +
                '}';
    }
}
