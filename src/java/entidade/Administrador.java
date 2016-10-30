package entidade;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author Igor
 */
@Entity
@DiscriminatorValue(value = "Administrador")
public class Administrador extends PessoaPrototype{

    public Administrador(){
        this.ativo = true;
        this.nome = "Administrador";
        this.email = "Administrador";
        this.senha = "BiblioTec";
    }
    
    public Administrador (Administrador administrador){
        this.ativo = administrador.isAtivo();
        this.nome = administrador.getNome();
        this.email = administrador.getEmail();
        this.senha = administrador.getSenha();
    }
    
    @Override
    public PessoaPrototype clonar() {
        return new Administrador (this);
    }
    
}
