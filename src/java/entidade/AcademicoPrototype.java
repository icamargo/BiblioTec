/**
 *
 * @author Igor
 */
package entidade;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue (value = "Academico")
public class AcademicoPrototype extends ItemPrototype{
    private String modalidade;
    
    public AcademicoPrototype(){
        status = "Disponivel";
    }
    public AcademicoPrototype (AcademicoPrototype academico){
        this.modalidade = academico.getModalidade();
    }
    
    @Override
    public ItemPrototype clonar(){
        return new AcademicoPrototype (this);
    }
    public String getModalidade() {
        return modalidade;
    }
    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }    
}
