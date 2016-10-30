/**
 *
 * @author Igor
 */
package DAO;

import static controle.ControleItem.FILTRO_NOME;
import static controle.ControleItem.FILTRO_TIPO;
import static controle.ControleItem.FILTRO_TIPO_NOME;
import static controle.ControleItem.SEM_FILTRO;
import entidade.Administrador;
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
        this.preparaSessao();
        session.save(pessoa);
        trans.commit();
        session.close();
    }
    
    public void addSemLogin(PessoaPrototype pessoa) throws IOException {
        this.preparaSessao();
        session.save(pessoa);
        trans.commit();
        session.close();
    }
    
    public List<PessoaPrototype> getPessoaPorCodigo(int vlrFiltroCodigo){
        this.preparaSessao();
        Criteria cri = session.createCriteria(PessoaPrototype.class);
        cri.add(Restrictions.eq("codigo", vlrFiltroCodigo));
        this.lista = cri.list();
        trans.commit();
        session.close();
        return lista;
    }
    
    public List<PessoaPrototype> getPessoaPorCpf(String vlrFiltroCpf){
        this.preparaSessao();
        Criteria cri = session.createCriteria(PessoaPrototype.class);
        cri.add(Restrictions.eq("cpf", vlrFiltroCpf));
        this.lista = cri.list();
        trans.commit();
        session.close();
        return lista;
    }
    
    public List<PessoaPrototype> getPessoaPorRg(String vlrFiltroRg){
        this.preparaSessao();
        Criteria cri = session.createCriteria(PessoaPrototype.class);
        cri.add(Restrictions.eq("rg", vlrFiltroRg));
        this.lista = cri.list();
        trans.commit();
        session.close();
        return lista;
    }
    
    public List<PessoaPrototype> getPessoas(int tipoFiltro, String vlrFiltroTipo, String vlrFiltroNome) {
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
        trans.commit();
        session.close();
        return lista;
    }
    
    public void atualizarPessoa(PessoaPrototype pessoa) throws IOException{
        this.preparaSessao();
        session.update(pessoa);
        trans.commit();//confirmaçao
        session.close();
    }
    
    public UsuarioPrototype getUsuarioPorCodigo(int codigo){
        UsuarioPrototype usuario = null;
        
        this.preparaSessao();
        Criteria cri = session.createCriteria(UsuarioPrototype.class);
        
        cri.add(Restrictions.eq("codigo", codigo));
        cri.setMaxResults(1);
        usuario = (UsuarioPrototype) cri.uniqueResult();
        
        trans.commit();
        session.close();
        return usuario;
    }
    
    public String getPerfilAcesso(String email, String senha){
        String perfil;
        
        this.preparaSessao();
        
        String sql = "SELECT tipoPessoa FROM PessoaPrototype WHERE email =:email AND senha =:senha";
        Query consulta = session.createQuery(sql);
        consulta.setString("email", email);
        consulta.setString("senha", senha);
        perfil = (String) consulta.uniqueResult();
        
        trans.commit();
        session.close();
       
        return perfil;        
    }
    
    public BalconistaPrototype buscarBalconista(String email){
        this.preparaSessao();
        
        BalconistaPrototype balconista = null;
        
        Criteria cri = session.createCriteria(BalconistaPrototype.class);
        
        cri.add(Restrictions.eq("email", email));
        cri.setMaxResults(1);
        
        balconista = (BalconistaPrototype) cri.uniqueResult();
        
        trans.commit();
        session.close();
        
        return balconista;
    }
    
    public BibliotecarioPrototype buscarBibliotecario(String email){
        this.preparaSessao();
        
        BibliotecarioPrototype bibliotecario = null;
        
        Criteria cri = session.createCriteria(BibliotecarioPrototype.class);
        
        cri.add(Restrictions.eq("email", email));
        cri.setMaxResults(1);
        
        bibliotecario = (BibliotecarioPrototype) cri.uniqueResult();
        
        trans.commit();
        session.close();
        
        return bibliotecario;
    }
    
    public UsuarioPrototype buscarUsuario(String email){
        this.preparaSessao();
        
        UsuarioPrototype usuario = null;
        
        Criteria cri = session.createCriteria(UsuarioPrototype.class);
        
        cri.add(Restrictions.eq("email", email));
        cri.setMaxResults(1);
        
        usuario = (UsuarioPrototype) cri.uniqueResult();
        
        trans.commit();
        session.close();
        
        return usuario;
    }
    
    public Administrador buscarAdm(String email){
        this.preparaSessao();
        
        Administrador administrador = null;
        
        Criteria cri = session.createCriteria(Administrador.class);
        
        cri.add(Restrictions.eq("email", email));
        cri.setMaxResults(1);
        
        administrador = (Administrador) cri.uniqueResult();
        
        trans.commit();
        session.close();
        
        return administrador;
    }
}
