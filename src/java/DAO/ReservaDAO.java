package DAO;

import entidade.PessoaPrototype;
import entidade.Reserva;
import entidade.UsuarioPrototype;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
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
public class ReservaDAO {
    private Session session;
    private Transaction trans;
    private Criteria cri;
    private List<Reserva> lista;
    
    private void preparaSessao(){
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }catch(HibernateException e){
            session = HibernateUtil.getSessionFactory().openSession(); 
        }
        trans = session.beginTransaction();
    }
    
    public void atualizaReserva(Reserva reserva){
        this.preparaSessao();
        session.merge(reserva);
        trans.commit();
        session.close();
    }
    
    public void cancelaReserva(Reserva reserva){
        this.preparaSessao();
        session.merge(reserva);
        trans.commit();
        session.close();
    }
    
    public void novaReserva(Reserva reserva) throws IOException{
        this.preparaSessao();
        session.save(reserva);
        trans.commit();
        session.close();
    }
    
    public List<Reserva> getReservas(int numeroCatalogo){
        this.preparaSessao();
        cri = session.createCriteria(Reserva.class);
        cri.add(Restrictions.eq("numeroCatalogo", numeroCatalogo));
        Criterion aberta =  Restrictions.eq("statusReserva", "Aberta");
        Criterion efetivada = Restrictions.eq("statusReserva", "Efetivada");
        LogicalExpression expOu = Restrictions.or(aberta, efetivada);
        cri.add(expOu);
        lista = cri.list();
        trans.commit();
        session.close();
        return lista;
    }
    
    public List<Reserva> todasReservas(){
        this.preparaSessao();       
        cri = session.createCriteria(Reserva.class);
        Criterion aberta = Restrictions.eq("statusReserva", "Aberta");
        Disjunction expOu = Restrictions.or(aberta);
        cri.add(expOu);
        lista = cri.list();
        trans.commit();
        session.close();
        return lista;
    }
    
    public Calendar getMaiorDataDisponibilizacao(int numeroCatalogo){
        this.preparaSessao();
        cri = session.createCriteria(Reserva.class);
        ProjectionList projList = Projections.projectionList();
        Calendar maiorDataDisponibilizacao = Calendar.getInstance();      
        projList.add(Projections.max("dataDisponibilizacao"));
        cri.add(Restrictions.eq("numeroCatalogo", numeroCatalogo));
        cri.setProjection(projList);
        cri.setMaxResults(1);
        maiorDataDisponibilizacao = (Calendar) cri.uniqueResult();
        trans.commit();
        session.close();
        return maiorDataDisponibilizacao;
    }
    
    public void atualizarPessoa(PessoaPrototype pessoa){
        this.preparaSessao();
        session.merge(pessoa);
        trans.commit();//confirmaçao
    }
       
    public Reserva buscarReseva(int livro){
        Reserva reserva = null;       
        this.preparaSessao();
        cri = session.createCriteria(Reserva.class);       
        cri.add(Restrictions.eq("numeroCatalogo", livro));
        cri.setMaxResults(1);
        reserva = (Reserva) cri.uniqueResult();        
        trans.commit();
        session.close();
        return reserva;
    }
    
    public Reserva buscaReservaExistente(int codigoUsuario, int numeroCatalogo){
        Reserva reserva = null;
        this.preparaSessao();
        cri = session.createCriteria(Reserva.class);        
        cri.add(Restrictions.eq("codigoUsuario", codigoUsuario));
        cri.add(Restrictions.eq("numeroCatalogo", numeroCatalogo));
        cri.add(Restrictions.eq("statusReserva", "Aberta"));
        reserva = (Reserva) cri.uniqueResult();      
        trans.commit();
        session.close();
        return reserva;
    }
}
