UPDATE servicios
SET duracion_minutos = ROUND(duracion_minutos / 60.0) * 60;