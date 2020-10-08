/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lunkes.sga.services;

import com.lunkes.sga.dados.UsuarioDao;
import com.lunkes.sga.domain.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author ismael
 */
@Stateless
public class UsuarioServiceImpl implements UsuarioService{

    @Inject
    UsuarioDao usuarioDao;
    
    @Override
    public List<Usuario> findAll() {
        return usuarioDao.findAll();
    }

    @Override
    public Usuario findById(Usuario usuario) {
        return usuarioDao.findById(usuario);
    }

    @Override
    public Usuario findByUsername(Usuario usuario) {
        return usuarioDao.findByUsername(usuario);
    }

    @Override
    public void save(Usuario usuario) {
        usuarioDao.save(usuario);
    }

    @Override
    public void update(Usuario usuario) {
        usuarioDao.update(usuario);
    }

    @Override
    public void delete(Usuario usuario) {
        usuarioDao.delete(usuario);
    }
    
}
