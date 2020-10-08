/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lunkes.sga.dados;

import com.lunkes.sga.domain.Usuario;
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
public class UsuarioDaoImpl implements UsuarioDao{

    @PersistenceContext(name = "SgaPU")
    EntityManager em;
    
    @Override
    public List<Usuario> findAll() {
         return em.createNamedQuery("Usuario.findAll").getResultList();
    }

    @Override
    public Usuario findById(Usuario usuario) {
        return em.find(Usuario.class, usuario.getIdUsuario());
    }

    @Override
    public Usuario findByUsername(Usuario usuario) {
        Query query = em.createQuery("FROM usuario u WHERE u.username =: username");
        query.setParameter("username", usuario.getUsername());
        return (Usuario) query.getSingleResult();
    }

    @Override
    public void save(Usuario usuario) {
        em.persist(usuario);
    }

    @Override
    public void update(Usuario usuario) {
        em.merge(usuario);
    }

    @Override
    public void delete(Usuario usuario) {
        em.remove(em.merge(usuario));
    }
    
}
