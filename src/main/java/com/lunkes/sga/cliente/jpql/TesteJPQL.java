/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lunkes.sga.cliente.jpql;

import com.lunkes.sga.domain.Pessoa;
import com.lunkes.sga.domain.Usuario;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author ismael
 */
public class TesteJPQL {
    
    static Logger log = LogManager.getRootLogger();
    
    public static void main(String[] args) {
        String jpql = null;
        Query query = null;
        
        List<Pessoa> pessoas = null;
        Pessoa pessoa = null;
        Iterator iter = null;
        Object[] tupla = null;
        List<String> nomes = null;
        List<Usuario>usuarios = null;
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SgaPU");
        EntityManager em = emf.createEntityManager();
        
        //1. Consulta a todos os objetos do tipo pessoa
        log.debug("\n1.Consulta de todas as pessoas");
        jpql = "select p from Pessoa p";
        pessoas = em.createQuery(jpql).getResultList();
        imprimirPessoas(pessoas);
        
        //2. Consulta a todos os objetos do tipo pessoa
        log.debug("\n2.Consulta a pessoa de id = 1");
        jpql = "select p from Pessoa p where p.idPessoa = 1";
        pessoa = (Pessoa) em.createQuery(jpql).getSingleResult();
        log.debug(pessoa);
        
        //3. Consulta a pessoa por nome
        log.debug("\n3.Consulta a pessoa pelo nome que comece com i");
        jpql = "select p from Pessoa p where p.nome like 'i%'";
        pessoas = em.createQuery(jpql).getResultList();
        imprimirPessoas(pessoas);
        
        //4. Consulta de dados individual, se cria um arranjo (tupla) de tipo object de 3 colunas
        log.debug("\n4.Consulta de dados individuais");
        jpql = "select p.nome, p.email from Pessoa p ";
        iter = em.createQuery(jpql).getResultList().iterator();
        while (iter.hasNext()) {            
            tupla = (Object[]) iter.next();
            String nome = (String) tupla[0];
            String email = (String) tupla[1];
            log.debug("nome: "+nome +", email: "+email);            
        }
        
        //5. Obter o objeto Pessoa e o Id, se cria um arranjo do tipo objeto de duas colunas
        log.debug("\n5.Consulta o objeto Pessoa e o Id");
        jpql = "select p, p.idPessoa from Pessoa p ";
        iter = em.createQuery(jpql).getResultList().iterator();
        while (iter.hasNext()) {            
            tupla = (Object[]) iter.next();
            pessoa = (Pessoa) tupla[0];
            int idPessoa = (int) tupla[1];
            log.debug("Pessoa: "+pessoa +", Id: "+idPessoa);            
        }
        
         //6. Consulta de todas as pessoas
        log.debug("\n6.Consulta de todas as pessoas");
        jpql = "select new com.lunkes.sga.domain.Pessoa(p.idPessoa) from Pessoa p ";
        pessoas = em.createQuery(jpql).getResultList();
        imprimirPessoas(pessoas);
        
        //7. Retorna o valor minimo e maximo de idPessoa (scaler result)
        log.debug("\n7.Consulta o valor minimo e maximo de idPessoa");
        jpql = "select min(p.idPessoa) as MinId, max(p.idPessoa) as MaxId, count(p.idPessoa) as Contador from Pessoa p ";
        iter = em.createQuery(jpql).getResultList().iterator();
        while (iter.hasNext()) {            
            tupla = (Object[]) iter.next();
            Integer idMin = (Integer) tupla[0];
            Integer idMax = (Integer) tupla[1];
            Long idCount = (Long) tupla[2];
            log.debug("IdMin: "+idMin +", IdMax: "+idMax+", IdCount: "+idCount);            
        }
        
        //8. Contar os nomes das pessoas que sao diferentes
        log.debug("\n8. Contar os nomes das pessoas que sao diferentes");
        jpql = "select count(distinct p.nome) from Pessoa p ";   
        Long contador = (Long) em.createQuery(jpql).getSingleResult();
        log.debug("O numero de pessoas com nomes diferentes sao: "+contador+" pessoas.");
        
         //9. Concatenar e converter para maiusculas o nome e sobrenome
        log.debug("\n9. Concatenar e converter para maiusculas o nome e sobrenome");
        jpql = "select CONCAT(p.nome, ' ',p.sobrenome) as nome from Pessoa p ";   
        nomes = em.createQuery(jpql).getResultList();
        for (String nomeCompleto : nomes) {
           log.debug(nomeCompleto);
        }   
        
        //10. Obtem objeto pessoa igual ao parametro proporcionado
        log.debug("\n10. Obtem objeto pessoa igual ao parametro proporcionado");
        int idPessoa = 1;
        jpql = "select p from Pessoa p where p.idPessoa = :id ";   
        query = em.createQuery(jpql);
        query.setParameter("id", idPessoa);
        pessoa = (Pessoa) query.getSingleResult();
        log.debug("A pessoa selecionada e: "+pessoa);
        
        //11. Obtem as pessoas que contem uma letra a no nome, sem importar se e maiscula ou minuscula
        log.debug("\n11.Obtem as pessoas que contem uma letra a no nome, sem importar se e maiscula ou minuscula");
        jpql = "select p from Pessoa p where upper(p.nome) like upper(:parametro)";
        String parametroString = "%a%";
        query = em.createQuery(jpql);
        query.setParameter("parametro", parametroString);        
        pessoas = query.getResultList();
        imprimirPessoas(pessoas);
        
        //12. Uso de between
        log.debug("\n12.Uso de between");
        jpql = "select p from Pessoa p where p.idPessoa between :param1 and :param2 ";
        Integer param1 = 1;
        Integer param2 = 3;
        query = em.createQuery(jpql);
        query.setParameter("param1", param1);        
        query.setParameter("param2", param2);        
        pessoas = query.getResultList();
        imprimirPessoas(pessoas);
        
         //13. Uso de ordenamento
        log.debug("\n13.Uso de ordenamento");
        jpql = "select p from Pessoa p where p.idPessoa > :param order by p.nome desc, p.idPessoa desc ";
        Integer param = 2;        
        query = em.createQuery(jpql);        
        query.setParameter("param", param);        
        pessoas = query.getResultList();
        imprimirPessoas(pessoas);
        
        //14. Uso de subquery (depende se o banco de dados suporta este recurso)
        log.debug("\n14.Uso de subquery");
        jpql = "select p from Pessoa p where p.idPessoa in (select min(p1.idPessoa) from Pessoa p1) ";        
        pessoas = em.createQuery(jpql).getResultList();
        imprimirPessoas(pessoas);
        
        //15. Uso de join com Lazy Loading
        log.debug("\n14.Uso de join com Lazy Loading");
        jpql = "select u from Usuario u join u.pessoa p";        
        usuarios = em.createQuery(jpql).getResultList();
        imprimirUsuarios(usuarios);
        
        //16. Uso de left join com Eager Loading
        log.debug("\n16.Uso de left join com Eager Loading");
        jpql = "select u from Usuario u left join fetch u.pessoa";        
        usuarios = em.createQuery(jpql).getResultList();
        imprimirUsuarios(usuarios);
        
        
    }

    private static void imprimirPessoas(List<Pessoa> pessoas) {
        for (Pessoa pessoa : pessoas) {
            log.debug(pessoa);
        }
    }

    private static void imprimirUsuarios(List<Usuario> usuarios) {
        for (Usuario usuario : usuarios) {
            log.debug(usuario);
        }
    }
    
}
