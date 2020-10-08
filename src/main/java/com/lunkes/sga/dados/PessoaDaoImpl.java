/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lunkes.sga.dados;

import com.lunkes.sga.domain.Pessoa;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ismael
 */
@Stateless
public class PessoaDaoImpl implements PessoaDao{
    
    @PersistenceContext(unitName = "SgaPU")
    EntityManager em;

    @Override
    public List<Pessoa> findAll() {
      return em.createNamedQuery("Pessoa.findAll").getResultList();        
    }

    @Override
    public Pessoa findById(Pessoa pessoa) {
        return em.find(Pessoa.class, pessoa.getIdPessoa());
    }

    @Override
    public Pessoa findByEmail(Pessoa pessoa) {
        Query query = em.createQuery("FROM pessoa p WHERE p.email =: email");
        query.setParameter("email", pessoa.getEmail());
        return (Pessoa) query.getSingleResult();
    }

    @Override
    public void save(Pessoa pessoa) {
        em.persist(pessoa);
    }

    @Override
    public void update(Pessoa pessoa) {
        em.merge(pessoa);
    }

    @Override
    public void delete(Pessoa pessoa) {        
        em.remove(em.merge(pessoa));
    }
    
}
