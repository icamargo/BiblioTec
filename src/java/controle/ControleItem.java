/**
 *
 * @author Igor
 */
package controle;

import DAO.ItemDAO;
import DAO.PessoaDAO;
import entidade.AcademicoPrototype;
import entidade.Emprestimo;
import entidade.ItemPrototype;
import entidade.LivroPrototype;
import entidade.PeriodicoPrototype;
import entidade.UsuarioPrototype;
import java.io.IOException;
import java.text.DateFormat;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean (name = "controleItem")
@SessionScoped
public class ControleItem {
    public static final int SEM_FILTRO = 0;
    public static final int FILTRO_TIPO_NOME_AUTOR = 1;
    public static final int FILTRO_TIPO_NOME = 2;
    public static final int FILTRO_TIPO_AUTOR = 3;
    public static final int FILTRO_TIPO = 4;
    public static final int FILTRO_NOME_AUTOR = 5;
    public static final int FILTRO_NOME = 6;
    public static final int FILTRO_AUTOR = 7;
    
    private LivroPrototype livro = new LivroPrototype();
    private AcademicoPrototype academico = new AcademicoPrototype();
    private PeriodicoPrototype periodico = new PeriodicoPrototype();
    
    private LivroPrototype prototipoLivro = new LivroPrototype();
    private AcademicoPrototype prototipoAcademico = new AcademicoPrototype();
    private PeriodicoPrototype prototipoPeriodico = new PeriodicoPrototype();
    
    private String filtroNome, filtroNumCatalogo, filtroAutor, filtroTipo;
    
    private ItemDAO itemDAO = new ItemDAO();
    private ControleCritClas controleCritClas = new ControleCritClas();
    private List itens;
    
    private String motivoInativacao, detalhesInativacao;
    private ControleEmprestimo controleEmprestimo = new ControleEmprestimo();
    private PessoaDAO pessoaDAO = new PessoaDAO();
    
    private UsuarioPrototype usuario = new UsuarioPrototype();
    private Emprestimo emprestimo = new Emprestimo();
    private StringBuffer detalhesInadimplencia = new StringBuffer("Usuário inadimplente por ");
    private StringBuffer usuarioResponsavel = new StringBuffer("Usuário responsável: ");
    private DateFormat formataData = DateFormat.getDateInstance();
    
