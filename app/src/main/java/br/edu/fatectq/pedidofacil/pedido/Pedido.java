package br.edu.fatectq.pedidofacil.pedido;

public class Pedido {

    Integer categ;
    Integer prod;
    float qtd;
    Integer mesa;

    public Integer getCateg() {
        return categ;
    }

    public void setCateg(Integer categ) {
        this.categ = categ;
    }

    public Integer getProd() {
        return prod;
    }

    public void setProd(Integer prod) {
        this.prod = prod;
    }

    public float getQtd() {
        return qtd;
    }

    public void setQtd(float qtd) {
        this.qtd = qtd;
    }

    public Integer getMesa() {
        return mesa;
    }

    public void setMesa(Integer mesa) {
        this.mesa = mesa;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "categ=" + categ +
                ", prod=" + prod +
                ", qtd=" + qtd +
                ", mesa=" + mesa +
                '}';
    }
}
