package com.generation.farmacia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Campo obrigatório")
    @Size(min = 2, max = 255, message = "Digite um nome entre 2 e 255 caracteres")
    private String nome;

    @NotBlank(message = "Campo obrigatório")
    @Size(min = 2, max = 255, message = "Digite uma descricao entre 2 e 255 caracteres")
    private String descricao;

    @Size(min = 2, max = 255, message = "Adicione um link com 2 a 255 caracteres")
    private String imagem;

    @NotNull(message = "Campo obrigatório")
    @Digits(integer = 3, fraction = 2, message = "O numero não está dentro do padrão")
    private BigDecimal preco;

    @ManyToOne
    @JsonIgnoreProperties("produtos")
    private Categoria categoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
