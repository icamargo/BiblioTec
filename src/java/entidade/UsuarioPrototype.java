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

@Entity
@DiscriminatorValue(value = "Usuario")
public class UsuarioPrototype extends PessoaPrototype{
    private String situacao;
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
