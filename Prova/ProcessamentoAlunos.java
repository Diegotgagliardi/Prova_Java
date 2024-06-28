import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Aluno {
    int matricula;
    String nome;
    double nota;

    public Aluno(int matricula, String nome, double nota) {
        this.matricula = matricula;
        this.nome = nome;
        this.nota = nota;
    }
}

public class ProcessamentoAlunos {

    public static void main(String[] args) {
        List<Aluno> listaDeAlunos = lerAlunos("alunos.csv");
        processarAlunos(listaDeAlunos);
    }

    private static List<Aluno> lerAlunos(String arquivo) {
        List<Aluno> alunos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            reader.readLine(); // Ignora a linha de cabeçalho
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                int matricula = Integer.parseInt(dados[0]);
                String nome = dados[1];
                double nota = Double.parseDouble(dados[2]);
                alunos.add(new Aluno(matricula, nome, nota));
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return alunos;
    }

    private static void processarAlunos(List<Aluno> alunos) {
        int quantidadeAlunos = alunos.size();
        int aprovados = 0;
        int reprovados = 0;
        double menorNota = Double.MAX_VALUE;
        double maiorNota = Double.MIN_VALUE;
        double somaNotas = 0;

        for (Aluno aluno : alunos) {
            if (aluno.nota >= 6.0) {
                aprovados++;
            } else {
                reprovados++;
            }
            menorNota = Math.min(menorNota, aluno.nota);
            maiorNota = Math.max(maiorNota, aluno.nota);
            somaNotas += aluno.nota;
        }

        double mediaGeral = somaNotas / quantidadeAlunos;

        gravarResumo("resumo.csv", quantidadeAlunos, aprovados, reprovados, menorNota, maiorNota, mediaGeral);
    }

    private static void gravarResumo(String arquivo, int quantidadeAlunos, int aprovados, int reprovados,
                                     double menorNota, double maiorNota, double mediaGeral) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            writer.write("Quantidade de Alunos;Aprovados;Reprovados;Menor Nota;Maior Nota;Média Geral");
            writer.newLine();
            writer.write(quantidadeAlunos + ";" + aprovados + ";" + reprovados + ";" + menorNota + ";" + maiorNota + ";" + mediaGeral);
        } catch (IOException e) {
            System.err.println("Erro ao gravar o arquivo: " + e.getMessage());
        }
    }
}