/**
 *
 * @author Pedro
 */
package entidade;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Usuario")
public class UsuarioPrototype extends PessoaPrototype{
    private String situacao;

    public UsuarioPrototype() {
        this.situacao = "Normal";
        this.ativo = true;
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
