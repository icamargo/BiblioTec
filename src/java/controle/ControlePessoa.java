/**
 *
 * @author Igor
 */
package controle;

import DAO.PessoaDAO;
import static controle.ControleItem.FILTRO_NOME;
import static controle.ControleItem.FILTRO_TIPO;
import static controle.ControleItem.FILTRO_TIPO_NOME;
import static controle.ControleItem.SEM_FILTRO;
import entidade.BalconistaPrototype;
import entidade.BibliotecarioPrototype;
import entidade.PessoaPrototype;
import entidade.UsuarioPrototype;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


@ManagedBean(name = "controlePessoa")
@SessionScoped
public class ControlePessoa {
    private UsuarioPrototype usuario = new UsuarioPrototype();
    private BalconistaPrototype balconista = new BalconistaPrototype();
    private BibliotecarioPrototype bibliotecario = new BibliotecarioPrototype();
    
    private final UsuarioPrototype prototipoUsuario = new UsuarioPrototype();
    private final BalconistaPrototype prototipoBalconista = new BalconistaPrototype();
    private final BibliotecarioPrototype prototipoBibliotecario = new BibliotecarioPrototype();
    
    private String filtroNome, filtroCodigo, filtroCpf, filtroRg, filtroTipo;
    
    private final PessoaDAO pessoaDAO = new PessoaDAO();
    private List pessoas;
    
    public ControlePessoa() {
    }
    
    public String adicionarUsuario() throws IOException {
        String retorno = null;
        try{
            if(validarEmail(usuario.getEmail())){
                PessoaPrototype usuarioNovo = prototipoUsuario.clonar();
                usuarioNovo = usuario;
                pessoaDAO.add(usuarioNovo);
                usuario = new UsuarioPrototype();
                retorno =  "interfaceBalconista?faces-redirect=true";
            }
            else FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Email invalido")); 
        }catch(Exception ex){          
        }
        return retorno;
    }
    
    public String adicionarUsuarioSemLogin() throws IOException {
        String retorno = null;
        try{
            if(validarEmail(usuario.getEmail())){
                PessoaPrototype usuarioNovo = prototipoUsuario.clonar();
                usuarioNovo = usuario;
                pessoaDAO.addSemLogin(usuarioNovo);
                //send_email();
                usuario = new UsuarioPrototype();
                retorno = "interfaceLogin?faces-redirect=true";
            }
            else FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Email invalido"));            
        }catch(Exception ex){
        }
        return retorno;
    }
    
    public String adicionarBalconista() throws IOException{
        String retorno = null;
        try{
                if(validarEmail(balconista.getEmail())){
                PessoaPrototype balconistaNovo = prototipoBalconista.clonar();
                balconistaNovo = balconista;
                pessoaDAO.add(balconistaNovo);
                balconista = new BalconistaPrototype();
                retorno = "interfaceAdministrador?faces-redirect=true";
            }
            else FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Email invalido"));
        }catch(Exception e){          
        }
        return retorno;
    }
    
    public String adicionarBibliotecario() throws IOException{
        String retorno = null;
        try{
            if(validarEmail(bibliotecario.getEmail())){
                PessoaPrototype bibliotecarioNovo = prototipoBibliotecario.clonar();
                bibliotecarioNovo = bibliotecario;
                pessoaDAO.add(bibliotecarioNovo);
                bibliotecario = new BibliotecarioPrototype();
                retorno =  "interfaceAdministrador?faces-redirect=true";
            }
            else FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Email invalido"));
        }catch(Exception e){
        }
        return retorno;
    }
    
