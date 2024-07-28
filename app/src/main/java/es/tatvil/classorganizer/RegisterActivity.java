package es.tatvil.classorganizer;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText, nombreEditText, apellido1EditText, apellido2EditText, dniEditText, telefonoEditText, direccionEditText, codigoPostalEditText;
    private RadioGroup roleRadioGroup;
    private Button registerButton;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        nombreEditText = findViewById(R.id.nombreEditText);
        apellido1EditText = findViewById(R.id.apellido1EditText);
        apellido2EditText = findViewById(R.id.apellido2EditText);
        dniEditText = findViewById(R.id.dniEditText);
        telefonoEditText = findViewById(R.id.telefonoEditText);
        direccionEditText = findViewById(R.id.direccionEditText);
        codigoPostalEditText = findViewById(R.id.codigoPostalEditText);
        roleRadioGroup = findViewById(R.id.roleRadioGroup);
        registerButton = findViewById(R.id.registerButton);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String nombre = nombreEditText.getText().toString().trim();
        String apellido1 = apellido1EditText.getText().toString().trim();
        String apellido2 = apellido2EditText.getText().toString().trim();
        String dni = dniEditText.getText().toString().trim();
        String telefono = telefonoEditText.getText().toString().trim();
        String direccion = direccionEditText.getText().toString().trim();
        String codigoPostal = codigoPostalEditText.getText().toString().trim();
        int selectedRoleId = roleRadioGroup.getCheckedRadioButtonId();

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required.");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required.");
            return;
        }

        if (password.length() < 6) {
            passwordEditText.setError("Password should be at least 6 characters.");
            return;
        }

        if (selectedRoleId == -1) {
            Toast.makeText(this, "Select a role.", Toast.LENGTH_SHORT).show();
            return;
        }

        String role = ((RadioButton) findViewById(selectedRoleId)).getText().toString().toLowerCase();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String userId = mAuth.getCurrentUser().getUid();
                            Persona user;
                            if ("student".equals(role)) {
                                user = new Alumno(nombre, apellido1, apellido2, new Date(), dni, telefono, email, direccion, codigoPostal, new Localidad());
                            } else {
                                user = new Profesor(nombre, apellido1, apellido2, new Date(), dni, telefono, email, direccion, codigoPostal, new Localidad());
                            }
                            db.collection("users").document(userId).set(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                                finish();
                                            } else {
                                                Toast.makeText(RegisterActivity.this, "Failed to save user data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
