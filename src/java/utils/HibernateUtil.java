/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entidade.AcademicoPrototype;
import entidade.BalconistaPrototype;
import entidade.BibliotecarioPrototype;
import entidade.Emprestimo;
import entidade.ItemPrototype;
import entidade.LivroPrototype;
import entidade.PeriodicoPrototype;
import entidade.PessoaPrototype;
import entidade.Reserva;
import entidade.UsuarioPrototype;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
/**
 *
 * @author Igor
 */ 
public class HibernateUtil {
    private static SessionFactory sessionFactory;
     
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            // loads configuration and mappings
            Configuration configuration = new Configuration().configure();
            
            configuration.addAnnotatedClass(Reserva.class);
            configuration.addAnnotatedClass(Emprestimo.class);
            configuration.addAnnotatedClass(AcademicoPrototype.class);
            configuration.addAnnotatedClass(BalconistaPrototype.class);
            configuration.addAnnotatedClass(BibliotecarioPrototype.class);
            configuration.addAnnotatedClass(ItemPrototype.class);
            configuration.addAnnotatedClass(LivroPrototype.class);
            configuration.addAnnotatedClass(PeriodicoPrototype.class);
            configuration.addAnnotatedClass(PessoaPrototype.class);
            configuration.addAnnotatedClass(UsuarioPrototype.class);
            
            ServiceRegistry serviceRegistry
                = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
             
            // builds a session factory from the service registry
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);           
        }
         
        return sessionFactory;
    }
}
