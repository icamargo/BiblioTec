/**
 *
 * @author Igor
 */
package controle;

import DAO.ItemDAO;
import entidade.AcademicoPrototype;
import entidade.ItemPrototype;
import entidade.LivroPrototype;
import entidade.PeriodicoPrototype;
import java.io.IOException;
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
    private List itens;
    
    public ControleItem(){
    }
    public String adicionarLivro() throws IOException{
        ItemPrototype livroNovo = prototipoLivro.clonar();
        livroNovo = livro;
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
    public void inativarAcademico() throws IOException{
        if(academico.getStatus().equals("Disponível")){
            academico.setStatus("Inativo");
        }
        else{
            academico.setStatus("Disponível");
        }
        itemDAO.atualizarItem(academico);
        FacesContext.getCurrentInstance().getExternalContext().redirect("gerenciarItens.xhtml");
    }
    
    public void inativarLivro() throws IOException{
        if(livro.getStatus().equals("Disponível")){
            livro.setStatus("Inativo");
        }
        else{
            livro.setStatus("Disponível");
        }
        itemDAO.atualizarItem(livro);
        FacesContext.getCurrentInstance().getExternalContext().redirect("gerenciarItens.xhtml");
    }
    
    public void inativarPeriodico() throws IOException{
        if(periodico.getStatus().equals("Disponível")){
            periodico.setStatus("Inativo");
        }
        else{
            periodico.setStatus("Disponível");
        }
        itemDAO.atualizarItem(periodico);
        FacesContext.getCurrentInstance().getExternalContext().redirect("gerenciarItens.xhtml");
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
    
    
}
