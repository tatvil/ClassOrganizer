package es.tatvil.classorganizer;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassManager {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Método para crear una nueva clase
    public void createClass(String profesorId, String nombre, String descripcion, String fecha, String hora) {
        Map<String, Object> nuevaClase = new HashMap<>();
        nuevaClase.put("nombre", nombre);
        nuevaClase.put("descripcion", descripcion);
        nuevaClase.put("fecha", fecha);
        nuevaClase.put("hora", hora);
        nuevaClase.put("profesorId", profesorId);
        nuevaClase.put("alumnos", new ArrayList<String>()); // Inicialmente sin alumnos asignados

        db.collection("clases").add(nuevaClase)
                .addOnSuccessListener(documentReference -> {
                    System.out.println("Clase creada con ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    System.err.println("Error al crear clase: " + e.getMessage());
                });
    }

    // Método para asignar alumnos a una clase
    public void assignStudentsToClass(String claseId, List<String> alumnoIds) {
        db.collection("clases").document(claseId)
                .update("alumnos", alumnoIds)
                .addOnSuccessListener(aVoid -> {
                    System.out.println("Alumnos asignados correctamente.");
                })
                .addOnFailureListener(e -> {
                    System.err.println("Error al asignar alumnos: " + e.getMessage());
                });
    }
}
