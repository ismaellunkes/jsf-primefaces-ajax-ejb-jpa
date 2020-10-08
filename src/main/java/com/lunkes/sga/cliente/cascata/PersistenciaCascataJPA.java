/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lunkes.sga.cliente.cascata;

import com.lunkes.sga.domain.Pessoa;
import com.lunkes.sga.domain.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author ismael
 */
public class PersistenciaCascataJPA {

    static Logger log = LogManager.getRootLogger();

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SgaPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        //Inicia a transacao
        //Passo 1. Cria novo objeto
        //Objeto em estado transitivo
        Pessoa pessoa1 = new Pessoa("Ivam", "Cordeiro", "ivam@gmail.com", "40473");
        
        ///Cria objeto que tem dependencia com objeto pessoa
        Usuario usuario1 = new Usuario("ivamc", "2123434", pessoa1);

        //Passo 2. Iniciar a transacao
        tx.begin();

        //Passo 3 . Executa SQL
        //Persistimos somente o usuario
        // Nao ha necessidade de persistir objeto pessoa, sera feito por cascateamento
        em.persist(usuario1);

        //Passo 4. commmit/rollback
        tx.commit();

        //Objeto em estado detached
        log.debug("Objeto persistido Usuario: " + usuario1);
        log.debug("Objeto persistido Pessoa: " + pessoa1);

        //Encerramos o Entity Manager
        em.close();
    }
}
