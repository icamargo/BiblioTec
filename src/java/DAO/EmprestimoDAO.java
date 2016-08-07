package DAO;

import entidade.Emprestimo;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
}
