/**
 *
 * @author Igor
 */
package entidade;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@DiscriminatorValue (value = "Livro")
public class LivroPrototype extends ItemPrototype{
    private String isbn, motivoInativacao, detalhesInativacao;
    @OneToOne (mappedBy = "livro")
    @Cascade (CascadeType.ALL)
    private Emprestimo emprestimo;
    
    public LivroPrototype(){
        status = "Disponível";
    }
    public LivroPrototype(LivroPrototype livro){
        this.isbn = livro.getIsbn();
    }
    
    @Override
    public ItemPrototype clonar(){
        return new LivroPrototype (this);
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }
    
}
