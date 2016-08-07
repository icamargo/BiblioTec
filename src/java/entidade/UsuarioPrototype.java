/**
 *
 * @author Pedro
 */
package entidade;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@DiscriminatorValue(value = "Usuario")
public class UsuarioPrototype extends PessoaPrototype{
    private String situacao;
    @OneToOne (mappedBy = "usuario")
    @Cascade (CascadeType.ALL)
    private Emprestimo emprestimo;
    
    @OneToMany
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
}
