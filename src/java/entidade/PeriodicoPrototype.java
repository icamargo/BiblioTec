/**
 *
 * @author Igor
 */
package entidade;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue (value = "Periodico")
public class PeriodicoPrototype extends ItemPrototype{
    private String issn;
    private String tipoPeriodico;
    
    public PeriodicoPrototype(){
        status = "Disponível";
    }
    public PeriodicoPrototype(PeriodicoPrototype periodico){
        this.issn = periodico.getIssn();
        this.tipoPeriodico = periodico.getTipoPeriodico();
    }
    
    @Override
    public PeriodicoPrototype clonar(){
        return new PeriodicoPrototype(this);
    }
    public String getIssn() {
        return issn;
    }

    public String getTipoPeriodico() {
        return tipoPeriodico;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public void setTipoPeriodico(String tipoPeriodico) {
        this.tipoPeriodico = tipoPeriodico;
    }
}
