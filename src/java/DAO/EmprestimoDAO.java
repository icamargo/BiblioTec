package DAO;

import entidade.Emprestimo;
import entidade.LivroPrototype;
import entidade.UsuarioPrototype;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
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
    public void novoEmprestimo(Emprestimo emprestimo){
        this.preparaSessao();
        session.persist(emprestimo);
        trans.commit();
        session.close();
    }
    
    public Emprestimo getEmprestimo (LivroPrototype livro){
        Emprestimo emprestimo;
        
        this.preparaSessao();
        Criteria crit = session.createCriteria(Emprestimo.class);
        
        crit.add(Restrictions.eq("livro", livro));
        crit.setMaxResults(1);        
        emprestimo = (Emprestimo) crit.uniqueResult(); 
        
        trans.commit();
        session.close();
        return emprestimo;
    }
    
     public Emprestimo getEmprestimo2 (LivroPrototype livro){
        Emprestimo emprestimo;
        
        this.preparaSessao();
        Criteria crit = session.createCriteria(Emprestimo.class);
        
        crit.add(Restrictions.eq("livro2", livro));
        crit.setMaxResults(1);        
        emprestimo = (Emprestimo) crit.uniqueResult(); 
        
        trans.commit();
        session.close();
        return emprestimo;
    }
     
      public Emprestimo getEmprestimo3 (LivroPrototype livro){
        Emprestimo emprestimo;
        
        this.preparaSessao();
        Criteria crit = session.createCriteria(Emprestimo.class);
        
        crit.add(Restrictions.eq("livro3", livro));
        crit.setMaxResults(1);        
        emprestimo = (Emprestimo) crit.uniqueResult(); 
        
        trans.commit();
        session.close();
        return emprestimo;
    }
    
    public List<Emprestimo> getEmprestimos(LivroPrototype livro){
        this.preparaSessao();
        cri = session.createCriteria(Emprestimo.class);
        cri.add(Restrictions.eq("livro", livro));
        Criterion aberto =  Restrictions.eq("statusEmprestimo", "Aberto");
        Criterion fechado = Restrictions.eq("statusEmprestimo", "Fechado");
        LogicalExpression expOu = Restrictions.or(aberto, fechado);
        cri.add(expOu);
        lista = cri.list();
        trans.commit();
        session.close();
        return lista;
    }
    
    public void atualizar(Emprestimo emprestimo){
        this.preparaSessao();
        session.update(emprestimo);
        trans.commit();
        session.close();
    }
    
    public Emprestimo ultimoEmprestimoUsuario(UsuarioPrototype usuario, LivroPrototype livro, LivroPrototype livro2, LivroPrototype livro3, String status){
        this.preparaSessao();
        Emprestimo emprestimo;
        
        Criteria crit = session.createCriteria(Emprestimo.class);
        
        if(livro != null)crit.add(Restrictions.eq("livro", livro));
        if(livro2 != null)crit.add(Restrictions.eq("livro2", livro2));
        if(livro3 != null)crit.add(Restrictions.eq("livro3", livro3));
        crit.add(Restrictions.eq("usuario", usuario));
        crit.add(Restrictions.eq("statusEmprestimo", status));
        crit.setMaxResults(1);        
        emprestimo = (Emprestimo) crit.uniqueResult(); 
        
        trans.commit();
        session.close();
        return emprestimo;
    }
    
    public Emprestimo buscarUltimoEmprestimo(LivroPrototype livro){
        Emprestimo emprestimo = new Emprestimo();

        this.preparaSessao();
        
        String sql = "FROM Emprestimo WHERE dataDevolucao = (SELECT MAX(dataDevolucao) FROM Emprestimo WHERE livro = :livro OR livro2 = :livro OR livro3 = :livro) AND livro = :livro OR livro2 = :livro OR livro3 = :livro";
        Query consulta = session.createQuery(sql);
        consulta.setParameter("livro",livro);
        consulta.setParameter("livro2",livro);
        consulta.setParameter("livro3",livro);
        emprestimo = (Emprestimo) consulta.uniqueResult();
        
        trans.commit();
        session.close();

        return emprestimo;
    }
        
}
