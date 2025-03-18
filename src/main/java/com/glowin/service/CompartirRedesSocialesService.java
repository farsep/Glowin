package com.glowin.service;

import org.springframework.stereotype.Service;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class CompartirRedesSocialesService {

    public String generarEnlaceCompartirFacebook(String enlace) {
        return "https://www.facebook.com/sharer/sharer.php?u=" + URLEncoder.encode(enlace, StandardCharsets.UTF_8);
    }

    public String generarEnlaceCompartirWhatsApp(String enlace) {
        return "https://api.whatsapp.com/send?text=" + URLEncoder.encode(enlace, StandardCharsets.UTF_8);
    }
}