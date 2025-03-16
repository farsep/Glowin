package com.glowin.repository;


import com.glowin.models.Favorito;
import com.glowin.models.Servicio;
import com.glowin.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IFavoritoRepository extends JpaRepository<Favorito, Long> {
    boolean existsByUsuarioAndServicio(Usuario usuario, Servicio servicio);
    List<Favorito> findByUsuarioId(Long usuarioId);
}
