package com.glowin.service;

import com.glowin.models.CategoriaServicio;
import com.glowin.models.ImagenServicio;
import com.glowin.models.Servicio;
import com.glowin.models.Usuario;
import com.glowin.models.output.CategoriaServicioOutput;
import com.glowin.models.output.ServicioOutput;
import com.glowin.repository.IServicioRepository;
import com.glowin.repository.ICategoriaServicioRepository;
import com.glowin.repository.IImagenServicioRepository;
import com.glowin.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class CompartirRedesSocialesService {

    @Autowired
    private ICategoriaServicioRepository categoriaServicioRepository;

    @Autowired
    private IServicioRepository servicioRepository;

    @Autowired
    private IImagenServicioRepository imagenServicioRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    // Expresión regular para validar números de teléfono
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[1-9]\\d{1,14}$");


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

        // Obtener la primera imagen relacionada al servicio
        List<ImagenServicio> imagenes = imagenServicioRepository.findByServicioId(id);
        String urlImagen = imagenes.isEmpty() ? "" : imagenes.get(0).getUrlImagen();

        // Generar enlaces de compartir incluyendo la URL de la imagen
        String enlace = "https://localhost:5432/servicios/" + id;
        String enlaceFacebook = "https://www.facebook.com/sharer/sharer.php?u=" + URLEncoder.encode(enlace, StandardCharsets.UTF_8) + "&picture=" + URLEncoder.encode(urlImagen, StandardCharsets.UTF_8);
        String enlaceWhatsApp = "https://api.whatsapp.com/send?text=" + URLEncoder.encode(enlace, StandardCharsets.UTF_8) + "%20" + URLEncoder.encode(urlImagen, StandardCharsets.UTF_8);

        return new ServicioOutput(servicio, enlaceFacebook, enlaceWhatsApp);
    }

    // Validar que el número de celular tenga el .*,-605.,4y1hgc xzformato correcto
    private boolean ValidarNumeroCelular(String phoneNumber) {
        return PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    // Obtener el número de WhatsApp del super administrador
    public String obtenerNumeroWhatsAppSuperAdmin() {
        Optional<Usuario> superAdmin = usuarioRepository.findById(1L);
        String numeroWhatsApp = superAdmin.map(Usuario::getCelular).orElseThrow(() -> new RuntimeException("Super Admin no encontrado"));
        if (!ValidarNumeroCelular(numeroWhatsApp)) {
            throw new RuntimeException("Numero de celular inválido");
        }
        return numeroWhatsApp;
    }

    // Generar enlace de WhatsApp para contactar al super administrador (atención al cliente)
    public String generarEnlaceWhatsApp() {
        String numeroWhatsApp = obtenerNumeroWhatsAppSuperAdmin();
        String mensajePredeterminado = "Hola, equipo de atención al cliente. Necesito asistencia con [breve descripción del problema o servicio]. Agradezco su pronta respuesta.";
        String mensajeSanitizado = URLEncoder.encode(mensajePredeterminado, StandardCharsets.UTF_8);
        return "https://api.whatsapp.com/send?phone=" + numeroWhatsApp + "&text=" + mensajeSanitizado;
    }

}