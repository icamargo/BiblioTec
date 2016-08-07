package DAO;

import entidade.PessoaPrototype;
import entidade.Reserva;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import utils.HibernateUtil;
/**
 *
 * @author Reserva
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
    
    public void cancelaReserva(Reserva reserva){
        this.preparaSessao();
        session.update(reserva);
        trans.commit();
    }
    
    public void add(Reserva reserva) throws IOException{
        this.preparaSessao();
        session.save(reserva);
        trans.commit();
    }
    
    public List<Reserva> getReservas(int numeroCatalogo){
        this.preparaSessao();
        cri = session.createCriteria(Reserva.class);
        cri.add(Restrictions.eq("numeroCatalogo", numeroCatalogo));
        Criterion aberta =  Restrictions.eq("statusReserva", "Aberta");
        Criterion efetivada = Restrictions.eq("statusReserva", "Efetivada");
        Criterion cancelada = Restrictions.eq("statusReserva", "Cancelada");
        Disjunction expOu = Restrictions.or(aberta, efetivada, cancelada);
        cri.add(expOu);
        lista = cri.list();
        trans.commit();
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
        return maiorDataDisponibilizacao;
    }
    
    public void atualizarPessoa(PessoaPrototype pessoa){
        this.preparaSessao();
        session.update(pessoa);
        trans.commit();//confirmaçao
    }
}
