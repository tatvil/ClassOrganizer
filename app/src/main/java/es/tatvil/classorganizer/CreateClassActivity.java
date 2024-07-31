package es.tatvil.classorganizer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateClassActivity extends AppCompatActivity {

    private EditText classNameEditText;
    private EditText classDescriptionEditText;
    private Spinner dayOfWeekSpinner;
    private EditText startTimeEditText;
    private EditText endTimeEditText;
    private CheckBox recurringCheckBox;
    private Button createClassButton;
    private RecyclerView studentsRecyclerView;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private List<Persona> studentList;
    private StudentAdapter studentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);

        Log.d("CreateClassActivity", "onCreate called");

        classNameEditText = findViewById(R.id.classNameEditText);
        classDescriptionEditText = findViewById(R.id.classDescriptionEditText);
        dayOfWeekSpinner = findViewById(R.id.dayOfWeekSpinner);
        startTimeEditText = findViewById(R.id.startTimeEditText);
        endTimeEditText = findViewById(R.id.endTimeEditText);
        recurringCheckBox = findViewById(R.id.recurringCheckBox);
        createClassButton = findViewById(R.id.createClassButton);
        studentsRecyclerView = findViewById(R.id.studentsRecyclerView);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        studentList = new ArrayList<>();
        studentAdapter = new StudentAdapter(studentList);

        studentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentsRecyclerView.setAdapter(studentAdapter);

        loadStudents();

        createClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearClase();
            }
        });
    }

    private void loadStudents() {
        db.collection("alumnos")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Persona student = document.toObject(Persona.class);
                            studentList.add(student);
                        }
                        studentAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateClassActivity.this, "Error al cargar alumnos", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void crearClase() {
        String className = classNameEditText.getText().toString();
        String classDescription = classDescriptionEditText.getText().toString();
        String dayOfWeek = dayOfWeekSpinner.getSelectedItem().toString();
        String startTime = startTimeEditText.getText().toString();
        String endTime = endTimeEditText.getText().toString();
        boolean isRecurring = recurringCheckBox.isChecked();
        String userUID = auth.getCurrentUser().getUid();

        List<String> selectedStudentIds = studentAdapter.getSelectedStudentIds();

        Map<String, Object> clase = new HashMap<>();
        clase.put("nombre", className);
        clase.put("descripcion", classDescription);
        clase.put("profesorId", userUID);
        clase.put("diaSemana", dayOfWeek);
        clase.put("horaInicio", startTime);
        clase.put("horaFin", endTime);
        clase.put("recurrente", isRecurring);
        clase.put("alumnos", selectedStudentIds);

        db.collection("clases")
                .add(clase)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(CreateClassActivity.this, "Clase creada con éxito", Toast.LENGTH_SHORT).show();
                        finish(); // Volver a la actividad anterior
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateClassActivity.this, "Error al crear la clase", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
