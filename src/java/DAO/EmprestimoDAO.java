package DAO;

import entidade.Emprestimo;
import entidade.LivroPrototype;
import entidade.UsuarioPrototype;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import utils.HibernateUtil;
/**
 *
 * @author Igor
 */
public class EmprestimoDAO {
    private Session session;
    private Transaction trans;
    private Criteria cri;
    private List<Emprestimo> lista;
    
    private void preparaSessao(){
        if ((session == null) || (!(session.isOpen()))){
            session = HibernateUtil.getSessionFactory().openSession();
        }
        trans = session.beginTransaction();
    }
    public void add(Emprestimo emprestimo){
        this.preparaSessao();
        session.persist(emprestimo);
        trans.commit();
        session.close();
    }
    
    public Emprestimo getEmpretimo (LivroPrototype livro){
        Emprestimo emprestimo = null;
        
        this.preparaSessao();
        Criteria crit = session.createCriteria(Emprestimo.class);
        
        crit.add(Restrictions.eq("livro", livro));
        crit.setMaxResults(1);
        emprestimo = (Emprestimo) crit.uniqueResult();
        
        trans.commit();
        session.close();
        return emprestimo;
    }
    
    public void atualizar(Emprestimo emprestimo){
        this.preparaSessao();
        session.update(emprestimo);
        trans.commit();
        session.close();
    }
}
