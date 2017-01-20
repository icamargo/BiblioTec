/**
 *
 * @author Igor
 */
package entidade;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@DiscriminatorValue(value = "Usuario")
public class UsuarioPrototype extends PessoaPrototype{
    private String situacao;
    @OneToOne (mappedBy = "usuario")
    @Cascade (CascadeType.ALL)
    private Emprestimo emprestimo;
    
    @OneToMany (mappedBy = "usuario")
    @Cascade (CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Emprestimo> emprestimos;

        public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(List<Emprestimo> emprestimos) {
        this.emprestimos = emprestimos;
    }
    
    @OneToMany (fetch=FetchType.EAGER)
    @JoinTable(name = "usuario_reserva",joinColumns = 
            @JoinColumn(name = "usuario_id"),inverseJoinColumns = @JoinColumn(name = "reserva_id"))
    private List<Reserva> reservas;

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public UsuarioPrototype() {
        this.situacao = "Normal";
        this.ativo = true;
        this.reservas = new ArrayList<>();
        this.emprestimos = new ArrayList<>();
    }

    public UsuarioPrototype(UsuarioPrototype usuario) {
        this.situacao = usuario.getSituacao();
    }
    
    @Override
    public PessoaPrototype clonar(){
        return new UsuarioPrototype (this);
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }
}
