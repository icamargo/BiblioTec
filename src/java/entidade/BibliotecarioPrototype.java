/**
 *
 * @author Pedro
 */
package entidade;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue (value = "Bibliotecario")
public class BibliotecarioPrototype extends PessoaPrototype{
    private String setor;

    public BibliotecarioPrototype() {
        this.ativo = true;
    }
    
    public BibliotecarioPrototype(BibliotecarioPrototype bibliotecario){
        this.setor = bibliotecario.getSetor();
    }
    
    @Override
    public PessoaPrototype clonar(){
        return new BibliotecarioPrototype (this);
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }
}