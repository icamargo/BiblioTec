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
    private String isbn;
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
    
}
