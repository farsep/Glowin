package com.glowin.service;

import com.glowin.models.CategoriaServicio;
import com.glowin.models.ImagenServicio;
import com.glowin.models.Servicio;
import com.glowin.models.output.CategoriaServicioOutput;
import com.glowin.models.output.ServicioOutput;
import com.glowin.repository.IServicioRepository;
import com.glowin.repository.ICategoriaServicioRepository;
import com.glowin.repository.IImagenServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class CompartirRedesSocialesService {

    @Autowired
    private ICategoriaServicioRepository categoriaServicioRepository;

    @Autowired
    private IServicioRepository servicioRepository;

    @Autowired
    private IImagenServicioRepository imagenServicioRepository;

    public CategoriaServicioOutput generarEnlacesCompartir(Long id) {
        CategoriaServicio categoriaServicio = categoriaServicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CategoriaServicio not found"));

        String enlace = "https://localhost:5432/categorias-servicios/" + id;
        String enlaceFacebook = "https://www.facebook.com/sharer/sharer.php?u=" + URLEncoder.encode(enlace, StandardCharsets.UTF_8);
        String enlaceWhatsApp = "https://api.whatsapp.com/send?text=" + URLEncoder.encode(enlace, StandardCharsets.UTF_8);

        return new CategoriaServicioOutput(categoriaServicio, enlaceFacebook, enlaceWhatsApp);
    }

    public ServicioOutput generarEnlacesCompartirServicio(Long id) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio not found"));

        List<ImagenServicio> imagenes = imagenServicioRepository.findByServicioId(id);
        String urlImagen = imagenes.isEmpty() ? "" : imagenes.get(0).getUrlImagen();

        String enlace = "https://localhost:5432/servicios/" + id;
        String enlaceFacebook = "https://www.facebook.com/sharer/sharer.php?u=" + URLEncoder.encode(enlace, StandardCharsets.UTF_8) + "&picture=" + URLEncoder.encode(urlImagen, StandardCharsets.UTF_8);
        String enlaceWhatsApp = "https://api.whatsapp.com/send?text=" + URLEncoder.encode(enlace, StandardCharsets.UTF_8) + "%20" + URLEncoder.encode(urlImagen, StandardCharsets.UTF_8);

        return new ServicioOutput(servicio, enlaceFacebook, enlaceWhatsApp);
    }
}