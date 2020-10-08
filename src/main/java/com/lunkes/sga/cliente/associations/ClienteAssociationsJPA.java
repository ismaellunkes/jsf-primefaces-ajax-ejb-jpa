/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lunkes.sga.cliente.associations;

import com.lunkes.sga.domain.Pessoa;
import com.lunkes.sga.domain.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author ismael
 */
public class ClienteAssociationsJPA {
    
     static Logger log = LogManager.getRootLogger();
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SgaPU");
        EntityManager em = emf.createEntityManager();
        
        List<Pessoa> pessoas = em.createNamedQuery("Pessoa.findAll").getResultList();
        
        em.close();
        
        imprimirPessoas(pessoas);
        
    }
    
    private static void imprimirPessoas(List<Pessoa> pessoas){
        
        //Objetos em estado detached
        for (Pessoa pessoa : pessoas) {
            log.debug("Pessoa: "+pessoa);
            
            ///Recuperamos os usuarios de cada pessoa
            for (Usuario usuario : pessoa.getUsuarioList()) {
                log.debug("Usuario: "+usuario);
            }
             
        }
        
    }
    
}
