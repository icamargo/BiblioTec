/**
 *
 * @author Igor
 */
package DAO;

import static controle.ControleItem.FILTRO_AUTOR;
import static controle.ControleItem.FILTRO_NOME;
import static controle.ControleItem.FILTRO_NOME_AUTOR;
import static controle.ControleItem.FILTRO_TIPO;
import static controle.ControleItem.FILTRO_TIPO_AUTOR;
import static controle.ControleItem.FILTRO_TIPO_NOME;
import static controle.ControleItem.FILTRO_TIPO_NOME_AUTOR;
import static controle.ControleItem.SEM_FILTRO;
import entidade.AcademicoPrototype;
import entidade.ItemPrototype;
import entidade.LivroPrototype;
import entidade.PeriodicoPrototype;
import java.io.IOException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import utils.HibernateUtil;

public class ItemDAO {
    private Session session;
    private Transaction trans;
    private List<ItemPrototype> lista;
    
    private void preparaSessao(){
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }catch(HibernateException e){
            session = HibernateUtil.getSessionFactory().openSession(); 
        }
        trans = session.beginTransaction();
    }
    
    public void add(ItemPrototype item) throws IOException{
        this.preparaSessao();
        session.save(item);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Item Cadastrado com Sucesso!"));
        trans.commit();
        session.close();
    }

    public List<ItemPrototype> getItem (int vlrFiltroNumCatalogo){
        this.preparaSessao();
        Criteria cri = session.createCriteria(ItemPrototype.class);
        cri.add(Restrictions.eq("numeroCatalogo", vlrFiltroNumCatalogo));
        this.lista = cri.list();
        trans.commit();
        session.close();
        return lista;
    }
    
    public LivroPrototype getLivroPorNumeroCatalogo (int numeroCatalogo){
        LivroPrototype livro = null;
        
        this.preparaSessao();
        Criteria cri = session.createCriteria(LivroPrototype.class);
        
        cri.add(Restrictions.eq("numeroCatalogo", numeroCatalogo));
        cri.setMaxResults(1);
        livro = (LivroPrototype) cri.uniqueResult();
        
        trans.commit();
        session.close();
        return livro;
    }
    
    public List<ItemPrototype> getLista(int tipoFiltro, String vlrFiltroNome, String vlrFiltroAutor, String vlrFiltroTipo) {
        this.preparaSessao();
        Criteria cri = session.createCriteria(ItemPrototype.class);
        switch(tipoFiltro){
            case SEM_FILTRO:
                //sem filtro não faz nada
                break;
            case FILTRO_TIPO_NOME_AUTOR:
                switch(vlrFiltroTipo){
                    case "Livro":
                        cri = session.createCriteria(LivroPrototype.class);
                        break;
                    case "Academico":
                        cri = session.createCriteria(AcademicoPrototype.class);
                        break;
                    case "Periodico":
                        cri = session.createCriteria(PeriodicoPrototype.class);
                        break;
                }
                cri.add(Restrictions.ilike("nome", vlrFiltroNome, MatchMode.ANYWHERE));
                cri.add(Restrictions.ilike("autor", vlrFiltroAutor, MatchMode.ANYWHERE));
                break;
            case FILTRO_TIPO_NOME:
                switch(vlrFiltroTipo){
                    case "Livro":
                        cri = session.createCriteria(LivroPrototype.class);
                        break;
                    case "Academico":
                        cri = session.createCriteria(AcademicoPrototype.class);
                        break;
                    case "Periodico":
                        cri = session.createCriteria(PeriodicoPrototype.class);
                        break;
                }
                cri.add(Restrictions.ilike("nome", vlrFiltroNome, MatchMode.ANYWHERE));
                break;
            case FILTRO_TIPO_AUTOR:
                switch(vlrFiltroTipo){
                    case "Livro":
                        cri = session.createCriteria(LivroPrototype.class);
                        break;
                    case "Academico":
                        cri = session.createCriteria(AcademicoPrototype.class);
                        break;
                    case "Periodico":
                        cri = session.createCriteria(PeriodicoPrototype.class);
                        break;
                }
                cri.add(Restrictions.ilike("autor", vlrFiltroAutor, MatchMode.ANYWHERE));
                break;
            case FILTRO_TIPO:
                switch(vlrFiltroTipo){
                    case "Livro":
                        cri = session.createCriteria(LivroPrototype.class);
                        break;
                    case "Academico":
                        cri = session.createCriteria(AcademicoPrototype.class);
                        break;
                    case "Periodico":
                        cri = session.createCriteria(PeriodicoPrototype.class);
                        break;
                }
                break;
            case FILTRO_NOME_AUTOR:
                cri.add(Restrictions.ilike("nome", vlrFiltroNome, MatchMode.ANYWHERE));
                cri.add(Restrictions.ilike("autor", vlrFiltroAutor, MatchMode.ANYWHERE));
                break;
            case FILTRO_NOME:
                cri.add(Restrictions.ilike("nome", vlrFiltroNome, MatchMode.ANYWHERE));
                break;
            case FILTRO_AUTOR:
                cri.add(Restrictions.ilike("autor", vlrFiltroAutor, MatchMode.ANYWHERE));
                break;                        
        }
        this.lista = cri.list();
        trans.commit();
        session.close();
        return lista;
    }
    
    public void atualizarItem (ItemPrototype item) throws IOException{
        this.preparaSessao();
        session.update(item);
        //FacesContext context = FacesContext.getCurrentInstance();
        //context.addMessage(null, new FacesMessage("Item Atualizado com Sucesso!"));
        //context.getExternalContext().redirect("gerenciarItens.xhtml");
        trans.commit();//confirmaçao
        session.close();
    }

    
}