    public static boolean validarEmail(String email){
        if((email == null) || (email.trim().length() == 0)){
            return false;
        }
        
        String emailPattern = "\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";
        Pattern pattern = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    public List listarPessoas() {
        int vlrFiltroCodigo;
        
        if(!(filtroCodigo.equals(""))){
            //filtra por codigo
            vlrFiltroCodigo = Integer.parseInt(filtroCodigo);
            pessoas = pessoaDAO.getPessoaPorCodigo(vlrFiltroCodigo);
        }
        else if(!(filtroCpf.equals(""))){
            //filtra por cpf
            pessoas = pessoaDAO.getPessoaPorCpf(filtroCpf);
        }
        else if(!(filtroRg.equals(""))){
            //filtra por rg
            pessoas = pessoaDAO.getPessoaPorRg(filtroRg);
        }
        else if(!(filtroTipo.equals(""))){
            if(!(filtroNome.equals(""))){
                //filtra por tipo e por nome
                pessoas = pessoaDAO.getPessoas(FILTRO_TIPO_NOME, filtroTipo, filtroNome);
            }
            else{
                //filtra por tipo
                pessoas = pessoaDAO.getPessoas(FILTRO_TIPO, filtroTipo, filtroNome);
            }
        }
        else if(!(filtroNome.equals(""))){
            //filtra por nome
            pessoas = pessoaDAO.getPessoas(FILTRO_NOME, filtroTipo, filtroNome);
        }
        else {
            //não filtra por nada
            pessoas = pessoaDAO.getPessoas(SEM_FILTRO, filtroTipo, filtroNome);
        }
        //não tem pessoas cadastrados
        if (pessoas == null) {
            pessoas = new DAO.PessoaDAO().getPessoas(SEM_FILTRO, filtroTipo, filtroNome);
        }
        return pessoas;
    }
    
    public List listarUsuarios() {
        int vlrFiltroCodigo;
        
        if(!(filtroCodigo.equals(""))){
            //filtra por codigo
            vlrFiltroCodigo = Integer.parseInt(filtroCodigo);
            pessoas = pessoaDAO.getUsuariosPorCodigo(vlrFiltroCodigo);
        }
        else if(!(filtroCpf.equals(""))){
            //filtra por cpf
            pessoas = pessoaDAO.getUsuarioPorCpf(filtroCpf);
        }
        else if(!(filtroRg.equals(""))){
            //filtra por rg
            pessoas = pessoaDAO.getUsuarioPorRg(filtroRg);
        }
        else if(!(filtroNome.equals(""))){
            //filtra por nome
            pessoas = pessoaDAO.getUsuarioPorNome(filtroNome);
        }
        else {
            //não filtra por nada
            pessoas = pessoaDAO.getUsuarios();
        }
        //não tem pessoas cadastrados
        if (pessoas == null) {
            pessoas = new DAO.PessoaDAO().getPessoas(SEM_FILTRO, filtroTipo, filtroNome);
        }
        return pessoas;
    }

    public void exibirPessoa(PessoaPrototype pessoa) throws IOException{
        String tipoPessoa;
        
        tipoPessoa = pessoa.getTipoPessoa();
        switch(tipoPessoa){
            case "Balconista":
                this.balconista = (BalconistaPrototype) pessoa;
                FacesContext.getCurrentInstance().getExternalContext().redirect("exibirBalconista.xhtml");
                break;
            case "Bibliotecario":
                this.bibliotecario = (BibliotecarioPrototype) pessoa;
                FacesContext.getCurrentInstance().getExternalContext().redirect("exibirBibliotecario.xhtml");
                break;
            case "Usuario":
                this.usuario = (UsuarioPrototype) pessoa;
                FacesContext.getCurrentInstance().getExternalContext().redirect("exibirUsuario.xhtml");
                break;
        }
    }
    public void exibirHistorico(PessoaPrototype pessoa) throws IOException{
        String tipoPessoa;
        
        tipoPessoa = pessoa.getTipoPessoa();
        switch(tipoPessoa){
            case "Usuario":
                this.usuario = (UsuarioPrototype) pessoa;
                FacesContext.getCurrentInstance().getExternalContext().redirect("historicoEmprestimos.xhtml");
                break;
        }
    }
    
    public void inativarBalconista() throws IOException{
        if(balconista.isAtivo()==true){
            balconista.setAtivo(false);
        }
        else{
            balconista.setAtivo(true);
        }
        pessoaDAO.atualizarPessoa(balconista);
        FacesContext.getCurrentInstance().getExternalContext().redirect("gerenciarPessoas.xhtml");
    }
    
    public void inativarBibliotecario() throws IOException{
       if(bibliotecario.isAtivo()==true){
            bibliotecario.setAtivo(false);
        }
        else{
            bibliotecario.setAtivo(true);
        }
        pessoaDAO.atualizarPessoa(bibliotecario);
        FacesContext.getCurrentInstance().getExternalContext().redirect("gerenciarPessoas.xhtml");
    }
    
    public void inativarUsuario() throws IOException{
        if(usuario.isAtivo()==true){
            usuario.setAtivo(false);
        }
        else{
            usuario.setAtivo(true);
        }
        pessoaDAO.atualizarPessoa(usuario);
        FacesContext.getCurrentInstance().getExternalContext().redirect("gerenciarPessoas.xhtml");
    }
    
    public String atualizarBalconista() throws IOException{
        pessoaDAO.atualizarPessoa(balconista);
        balconista = new BalconistaPrototype();
        return "gerenciarPessoas?faces-redirect=true";
    }
    
    public String atualizarBibliotecario() throws IOException{
        pessoaDAO.atualizarPessoa(bibliotecario);
        bibliotecario = new BibliotecarioPrototype();
        return "gerenciarPessoas?faces-redirect=true";
    }
    
    public String atualizarUsuario() throws IOException{
        pessoaDAO.atualizarPessoa(usuario);
        usuario = new UsuarioPrototype();
        return "gerenciarPessoas?faces-redirect=true";
    }
    
    public String buscarPefilAcesso(String email, String senha){
        String perfil;
        
        perfil = pessoaDAO.getPerfilAcesso(email, senha);
        
        return perfil;
    }

    public void removerInadimplenciaUsuario() throws IOException{
        usuario.setSituacao("Normal");
        usuario.setDetalhesInadimplencia("");
        pessoaDAO.atualizarPessoa(usuario);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Inadimplência removida!"));
    }
    

    public UsuarioPrototype getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioPrototype usuario) {
        this.usuario = usuario;
    }

    public BalconistaPrototype getBalconista() {
        return balconista;
    }

    public void setBalconista(BalconistaPrototype balconista) {
        this.balconista = balconista;
    }

    public BibliotecarioPrototype getBibliotecario() {
        return bibliotecario;
    }

    public void setBibliotecario(BibliotecarioPrototype bibliotecario) {
        this.bibliotecario = bibliotecario;
    }

    public List getPessoas() {
        return pessoas;
    }

    public void setPessoas(List pessoas) {
        this.pessoas = pessoas;
    }

    public String getFiltroNome() {
        return filtroNome;
    }

    public void setFiltroNome(String filtroNome) {
        this.filtroNome = filtroNome;
    }

    public String getFiltroCodigo() {
        return filtroCodigo;
    }

    public void setFiltroCodigo(String filtroCodigo) {
        this.filtroCodigo = filtroCodigo;
    }

    public String getFiltroCpf() {
        return filtroCpf;
    }

    public void setFiltroCpf(String filtroCpf) {
        this.filtroCpf = filtroCpf;
    }

    public String getFiltroRg() {
        return filtroRg;
    }

    public void setFiltroRg(String filtroRg) {
        this.filtroRg = filtroRg;
    }

    public String getFiltroTipo() {
        return filtroTipo;
    }

    public void setFiltroTipo(String filtroTipo) {
        this.filtroTipo = filtroTipo;
    }   
}
