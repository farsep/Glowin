package com.glowin.controller;

import com.glowin.models.Favorito;
import com.glowin.models.Input.FavoritoInput;
import com.glowin.models.Servicio;
import com.glowin.models.Usuario;
import com.glowin.models.output.FavoritoOutput;
import com.glowin.repository.IFavoritoRepository;
import com.glowin.repository.IServicioRepository;
import com.glowin.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/favoritos")
public class ControllerFavoritos {

    @Autowired
    private IFavoritoRepository favoritoRepository;

    @Autowired
    private IServicioRepository servicioRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    // ✅ Marcar un servicio como favorito
    @PostMapping
    public ResponseEntity<?> agregarFavorito(@RequestBody FavoritoInput input) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(input.idUsuario());
        Optional<Servicio> servicioOpt = servicioRepository.findById(input.idServicio());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Usuario no encontrado");
        }
        if (servicioOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Servicio no encontrado");
        }

        Usuario usuario = usuarioOpt.get();
        Servicio servicio = servicioOpt.get();

        if (favoritoRepository.existsByUsuarioAndServicio(usuario, servicio)) {
            return ResponseEntity.badRequest().body("Error: El servicio ya está en favoritos");
        }

        Favorito favorito = favoritoRepository.save(new Favorito(usuario, servicio));

        return ResponseEntity.ok(new FavoritoOutput(
                favorito.getId(),
                favorito.getUsuario().getId(),
                favorito.getServicio().getId(),
                favorito.getFechaAgregado()
        ));
    }

    // ✅ Listar favoritos de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> obtenerFavoritos(@PathVariable Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            return ResponseEntity.badRequest().body("Error: Usuario no encontrado");
        }

        List<FavoritoOutput> favoritos = favoritoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(f -> new FavoritoOutput(f.getId(), f.getUsuario().getId(), f.getServicio().getId(), f.getFechaAgregado()))
                .toList();

        return ResponseEntity.ok(favoritos);
    }

    // ✅ Eliminar un favorito
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarFavorito(@PathVariable Long id) {
        Optional<Favorito> favoritoOpt = favoritoRepository.findById(id);

        if (favoritoOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Favorito no encontrado");
        }

        favoritoRepository.delete(favoritoOpt.get());
        return ResponseEntity.ok("Favorito eliminado correctamente");
    }

}
