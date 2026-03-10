package com.tt1.trabajo.servicios;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import interfaces.InterfazContactoSim;
import modelo.DatosSolicitud;
import modelo.DatosSimulation;
import modelo.Entidad;
import org.springframework.web.client.RestTemplate;

@Service
public class ServicioSolicitudes implements InterfazContactoSim {
    private DatosSolicitud solicitudProvisional;

    @Override
    public int solicitarSimulation(DatosSolicitud sol) {
        this.solicitudProvisional = sol;
        Random random = new Random();
        return random.nextInt(10000);
    }

    @Override
    public List<Entidad> getEntities() {
        List<Entidad> lista = new ArrayList<>();
        lista.add(new Entidad(1, "Entidad Alfa", "A"));
        lista.add(new Entidad(2, "Entidad Beta", "B"));
        lista.add(new Entidad(3, "Entidad Gamma", "G"));

        return lista;
    }

    @Override
    public DatosSimulation descargarDatos(int ticket) {
        // 1. Herramienta para hacer peticiones web
        RestTemplate restTemplate = new RestTemplate();

        // 2. ¡OJO AQUÍ! Cambia "DIRECCION_DE_LA_MAQUINA" por la URL real que os haya dado el profesor
        // El PDF dice que uses un usuario constante inventado por ti (ej: "Marcos")
        String urlApi = "http://DIRECCION_DE_LA_MAQUINA/api/resultados?ticket=" + ticket + "&usuario=Marcos";

        try {
            // 3. Llamamos a la máquina y guardamos el texto raro que nos devuelve (0,7,5, red...)
            String respuesta = restTemplate.getForObject(urlApi, String.class);

            // 4. Lo metemos en nuestro objeto de datos
            DatosSimulation datos = new DatosSimulation();
            datos.setRawData(respuesta); // Asegúrate de que tu clase DatosSimulation tenga este setter
            return datos;

        } catch (Exception e) {
            System.out.println("Error al conectar con la API: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean isValidEntityId() {
        return true;
    }
}