    public ControleItem(){
    }
    public String adicionarLivro() throws IOException{
        ItemPrototype livroNovo = prototipoLivro.clonar();
        livroNovo = livro;
        livroNovo.setCodClassificador(controleCritClas.geraCodClassificador("Livro"));
        itemDAO.add(livroNovo);
        livro = new LivroPrototype();
        return "interfaceBibliotecario?faces-redirect=true";
    }
    public String adicionarAcademico() throws IOException{
        ItemPrototype academicoNovo = prototipoAcademico.clonar();
        academicoNovo = academico;
        itemDAO.add(academicoNovo);
        academico = new AcademicoPrototype();
        return "interfaceBibliotecario?faces-redirect=true"; 
    }
    public String adicionarPeriodico() throws IOException{
        ItemPrototype periodicoNovo = prototipoPeriodico.clonar();
        periodicoNovo = periodico;
        itemDAO.add(periodicoNovo);
        periodico = new PeriodicoPrototype();
        return "interfaceBibliotecario?faces-redirect=true"; 
    }
    public List listarItens(){
        int intVlrFiltroNumCatalogo;
        
        if(!(filtroNumCatalogo.equals(""))){
            //filtra por numero catalogo
            intVlrFiltroNumCatalogo = Integer.parseInt(filtroNumCatalogo);
            itens = itemDAO.getItem(intVlrFiltroNumCatalogo);
        }
        else if((filtroTipo != null) && !(filtroTipo.equals(""))){
            if(!(filtroNome.equals(""))){
                if(!(filtroAutor.equals(""))){
                    //filtra por tipo, nome e autor
                    itens = itemDAO.getLista(FILTRO_TIPO_NOME_AUTOR, filtroNome, filtroAutor, filtroTipo);
                }
                else{
                    //filtra por tipo e por nome
                    itens = itemDAO.getLista(FILTRO_TIPO_NOME, filtroNome, filtroAutor, filtroTipo);
                }
            }
            else if(!(filtroAutor.equals(""))){
                //filtra por tipo e autor
                itens = itemDAO.getLista(FILTRO_TIPO_AUTOR, filtroNome, filtroAutor, filtroTipo);
            }
            else{
                //filtra por tipo
                itens = itemDAO.getLista(FILTRO_TIPO, filtroNome, filtroAutor, filtroTipo);
            }
        }
        else if(!(filtroNome.equals(""))){
            if(!(filtroAutor.equals(""))){
                //filtra por nome e autor
                itens = itemDAO.getLista(FILTRO_NOME_AUTOR, filtroNome, filtroAutor, filtroTipo);
            }
            else {
                //filtra por nome
                itens = itemDAO.getLista(FILTRO_NOME, filtroNome, filtroAutor, filtroTipo);
            }
        }
        else if(!(filtroAutor.equals(""))){
            //filtra por autor
            itens = itemDAO.getLista(FILTRO_AUTOR, filtroNome, filtroAutor, filtroTipo);
        }
        else {
            //não filtra por nada
            itens = itemDAO.getLista(SEM_FILTRO, filtroNome, filtroAutor, filtroTipo);
        }
        //não tem itens cadastrados
        if (itens == null) {
            itens = new DAO.ItemDAO().getLista(SEM_FILTRO, filtroNome, filtroAutor, filtroTipo);
        }
        return itens;
    }
    public void exibirItem(ItemPrototype item) throws IOException{
        String tipoItem;
        
        tipoItem = item.getTipoItem();
        switch(tipoItem){
            case "Livro":
                this.livro = (LivroPrototype) item;
                FacesContext.getCurrentInstance().getExternalContext().redirect("exibirLivro.xhtml");
                break;
            case "Periodico":
                this.periodico = (PeriodicoPrototype) item;
                FacesContext.getCurrentInstance().getExternalContext().redirect("exibirPeriodico.xhtml");
                break;
            case "Academico":
                this.academico = (AcademicoPrototype) item;
                FacesContext.getCurrentInstance().getExternalContext().redirect("exibirAcademico.xhtml");
                break;
        }
    }
    public void atualizarLivro() throws IOException{
            itemDAO.atualizarItem(livro);
    }
    
    public void atualizarAcademico() throws IOException{
        itemDAO.atualizarItem(academico);
    }
    public void atualizarPeriodico() throws IOException{
        itemDAO.atualizarItem(periodico);
    }
    
    public String verificaInativacaoAcademico(){
        if(academico.getStatus().equals("Inativo")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Artigo Acadêmico já está Inativo!"));
            return null;
        }
        else{
            return "inativacaoAcademico?faces-redirect=true";
        }
    }
    
    public void ativarAcademico() throws IOException{
        if (!(academico.getStatus().equals("Inativo"))){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Artigo Acadêmico já está ativo!"));
        }
        else {
            academico.setStatus("Disponível");
            academico.setMotivoInativacao("");
            academico.setDetalhesInativacao("");
            itemDAO.atualizarItem(academico);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Artigo Acadêmico Ativado com Sucesso!"));
        }
    }
    
    public void inativarAcademico() throws IOException{
        academico.setMotivoInativacao(motivoInativacao);
        academico.setDetalhesInativacao(detalhesInativacao);
        academico.setStatus("Inativo");
        itemDAO.atualizarItem(academico);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Artigo Acadêmico Inativado com Sucesso!"));
    }
    
    public String verificaInativacaoLivro(){
        if(livro.getStatus().equals("Inativo")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Livro já está Inativo!"));
            return null;
        }
        else{
            return "inativacaoLivro?faces-redirect=true";
        }
    }
    
    public void ativarLivro() throws IOException{
        if (!(livro.getStatus().equals("Inativo"))){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Livro já está ativo!"));
        }
        else {
            livro.setStatus("Disponível");
            livro.setMotivoInativacao("");
            livro.setDetalhesInativacao("");
            itemDAO.atualizarItem(livro);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Livro Ativado com Sucesso!"));
        }
    }
    
