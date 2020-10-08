/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lunkes.sga.cliente.criteria;

import com.lunkes.sga.domain.Pessoa;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author ismael
 */
public class TesteApiCriteria {
    
    static Logger log = LogManager.getRootLogger();
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SgaPU");
        EntityManager em = emf.createEntityManager();
        
        CriteriaBuilder cb = null;
        CriteriaQuery<Pessoa> criteriaQuery = null;        
        Root<Pessoa> fromPessoa = null;
        TypedQuery<Pessoa> query = null;
        Pessoa pessoa = null;
        List<Pessoa> pessoas = null;
        
        //Query utilizando API de criteria
        //1. Consulta de todas as pessoas 
        
        //Passo 1. O Objeto EntityManager cria instancia CriteriaBulider
        cb = em.getCriteriaBuilder();
        
        //Passo 2. Se cria um objeto CriteriaQuery
        criteriaQuery = cb.createQuery(Pessoa.class);
        
        //Passo 3. Criamos objeto raiz da query
        fromPessoa = criteriaQuery.from(Pessoa.class);
        
        //Passo 4. Selecionamos o necessario de from
        criteriaQuery.select(fromPessoa);
        
        //Passo 5. Criamos query typeSafe
        query = em.createQuery(criteriaQuery);
        
        //Passo 6. Executamos a consulta
        pessoas = query.getResultList();
        
        imprimirPessoas(pessoas);
        
        //2-a. Consulta de uma pessoa com id=1
        //jpql = "select p from Pessoa p where p.idPessoa =1"
        log.debug("\n2-a. Consulta de uma pessoa com id = 1");
        cb = em.getCriteriaBuilder();
        criteriaQuery = cb.createQuery(Pessoa.class);
        fromPessoa = criteriaQuery.from(Pessoa.class);
        criteriaQuery.select(fromPessoa).where(cb.equal(fromPessoa.get("idPessoa"), 1));
        pessoa = em.createQuery(criteriaQuery).getSingleResult();
        log.debug(pessoa);
        
         //2-b. Consulta de uma pessoa com id=1        
        log.debug("\n2-b. Consulta de uma pessoa com id = 1");
        cb = em.getCriteriaBuilder();
        criteriaQuery = cb.createQuery(Pessoa.class);
        fromPessoa = criteriaQuery.from(Pessoa.class);
        criteriaQuery.select(fromPessoa);
        // A Classe Predicate permite agregar varios criterios dinamicamente
        List<Predicate> criterios =  new ArrayList<Predicate>();        
        //Verificamos se temos criterios para agregar
        Integer idPessoaParam = 1;
        ParameterExpression<Integer> parameter = cb.parameter(Integer.class, "idPessoa");
        criterios.add(cb.equal(fromPessoa.get("idPessoa"), parameter));
        //Se agregam os criterios
        if(criterios.isEmpty()){
            throw new RuntimeException("Sem criterios");
        }
        else if (criterios.size()==1){
            criteriaQuery.where(criterios.get(0));
        }
        else {
            criteriaQuery.where(cb.and(criterios.toArray(new Predicate[0])));
        }
        query = em.createQuery(criteriaQuery);
        query.setParameter("idPessoa", idPessoaParam);
        // Se executa a query
        pessoa = query.getSingleResult();
        log.debug(pessoa);
        
    }

    private static void imprimirPessoas(List<Pessoa> pessoas) {
        for (Pessoa pessoa : pessoas) {
            log.debug(pessoa);
        }
    }
    
}
