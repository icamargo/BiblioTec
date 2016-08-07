/**
 *
 * @author Pedro
 */
package DAO;

import static controle.ControleItem.FILTRO_NOME;
import static controle.ControleItem.FILTRO_TIPO;
import static controle.ControleItem.FILTRO_TIPO_NOME;
import static controle.ControleItem.SEM_FILTRO;
import entidade.BalconistaPrototype;
import entidade.BibliotecarioPrototype;
import entidade.PessoaPrototype;
import entidade.UsuarioPrototype;
import java.io.IOException;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import utils.HibernateUtil;

public class PessoaDAO {

    private Session session;
    private Transaction trans;
    private List<PessoaPrototype> lista;
    
    private void preparaSessao(){
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }catch(HibernateException e){
            session = HibernateUtil.getSessionFactory().openSession(); 
        }
        trans = session.beginTransaction();
    }
    
    public void add(PessoaPrototype pessoa) throws IOException {
        //session = HibernateUtil.getSessionFactory().openSession();
        //trans = session.beginTransaction();
        this.preparaSessao();
        session.save(pessoa);
        trans.commit();
    }
    
    public void addSemLogin(PessoaPrototype pessoa) throws IOException {
        //session = HibernateUtil.getSessionFactory().openSession();
        //trans = session.beginTransaction();
        this.preparaSessao();
        session.save(pessoa);
        trans.commit();
    }
    
    public UsuarioPrototype buscarUsuario(UsuarioPrototype usuario) {
        //session = HibernateUtil.getSessionFactory().openSession();
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }catch(HibernateException e){
            session = HibernateUtil.getSessionFactory().openSession(); 
        }
        String sql = "select u from UsuarioPrototype u where email=:em and senha=:pass";
        Query query = session.createQuery(sql);
        query.setString("em", usuario.getEmail());
        query.setString("pass", usuario.getSenha());
        return (UsuarioPrototype) query.uniqueResult();
    }
    
    public BalconistaPrototype buscarBa(BalconistaPrototype balconista) {
        //session = HibernateUtil.getSessionFactory().openSession();
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }catch(HibernateException e){
            session = HibernateUtil.getSessionFactory().openSession(); 
        }
        String sql = "select u from BalconistaPrototype u where email=:em and senha=:pass";
        Query query = session.createQuery(sql);
        query.setString("em", balconista.getEmail());
        query.setString("pass", balconista.getSenha());
        return (BalconistaPrototype) query.uniqueResult();
    }
    
    public BibliotecarioPrototype buscarBi(BibliotecarioPrototype bi) {
        //session = HibernateUtil.getSessionFactory().openSession();
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }catch(HibernateException e){
            session = HibernateUtil.getSessionFactory().openSession(); 
        }
        String sql = "select u from BibliotecarioPrototype u where email=:em and senha=:pass";
        Query query = session.createQuery(sql);
        query.setString("em", bi.getEmail());
        query.setString("pass", bi.getSenha());
        return (BibliotecarioPrototype) query.uniqueResult();
    }
    
    public List<PessoaPrototype> getPessoaPorCodigo(int vlrFiltroCodigo){
        //session = HibernateUtil.getSessionFactory().openSession();
        //trans = session.beginTransaction();
        this.preparaSessao();
        Criteria cri = session.createCriteria(PessoaPrototype.class);
        cri.add(Restrictions.eq("codigo", vlrFiltroCodigo));
        this.lista = cri.list();
        trans.commit();
        return lista;
    }
    
    public List<PessoaPrototype> getPessoaPorCpf(String vlrFiltroCpf){
        //session = HibernateUtil.getSessionFactory().openSession();
        //trans = session.beginTransaction();
        this.preparaSessao();
        Criteria cri = session.createCriteria(PessoaPrototype.class);
        cri.add(Restrictions.eq("cpf", vlrFiltroCpf));
        this.lista = cri.list();
        trans.commit();
        return lista;
    }
    
    public List<PessoaPrototype> getPessoaPorRg(String vlrFiltroRg){
        //session = HibernateUtil.getSessionFactory().openSession();
        //trans = session.beginTransaction();
        this.preparaSessao();
        Criteria cri = session.createCriteria(PessoaPrototype.class);
        cri.add(Restrictions.eq("rg", vlrFiltroRg));
        this.lista = cri.list();
        session = HibernateUtil.getSessionFactory().openSession();
        trans = session.beginTransaction();
        return lista;
    }
    
    public List<PessoaPrototype> getPessoas(int tipoFiltro, String vlrFiltroTipo, String vlrFiltroNome) {
        //session = HibernateUtil.getSessionFactory().openSession();
        //trans = session.beginTransaction();
        this.preparaSessao();
        Criteria cri = session.createCriteria(PessoaPrototype.class);
        switch(tipoFiltro){
            case SEM_FILTRO:
                //sem filtro não faz nada
                break;
            case FILTRO_TIPO_NOME:
                switch(vlrFiltroTipo){
                    case "Balconista":
                        cri = session.createCriteria(BalconistaPrototype.class);
                        break;
                    case "Bibliotecario":
                        cri = session.createCriteria(BibliotecarioPrototype.class);
                        break;
                    case "Usuario":
                        cri = session.createCriteria(UsuarioPrototype.class);
                        break;
                }
                cri.add(Restrictions.ilike("nome", vlrFiltroNome, MatchMode.ANYWHERE));
                break;
            case FILTRO_TIPO:
                switch(vlrFiltroTipo){
                    case "Balconista":
                        cri = session.createCriteria(BalconistaPrototype.class);
                        break;
                    case "Bibliotecario":
                        cri = session.createCriteria(BibliotecarioPrototype.class);
                        break;
                    case "Usuario":
                        cri = session.createCriteria(UsuarioPrototype.class);
                        break;
                }
                break;
            case FILTRO_NOME:
                cri.add(Restrictions.ilike("nome", vlrFiltroNome, MatchMode.ANYWHERE));
                break;
            
        }
        this.lista = cri.list();
        return lista;
    }
    
    public void atualizarPessoa(PessoaPrototype pessoa) throws IOException{
        //session = HibernateUtil.getSessionFactory().openSession();
        //trans = session.beginTransaction();
        this.preparaSessao();
        session.update(pessoa);
        trans.commit();//confirmaçao
    }
}