    public void inativarLivro() throws IOException {
        if ((motivoInativacao.equals("UsuárioDanificou")) || (motivoInativacao.equals("UsuárioExtraviou"))) {
            this.insereInadimplenciaUsuario();
            detalhesInativacao = detalhesInativacao + "." + "\n" + usuarioResponsavel.toString();
            livro.setDetalhesInativacao(detalhesInativacao);
        }
        else{
            livro.setDetalhesInativacao(detalhesInativacao + ".");
        }
        livro.setMotivoInativacao(motivoInativacao);
        livro.setStatus("Inativo");
        itemDAO.atualizarItem(livro);   
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Livro Inativado com Sucesso!"));
    }
    
    private void insereInadimplenciaUsuario() throws IOException {
        emprestimo = controleEmprestimo.buscaUltimoEmprestimo(livro);
        usuario = emprestimo.getUsuario();
        String detalhesInadimplenciaAnterior = usuario.getDetalhesInadimplencia();
        
        switch (motivoInativacao) {
            case "UsuárioDanificou":
                detalhesInadimplencia.append("danificar o seguinte item: ");
                break;
            case "UsuárioExtraviou":
                detalhesInadimplencia.append("extraviar o seguinte item: ");
                break;
        }
        detalhesInadimplencia.append(livro.getNome());
        detalhesInadimplencia.append(", no emprestimo devolvido em: ");
        detalhesInadimplencia.append(formataData.format(emprestimo.getDataDevolucao().getTime()));
        detalhesInadimplencia.append(".");

        if (usuario.getSituacao().equals("Inadimplente")) {
            usuario.setDetalhesInadimplencia(detalhesInadimplenciaAnterior + "\n\n" + detalhesInadimplencia.toString());
        }
        else{
            usuario.setDetalhesInadimplencia(detalhesInadimplencia.toString());
        }
        
        usuario.setSituacao("Inadimplente");

        usuarioResponsavel.append(String.valueOf(usuario.getCodigo()));
        usuarioResponsavel.append(" - ");
        usuarioResponsavel.append(usuario.getNome());

        pessoaDAO.atualizarPessoa(usuario);
    }
    
    public String verificaInativacaoPeriodico(){
        if(periodico.getStatus().equals("Inativo")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Periódico já está Inativo!"));
            return null;
        }
        else{
            return "inativacaoPeriodico?faces-redirect=true";
        }
    }
    
    public void ativarPeriodico() throws IOException{
        if (!(periodico.getStatus().equals("Inativo"))){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Periódico já está ativo!"));
        }
        else {
            periodico.setStatus("Disponível");
            periodico.setMotivoInativacao("");
            periodico.setDetalhesInativacao("");
            itemDAO.atualizarItem(periodico);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Periódico Ativado com Sucesso!"));
        }
    }
    
    public void inativarPeriodico() throws IOException{
        periodico.setMotivoInativacao(motivoInativacao);
        periodico.setDetalhesInativacao(detalhesInativacao);
        periodico.setStatus("Inativo");
        itemDAO.atualizarItem(periodico);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Periódico Inativado com Sucesso!"));
    }
    
    public LivroPrototype getLivro() {
        return livro;
    }

    public AcademicoPrototype getAcademico() {
        return academico;
    }

    public PeriodicoPrototype getPeriodico() {
        return periodico;
    }

    public String getFiltroNome() {
        return filtroNome;
    }

    public void setFiltroNome(String filtroNome) {
        this.filtroNome = filtroNome;
    }

    public String getFiltroNumCatalogo() {
        return filtroNumCatalogo;
    }

    public void setFiltroNumCatalogo(String filtroNumCatalogo) {
        this.filtroNumCatalogo = filtroNumCatalogo;
    }

    public String getFiltroAutor() {
        return filtroAutor;
    }

    public void setFiltroAutor(String filtroAutor) {
        this.filtroAutor = filtroAutor;
    }

    public List getItens() {
        return itens;
    }

    public void setItens(List itens) {
        this.itens = itens;
    }

    public String getFiltroTipo() {
        return filtroTipo;
    }

    public void setFiltroTipo(String filtroTipo) {
        this.filtroTipo = filtroTipo;
    }

    public String getMotivoInativacao() {
        return motivoInativacao;
    }

    public void setMotivoInativacao(String motivoInativacao) {
        this.motivoInativacao = motivoInativacao;
    }

    public String getDetalhesInativacao() {
        return detalhesInativacao;
    }

    public void setDetalhesInativacao(String detalhesInativacao) {
        this.detalhesInativacao = detalhesInativacao;
    }
    
    
}
