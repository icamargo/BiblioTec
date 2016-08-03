/**
 *
 * @author Igor
 */
package entidade;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue (value = "Livro")
public class LivroPrototype extends ItemPrototype{
    private String isbn;
    
    public LivroPrototype(){
        status = "Disponivel";
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
