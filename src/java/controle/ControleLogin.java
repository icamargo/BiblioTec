package controle;

import DAO.PessoaDAO;
import entidade.BalconistaPrototype;
import entidade.BibliotecarioPrototype;
import entidade.UsuarioPrototype;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
/**
 *
 * @author Igor
 */
@ManagedBean(name = "controleLogin")
@SessionScoped
public class ControleLogin implements Serializable{
    
    private String email;
    private String senha;
    private String perfil;
    
    private ControlePessoa controlePessoa;
    
    private UsuarioPrototype usuario;
    private BalconistaPrototype balconista;
    private BibliotecarioPrototype bibliotecario;
    private PessoaDAO pessoaDAO;
    
    public ControleLogin(){
    }
    
    public String autenticarAcesso(){
        controlePessoa = new ControlePessoa();
        
        perfil = controlePessoa.buscarPefilAcesso(email, senha);
        if (perfil != null) {
            switch (perfil) {
                case "Balconista":
                    return this.autenticarBalconista();

                case "Bibliotecario":
                    return this.autenticarBibliotecario();

                case "Usuario":
                    return this.autenticarUsuario();
            }
            return null;
        }
        else 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("E-mail/Senha não encontrado!"));
        return null;
    }
    
    public String autenticarBalconista(){
        pessoaDAO = new PessoaDAO();
        
        balconista = new BalconistaPrototype();
        
        balconista = pessoaDAO.buscarBalconista(email);
        
        if(balconista != null){
            if(balconista.isAtivo()){
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                session.setAttribute("pessoa", balconista);

                return "/AcessoAutenticado/AcessoBalconista/interfaceBalconista?faces-redirect=true";
            }
            else{
                return "/AcessoLivre/login?faces-redirect=true";
            }
        }
        else{
            return "/AcessoLivre/login?faces-redirect=true";
        }
    }
    
    public String autenticarBibliotecario(){
        pessoaDAO = new PessoaDAO();
        
        bibliotecario = new BibliotecarioPrototype();
        
        bibliotecario = pessoaDAO.buscarBibliotecario(email);
        
        if(bibliotecario != null){
            if(bibliotecario.isAtivo()){
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                session.setAttribute("pessoa", bibliotecario);

                return "/AcessoAutenticado/AcessoBibliotecario/interfaceBibliotecario?faces-redirect=true";
            }
            else{
                return "/AcessoLivre/login?faces-redirect=true";
            }
        }
        else{
            return "/AcessoLivre/login?faces-redirect=true";
        }
    }
    
    public String autenticarUsuario(){
        pessoaDAO = new PessoaDAO();
        
        usuario = new UsuarioPrototype();
        
        usuario = pessoaDAO.buscarUsuario(email);
            
        if(usuario != null){
            if(usuario.isAtivo()){
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                session.setAttribute("pessoa", usuario);

                return "/AcessoAutenticado/AcessoUsuario/interfaceUsuario?faces-redirect=true";
            }
            else{
                return "/AcessoLivre/login?faces-redirect=true";
            }
        }
        else{
            return "/AcessoLivre/login?faces-redirect=true";
        }
    }
    
    public void logout() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext().redirect("AcessoLivre/interfaceLogin.xhtml");            
    }

    public UsuarioPrototype getUsuario() {
        if(usuario == null){
            usuario = new UsuarioPrototype();
        }
        return usuario;
    }

    public void setUsuario(UsuarioPrototype usuario) {
        this.usuario = usuario;
    }

    public BalconistaPrototype getBalconista() {
        if(balconista == null){
            balconista = new BalconistaPrototype();
        }
        return balconista;
    }

    public void setBalconista(BalconistaPrototype balconista) {
        this.balconista = balconista;
    }

    public BibliotecarioPrototype getBibliotecario() {
        if(bibliotecario == null){
            bibliotecario = new BibliotecarioPrototype();
        }
        return bibliotecario;
    }

    public void setBibliotecario(BibliotecarioPrototype bibliotecario) {
        this.bibliotecario = bibliotecario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
}
