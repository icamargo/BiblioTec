/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import DAO.PessoaDAO;
import entidade.BalconistaPrototype;
import entidade.BibliotecarioPrototype;
import entidade.PessoaPrototype;
import entidade.UsuarioPrototype;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Igor
 */
@ManagedBean(name = "loginController")
@SessionScoped
public class LoginControle implements Serializable{
    
    private UsuarioPrototype usuario;
    private BalconistaPrototype balconista;
    private BibliotecarioPrototype bibliotecario;
    private PessoaDAO pessoaDAO;
    
    public LoginControle(){
    }
    
    public String logarU(){
        
        pessoaDAO = new PessoaDAO();
        usuario = pessoaDAO.buscarUsuario(usuario);
            
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
    
    public String logarBi(){
        
        pessoaDAO = new PessoaDAO();
        bibliotecario = pessoaDAO.buscarBi(bibliotecario);
        
        if(bibliotecario != null){
            System.out.println("entro");
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
            System.out.println("saiu");
            return "/AcessoLivre/login?faces-redirect=true";
        }
    }
    
    public String logarBa(){
        
        pessoaDAO = new PessoaDAO();
        balconista = pessoaDAO.buscarBa(balconista);
        
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
    
    public void logout() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext().redirect("AcessoLivre/login.xhtml");
                
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
}
