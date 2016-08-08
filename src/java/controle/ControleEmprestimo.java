package controle;

import DAO.EmprestimoDAO;
import DAO.ItemDAO;
import DAO.PessoaDAO;
import DAO.ReservaDAO;
import entidade.Emprestimo;
import entidade.LivroPrototype;
import entidade.Reserva;
import entidade.UsuarioPrototype;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
/**
 *
 * @author Igor
 */
@ManagedBean (name = "controleEmprestimo")
@SessionScoped
public class ControleEmprestimo {
    
    private Emprestimo emprestimo;
    private final PessoaDAO pessoaDAO = new PessoaDAO();
    private final ItemDAO itemDAO = new ItemDAO();
    private final EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
    private final ReservaDAO reservaDAO = new ReservaDAO();
    
    private int codigoUsuario, numeroCatalogo;
    
    public void devolucao() throws IOException{
        UsuarioPrototype usuario;
        LivroPrototype livro;
        List<Emprestimo> emprestimos;
        Calendar dataDevolvido = Calendar.getInstance();
        
        livro = itemDAO.getLivroPorNumeroCatalogo(numeroCatalogo);
        if(livro!=null){
            emprestimos = emprestimoDAO.getEmprestimos(livro);
            if(emprestimos!=null && emprestimos.size() > 0){
                for(Emprestimo emp: emprestimos){
                    if(emp.getStatusEmprestimo().equals("Aberto")){
                        emprestimo = emp;
                        if(emprestimo.getDataDevPrevista().compareTo(dataDevolvido) == 1 || emprestimo.getDataDevPrevista().compareTo(dataDevolvido) == 0){
                            livro.setStatus("Disponível");
                            itemDAO.atualizarItem(livro);

                            emprestimo.setStatusEmprestimo("Fechado");
                            emprestimo.setDataDevolucao(dataDevolvido);
                            emprestimoDAO.atualizar(emprestimo); 
                            FacesContext.getCurrentInstance().getExternalContext().redirect("interfaceBalconista.xhtml");
                        }
                        else{
                            usuario = emprestimo.getUsuario();
                            usuario.setSituacao("Inadimplente");
                            pessoaDAO.atualizarPessoa(usuario);
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Atraso na entrega favor regularize sua situaçao"));
                        }  
                    }
                }                      
            }
        }
        else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Item nao encontrado"));
        }
    }
    
    public void renovar(Emprestimo emprestimo) throws IOException{
        LivroPrototype livro;
        Calendar dataNova;
        List<Reserva> reservas;
        
        livro = emprestimo.getLivro();
        numeroCatalogo = livro.getNumeroCatalogo();
  
        reservas = reservaDAO.getReservas(numeroCatalogo);
        if(reservas!= null && reservas.size() > 0){
            for(Reserva res: reservas){
                if(res.getStatusReserva().equals("Aberta")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Item possui reserva"));
                    return;
                }
            }
            dataNova = emprestimo.getDataDevPrevista();
            dataNova.add(Calendar.DAY_OF_MONTH, 5);
            emprestimo.setDataDevPrevista(dataNova);
            emprestimo.setQtdeRenovacoes(emprestimo.getQtdeRenovacoes()+1);
            emprestimoDAO.atualizar(emprestimo);
            FacesContext.getCurrentInstance().getExternalContext().redirect("emprestimos.xhtml");
        }
        else{
            dataNova = emprestimo.getDataDevPrevista();
            dataNova.add(Calendar.DAY_OF_MONTH, 5);
            emprestimo.setDataDevPrevista(dataNova);
            emprestimoDAO.atualizar(emprestimo); 
            FacesContext.getCurrentInstance().getExternalContext().redirect("emprestimos.xhtml");
        }
    }

    
    public void criarEmprestimo() throws IOException{
        
        String situacaoUsuario;
        LivroPrototype livro;
        UsuarioPrototype usuario;
        List<Reserva> reservas;
        Calendar dataEmprestimo = Calendar.getInstance();
        Calendar dataDevPrevista = Calendar.getInstance();
        
        
        emprestimo = new Emprestimo();
        livro = itemDAO.getLivroPorNumeroCatalogo(numeroCatalogo);
        if(livro!=null){
   
            reservas = reservaDAO.getReservas(numeroCatalogo);
            if(reservas!=null && reservas.size() > 0){
                for(Reserva res: reservas){
                    if(res.getStatusReserva().equals("Aberta")){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Item possui reserva"));
                        return;
                    }
                }
                usuario = pessoaDAO.getUsuarioPorCodigo(codigoUsuario);
            
                if(usuario==null){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario nao encontrado!"));
                }
                else{
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
                            emprestimo.setQtdeRenovacoes(0);
                            emprestimoDAO.add(emprestimo);

                            usuario.getEmprestimos().add(emprestimo);
                            pessoaDAO.atualizarPessoa(usuario);

                            FacesContext.getCurrentInstance().getExternalContext().redirect("interfaceBalconista.xhtml");
                        }

                        else{
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Livro nao esta disponivel para emprestimo!"));
                        }
                    }
                    else{
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario possui alguma restriçao!"));
                    }
                }
            }
            else{
                usuario = pessoaDAO.getUsuarioPorCodigo(codigoUsuario);
            
                if(usuario==null){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario nao encontrado!"));
                }
                else{
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

                            usuario.getEmprestimos().add(emprestimo);
                            pessoaDAO.atualizarPessoa(usuario);

                            FacesContext.getCurrentInstance().getExternalContext().redirect("interfaceBalconista.xhtml");
                        }

                        else{
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Livro nao esta disponivel para emprestimo!"));
                        }
                    }
                    else{
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario possui alguma restriçao!"));
                    }
                }
            }          
        }
        else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Item nao encontrado no catalogo!"));
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
