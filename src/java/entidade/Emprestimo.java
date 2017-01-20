package entidade;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 *
 * @author Igor
 */
@Entity
@Table (name = "Emprestimos")
public class Emprestimo implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int idEmprestimo;
    private String statusEmprestimo;
    private int qtdeRenovacoes;
    @OneToOne
    @JoinColumn (name = "idUsuario")
    private UsuarioPrototype usuario;
    @OneToOne
    @JoinColumn (name = "idLivro")
    private LivroPrototype livro;
    @OneToOne
    @JoinColumn (name = "idPeriodico")
    private PeriodicoPrototype periodico;
    @Temporal(TemporalType.DATE)
    private Calendar dataEmprestimo;
    @Temporal(TemporalType.DATE)
    private Calendar dataDevPrevista;
    @Temporal(TemporalType.DATE)
    private Calendar dataDevolucao;

    public Emprestimo(){
    }

    public int getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setIdEmprestimo(int idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public String getStatusEmprestimo() {
        return statusEmprestimo;
    }

    public void setStatusEmprestimo(String statusEmprestimo) {
        this.statusEmprestimo = statusEmprestimo;
    }

    public int getQtdeRenovacoes() {
        return qtdeRenovacoes;
    }

    public void setQtdeRenovacoes(int qtdeRenovacoes) {
        this.qtdeRenovacoes = qtdeRenovacoes;
    }

    public Calendar getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(Calendar dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public Calendar getDataDevPrevista() {
        return dataDevPrevista;
    }

    public void setDataDevPrevista(Calendar dataDevPrevista) {
        this.dataDevPrevista = dataDevPrevista;
    }

    public Calendar getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Calendar dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public UsuarioPrototype getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioPrototype usuario) {
        this.usuario = usuario;
    }

    public LivroPrototype getLivro() {
        return livro;
    }

    public void setLivro(LivroPrototype livro) {
        this.livro = livro;
    }

    public PeriodicoPrototype getPeriodico() {
        return periodico;
    }

    public void setPeriodico(PeriodicoPrototype periodico) {
        this.periodico = periodico;
    }
}
