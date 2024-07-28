package es.tatvil.classorganizer;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class FirebaseHelper {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void addProvincias() {
        String[] provincias = {
                "Álava", "Albacete", "Alicante", "Almería", "Ávila", "Badajoz", "Baleares",
                "Barcelona", "Burgos", "Cáceres", "Cádiz", "Castellón", "Ciudad Real",
                "Córdoba", "A Coruña", "Cuenca", "Gerona", "Granada", "Guadalajara",
                "Guipúzcoa", "Huelva", "Huesca", "Jaén", "León", "Lérida", "La Rioja",
                "Lugo", "Madrid", "Málaga", "Murcia", "Navarra", "Orense", "Asturias",
                "Palencia", "Las Palmas", "Pontevedra", "Salamanca", "Santa Cruz de Tenerife",
                "Cantabria", "Segovia", "Sevilla", "Soria", "Tarragona", "Teruel",
                "Toledo", "Valencia", "Valladolid", "Vizcaya", "Zamora", "Zaragoza"
        };

        for (int i = 0; i < provincias.length; i++) {
            String id = String.format("%02d", i + 1); // Generate id 01, 02, ..., 50
            Map<String, Object> provincia = new HashMap<>();
            provincia.put("id", id);
            provincia.put("nombre", provincias[i]);

            db.collection("provincias").document(id)
                    .set(provincia)
                    .addOnSuccessListener(aVoid -> System.out.println("Provincia added: " + id))
                    .addOnFailureListener(e -> System.err.println("Error adding provincia: " + e.getMessage()));
        }
    }

    public void addLocalidades() {
        Map<String, Map<String, Object>> localidades = new HashMap<>();

        localidades.put("28001", Map.of("cod_postal", "28001", "nombre", "Madrid", "provincia", "28"));
        localidades.put("28230", Map.of("cod_postal", "28230", "nombre", "Las Rozas de Madrid", "provincia", "28"));
        localidades.put("28231", Map.of("cod_postal", "28231", "nombre", "Las Rozas de Madrid", "provincia", "28"));
        localidades.put("28232", Map.of("cod_postal", "28232", "nombre", "Las Rozas de Madrid", "provincia", "28"));
        localidades.put("28290", Map.of("cod_postal", "28290", "nombre", "Las Rozas de Madrid", "provincia", "28"));
        localidades.put("43895", Map.of("cod_postal", "43895", "nombre", "L'Ampolla", "provincia", "43"));
        localidades.put("28220", Map.of("cod_postal", "28220", "nombre", "Majadahonda", "provincia", "28"));
        localidades.put("28250", Map.of("cod_postal", "28250", "nombre", "Torrelodones", "provincia", "28"));
        localidades.put("08001", Map.of("cod_postal", "08001", "nombre", "Barcelona", "provincia", "08"));
        localidades.put("46001", Map.of("cod_postal", "46001", "nombre", "Valencia", "provincia", "46"));
        localidades.put("10450", Map.of("cod_postal", "10450", "nombre", "Jarandilla de la Vera", "provincia", "10"));
        localidades.put("10460", Map.of("cod_postal", "10460", "nombre", "Valverde de la Vera", "provincia", "10"));
        localidades.put("10460", Map.of("cod_postal", "10460", "nombre", "Losar de la Vera", "provincia", "10"));
        localidades.put("10410", Map.of("cod_postal", "10410", "nombre", "Villanueva de la Vera", "provincia", "10"));
        // Agrega más localidades según sea necesario

        // Iterar sobre el mapa y agregar cada localidad a la colección en Firestore
        for (Map.Entry<String, Map<String, Object>> entry : localidades.entrySet()) {
            String codPostal = entry.getKey();
            Map<String, Object> localidad = entry.getValue();

            db.collection("localidades").document(codPostal)
                    .set(localidad)
                    .addOnSuccessListener(aVoid -> System.out.println("Localidad added: " + codPostal))
                    .addOnFailureListener(e -> System.err.println("Error adding localidad: " + e.getMessage()));
        }
    }
}