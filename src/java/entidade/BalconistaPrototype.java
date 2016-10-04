/**
 *
 * @author Igor
 */
package entidade;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Balconista")
public class BalconistaPrototype extends PessoaPrototype{
    private String turnoTrabalho;

    public BalconistaPrototype() {
        this.ativo = true;
    }
    public BalconistaPrototype(BalconistaPrototype balconista){
        this.turnoTrabalho = balconista.getTurnoTrabalho();
    }
    
    @Override
    public PessoaPrototype clonar(){
        return new BalconistaPrototype (this);
    }

    public String getTurnoTrabalho() {
        return turnoTrabalho;
    }

    public void setTurnoTrabalho(String turnoTrabalho) {
        this.turnoTrabalho = turnoTrabalho;
    }
}
