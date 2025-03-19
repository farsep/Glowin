package com.glowin.service;

import com.glowin.models.CategoriaServicio;
import com.glowin.models.output.CategoriaServicioOutput;
import com.glowin.repository.ICategoriaServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class CompartirRedesSocialesService {

    @Autowired
    private ICategoriaServicioRepository categoriaServicioRepository;

    public CategoriaServicioOutput generarEnlacesCompartir(Long id) {
        CategoriaServicio categoriaServicio = categoriaServicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CategoriaServicio not found"));

        String enlace = "https://localhost:5432/categorias-servicios/" + id;
        String enlaceFacebook = "https://www.facebook.com/sharer/sharer.php?u=" + URLEncoder.encode(enlace, StandardCharsets.UTF_8);
        String enlaceWhatsApp = "https://api.whatsapp.com/send?text=" + URLEncoder.encode(enlace, StandardCharsets.UTF_8);

        return new CategoriaServicioOutput(categoriaServicio, enlaceFacebook, enlaceWhatsApp);
    }
}