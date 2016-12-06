package br.edu.fatectq.pedidofacil.produtos;

public class Produto {

    private Integer codigo;
    private String nome;
    private String tipo;
    private float preco;
    private float qtd;
    private Integer categ;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public float getQtd() {
        return qtd;
    }

    public void setQtd(float qtd) {
        this.qtd = qtd;
    }

    public Integer getCateg() {
        return categ;
    }

    public void setCateg(Integer categ) {
        this.categ = categ;
    }

}
