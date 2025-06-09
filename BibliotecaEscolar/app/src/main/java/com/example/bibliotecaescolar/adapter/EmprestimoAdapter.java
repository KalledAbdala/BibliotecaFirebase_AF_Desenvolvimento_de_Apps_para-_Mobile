package com.example.bibliotecaescolar.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bibliotecaescolar.R;
import com.example.bibliotecaescolar.modelos.Emprestimo;

import java.util.List;

public class EmprestimoAdapter extends RecyclerView.Adapter<EmprestimoAdapter.ViewHolder> {

    private List<Emprestimo> listaEmprestimos;

    public EmprestimoAdapter(List<Emprestimo> lista) {
        this.listaEmprestimos = lista;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            txtInfo = itemView.findViewById(R.id.txtInfoEmprestimo);
        }
    }

    @NonNull
    @Override
    public EmprestimoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_emprestimo, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EmprestimoAdapter.ViewHolder holder, int position) {
        Emprestimo emp = listaEmprestimos.get(position);

        String texto = "ID: " + emp.getId() +
                "\nAluno: " + emp.getNomeAluno() +
                "\nLivro: " + emp.getNomeLivro() +
                "\nData Empréstimo: " + emp.getDataEmprestimo() +
                "\nData Devolução: " + (emp.getDataDevolucao() != null ? emp.getDataDevolucao() : "Pendente");

        holder.txtInfo.setText(texto);
    }

    @Override
    public int getItemCount() {
        return listaEmprestimos.size();
    }
}
