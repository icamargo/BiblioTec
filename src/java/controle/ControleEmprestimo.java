package controle;

import DAO.EmprestimoDAO;
import DAO.ItemDAO;
import DAO.PessoaDAO;
import entidade.Emprestimo;
import entidade.LivroPrototype;
import entidade.UsuarioPrototype;
import java.io.IOException;
import java.util.Calendar;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
/**
 *
 * @author Igor
 */
@ManagedBean (name = "controleEmprestimo")
@SessionScoped
public class ControleEmprestimo {
    private Emprestimo emprestimo;
    private PessoaDAO pessoaDAO = new PessoaDAO();
    private ItemDAO itemDAO = new ItemDAO();
    private EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
    
    private int codigoUsuario, numeroCatalogo;

    public void criarEmprestimo() throws IOException{
        String situacaoUsuario;
        LivroPrototype livro = new LivroPrototype();
        Calendar dataEmprestimo = Calendar.getInstance();
        Calendar dataDevPrevista = Calendar.getInstance();
        UsuarioPrototype usuario = new UsuarioPrototype();
        
        emprestimo = new Emprestimo();
        
        if(numeroCatalogo != 0){
            livro = itemDAO.getLivroPorNumeroCatalogo(numeroCatalogo);
        }
        else{
            //MENSAGEM!! Preencha pelo menos um item para emprestimo
        }
        
        usuario = pessoaDAO.getUsuarioPorCodigo(codigoUsuario);
        situacaoUsuario = usuario.getSituacao();
        
        if(!(situacaoUsuario.equals("Inadimplente"))){
            if(livro.getStatus().equals("Disponível")){
                dataDevPrevista.add(Calendar.DAY_OF_MONTH, 10);

                livro.setStatus("Emprestado");
                itemDAO.atualizarItem(livro);
                
                emprestimo.setDataDevPrevista(dataDevPrevista);
                emprestimo.setDataEmprestimo(dataEmprestimo);
                emprestimo.setUsuario(usuario);
                emprestimo.setStatusEmprestimo("Aberto");
                emprestimo.setLivro(livro);
                emprestimoDAO.add(emprestimo);
            }
            else{
                //Livro não stá disponível nao pode ser emprestado
            }
            
        }
        else{
            //Usuario inadimplente nao pode emprestar item
        }
    }

    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public int getNumeroCatalogo() {
        return numeroCatalogo;
    }

    public void setNumeroCatalogo(int numeroCatalogo) {
        this.numeroCatalogo = numeroCatalogo;
    }
}
