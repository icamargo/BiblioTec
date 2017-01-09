package DAO;

import entidade.CriteriosClassificacao;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import utils.HibernateUtil;

/**
 *
 * @author Igor
 */
public class CritClasDAO {
    private Session session;
    private Transaction trans;
    
    private void preparaSessao(){
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }catch(HibernateException e){
            session = HibernateUtil.getSessionFactory().openSession(); 
        }
        trans = session.beginTransaction();
    }
    
    public List<CriteriosClassificacao> buscaCriterios(String tipoCriterio){
        List<CriteriosClassificacao> criterios = new ArrayList<CriteriosClassificacao>();
        this.preparaSessao();
        Criteria cri = session.createCriteria(CriteriosClassificacao.class);
        cri.add(Restrictions.ilike("tipoCriterio", tipoCriterio, MatchMode.START));
        cri.addOrder(Order.asc("descCriterio"));
        criterios = cri.list();
        trans.commit();
        session.close();
        return criterios;
    }
    
    public CriteriosClassificacao buscaCriterioId (int idCriterio){
        this.preparaSessao();
        CriteriosClassificacao criterio = null;
        Criteria cri = session.createCriteria(CriteriosClassificacao.class);
        cri.add(Restrictions.eq("idCriterio", idCriterio));
        cri.setMaxResults(1);
        criterio = (CriteriosClassificacao) cri.uniqueResult();
        trans.commit();
        session.close();
        return criterio;
    }
    
    public CriteriosClassificacao buscaCriterioDesc (String descCriterio){
        this.preparaSessao();
        CriteriosClassificacao criterio = null;
        Criteria cri = session.createCriteria(CriteriosClassificacao.class);
        cri.add(Restrictions.eq("descCriterio", descCriterio));
        cri.setMaxResults(1);
        criterio = (CriteriosClassificacao) cri.uniqueResult();
        trans.commit();
        session.close();
        return criterio;
    }
    
    public int buscaMaiorCodigoCrit(String tipoCriterio){
        int maiorCodCriterio;
        String sql = "SELECT MAX(codCriterio) FROM CriteriosClassificacao WHERE tipoCriterio = :tipoCriterio";
        
        this.preparaSessao();
        Query consulta = session.createQuery(sql);
        consulta.setString("tipoCriterio", tipoCriterio);
        maiorCodCriterio = (int) consulta.uniqueResult();
        
        trans.commit();
        session.close();
        
        return maiorCodCriterio;
    }
    
    public void addCriterio (CriteriosClassificacao novo){
        this.preparaSessao();
        session.save(novo);
        trans.commit();
        session.close();
    }
}
