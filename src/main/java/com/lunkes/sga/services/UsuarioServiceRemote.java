/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lunkes.sga.services;

import com.lunkes.sga.domain.Usuario;
import java.util.List;

/**
 *
 * @author ismael
 */
public interface UsuarioServiceRemote {
    
    public List<Usuario> findAll();

    public Usuario findById(Usuario usuario);

    public Usuario findByUsername(Usuario usuario); 

    public void save(Usuario usuario);

    public void update(Usuario usuario);

    public void delete(Usuario usuario);   
    
}
