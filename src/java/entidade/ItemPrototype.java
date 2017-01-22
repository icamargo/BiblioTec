package entidade;
//@author Igor

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@DiscriminatorColumn (name = "tipoItem")
@Table (name = "Item")
public abstract class ItemPrototype implements Serializable{
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    protected int numeroCatalogo;
    protected String nome;
    protected String status;
    protected int edicao;
    protected String autor;
    protected String editora;
    protected int volume;
    protected int anoPublicacao;
    protected int numPaginas;
    protected String origem;
    @Column(insertable=false, updatable=false)
    protected String tipoItem;
    protected float valorMultaDiaAtraso;
    protected String codClassificador;
    protected String motivoInativacao;
    @Column (columnDefinition = "TEXT")
    protected String detalhesInativacao;
    
    public ItemPrototype(){
    }
    
    public abstract ItemPrototype clonar();

    public int getNumeroCatalogo() {
        return numeroCatalogo;
    }

    public void setNumeroCatalogo(int numeroCatalogo) {
        this.numeroCatalogo = numeroCatalogo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getEdicao() {
        return edicao;
    }

    public void setEdicao(int edicao) {
        this.edicao = edicao;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public int getNumPaginas() {
        return numPaginas;
    }

    public void setNumPaginas(int numPaginas) {
        this.numPaginas = numPaginas;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public float getValorMultaDiaAtraso() {
        return valorMultaDiaAtraso;
    }

    public void setValorMultaDiaAtraso(float valorMultaDiaAtraso) {
        this.valorMultaDiaAtraso = valorMultaDiaAtraso;
    }
    public String getTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(String tipoItem) {
        this.tipoItem = tipoItem;
    }

    public String getCodClassificador() {
        return codClassificador;
    }

    public void setCodClassificador(String codClassificador) {
        this.codClassificador = codClassificador;
    }
    
    public String getMotivoInativacao() {
        return motivoInativacao;
    }

    public void setMotivoInativacao(String motivoInativacao) {
        this.motivoInativacao = motivoInativacao;
    }

    public String getDetalhesInativacao() {
        return detalhesInativacao;
    }

    public void setDetalhesInativacao(String detalhesInativacao) {
        this.detalhesInativacao = detalhesInativacao;
    }
}
