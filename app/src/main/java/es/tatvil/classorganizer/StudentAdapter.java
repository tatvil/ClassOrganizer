package es.tatvil.classorganizer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private List<Persona> studentList;

    public List<String> getSelectedStudentIds() {
        List<String> selectedStudentIds = new ArrayList<>();
        for (Persona student : studentList) {
                selectedStudentIds.add(student.getUid());
        }
        return selectedStudentIds;
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNombre, tvApellido1, tvApellido2, tvTelefono;

        public StudentViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvApellido1 = itemView.findViewById(R.id.tvApellido1);
            tvApellido2 = itemView.findViewById(R.id.tvApellido2);
            tvTelefono = itemView.findViewById(R.id.tvTelefono);
        }
    }

    public StudentAdapter(List<Persona> studentList) {
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Persona currentItem = studentList.get(position);
        holder.tvNombre.setText(currentItem.getNombre());
        holder.tvApellido1.setText(currentItem.getApellido1());
        holder.tvApellido2.setText(currentItem.getApellido2());
        holder.tvTelefono.setText(currentItem.getEmail());
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }
}
