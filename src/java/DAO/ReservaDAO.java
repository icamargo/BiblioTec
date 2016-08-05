package DAO;

import entidade.ItemPrototype;
import entidade.Reserva;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
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
    
    private void preparaSessaoSaveUpdate(){
        session = HibernateUtil.getSessionFactory().openSession();
        trans = session.beginTransaction();
    }
    
    private void preparaSessaoConsulta(){
        session = HibernateUtil.getSessionFactory().openSession();
        trans = session.beginTransaction();
        cri = session.createCriteria(Reserva.class);
    }
    
    public void add(Reserva reserva) throws IOException{
        this.preparaSessaoSaveUpdate();
        session.save(reserva);
        trans.commit();
    }
    
    public List<Reserva> getReservas(int numeroCatalogo){
        this.preparaSessaoConsulta();
        cri.add(Restrictions.eq("numeroCatalogo", numeroCatalogo));
        Criterion aberta =  Restrictions.eq("statusReserva", "Aberta");
        Criterion efetivada = Restrictions.eq("statusReserva", "Efetivada");
        LogicalExpression expOu = Restrictions.or(aberta, efetivada);
        cri.add(expOu);
        lista = cri.list();
        return lista;
    }
    
    public Calendar getMaiorDataDisponibilizacao(int numeroCatalogo){
        this.preparaSessaoConsulta();
        ProjectionList projList = Projections.projectionList();
        Calendar maiorDataDisponibilizacao = Calendar.getInstance();
        
        projList.add(Projections.max("dataDisponibilizacao"));
        cri.add(Restrictions.eq("numeroCatalogo", numeroCatalogo));
        cri.setProjection(projList);
        cri.setMaxResults(1);
        maiorDataDisponibilizacao = (Calendar) cri.uniqueResult();
        
        return maiorDataDisponibilizacao;
    }
}
