package com.example.bibliotecaescolar.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bibliotecaescolar.R;
import com.example.bibliotecaescolar.modelos.Aluno;
import com.example.bibliotecaescolar.modelos.Emprestimo;

import java.util.List;


public class AlunoAdapter extends RecyclerView.Adapter<AlunoAdapter.AlunoViewHolder> {
    private List<Aluno> listaAlunos;
    private List<Emprestimo> listaEmprestimos;

    public AlunoAdapter(List<Aluno> listaAlunos, List<Emprestimo> listaEmprestimos) {
        this.listaAlunos = listaAlunos;
        this.listaEmprestimos = listaEmprestimos;
    }

    @NonNull
    @Override
    public AlunoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_aluno, parent, false);
        return new AlunoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlunoViewHolder holder, int position) {
        Aluno aluno = listaAlunos.get(position);
        holder.txtId.setText("ID: " + aluno.getId());
        holder.txtNome.setText("Nome: " + aluno.getNome());
        holder.txtMatricula.setText("Matrícula: " + aluno.getMatricula());
        holder.txtTurma.setText("Turma: " + aluno.getTurma());

        // Verifica se esse aluno tem empréstimo ativo
        String livroEmprestado = "Nenhum";
        for (Emprestimo emp : listaEmprestimos) {
            if (emp.getIdAluno().equals(aluno.getId()) && (emp.getDataDevolucao() == null || emp.getDataDevolucao().isEmpty())) {
                livroEmprestado = emp.getNomeLivro();
                break;
            }
        }

        holder.txtLivroStatus.setText("Livro emprestado: " + livroEmprestado);
    }

    @Override
    public int getItemCount() {
        return listaAlunos.size();
    }

    public static class AlunoViewHolder extends RecyclerView.ViewHolder {
        TextView txtId, txtNome, txtMatricula, txtTurma, txtLivroStatus;

        public AlunoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.txtId);
            txtNome = itemView.findViewById(R.id.txtNome);
            txtMatricula = itemView.findViewById(R.id.txtMatricula);
            txtTurma = itemView.findViewById(R.id.txtTurma);
            txtLivroStatus = itemView.findViewById(R.id.txtLivroStatus);
        }
    }
}
