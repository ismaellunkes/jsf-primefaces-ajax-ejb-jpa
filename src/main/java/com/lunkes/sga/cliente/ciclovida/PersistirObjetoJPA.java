/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lunkes.sga.cliente.ciclovida;

import com.lunkes.sga.domain.Pessoa;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.apache.logging.log4j.*;

/**
 *
 * @author ismael
 */
public class PersistirObjetoJPA {
    static Logger log = LogManager.getRootLogger();
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SgaPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        //Inicia a transacao
        
        //Passo 1. Cria novo objeto
        //Objeto em estado transitivo
        Pessoa pessoa1 = new Pessoa("Teddy", "Lunkes", "teddy@gmail.com", "3435839483");
        
        //Passo 2. Iniciar a transacao
        tx.begin();
        
        //Passo 3 . Executa SQL
        em.persist(pessoa1);
        
        log.debug("Objeto persistido - porem sem commit "+pessoa1);
        
        //Passo 4. commmit/rollback
        tx.commit();
        
        //Objeto em estado detached
        log.debug("Objeto persistido - estado detached: "+pessoa1);
        
        //Encerramos o Entity Manager
        em.close();
        
    }
    
}
