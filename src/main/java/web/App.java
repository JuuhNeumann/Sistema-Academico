package web;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import conexaoBanco.Fabrica;
import dados.Queries;
import desktop.InterfacePrincipal;
import entidades.Aluno;
import entidades.Atividade;
import entidades.Avaliacao;
import entidades.Nota;
import entidades.Professor;
import entidades.Turma;
import spark.Spark;

import javax.swing.*;

public class App {

	private static Queries queriesDados = null;

	public static void main(String[] args) throws Exception {

		// Conectando com o banco
		Fabrica fab = new Fabrica();
		Connection conexao = fab.conectar();
		Queries queriesDados = new Queries(conexao);
		System.out.println("Conectando ao banco");


		Spark.port(2020);
		System.out.println("Registrando rotas...");

		Spark.get("/", (req, res) -> {
			return ViewUtil.renderiza("/visualiza-notas.html");
		});

		Spark.get("/localizando", (req, res) -> {
			return ViewUtil.renderiza("/localizando.html");
		});

		Spark.post("/localiza-matricula", (req, res) -> {
			String matricula = req.queryParams("matricula");
			// verificar se a matricula existe no banco de dados
			try {
				boolean existe = false;
				existe = queriesDados.buscaMatricula(matricula) != null;
				System.out.println(matricula+" "+existe);
				if (existe) {
					res.redirect("/exibir-notas?matricula=" + matricula);
					System.out.println("ENTROU NO IF");
					return "";
				} else {
					return "Matricula nao encontrada!";
				}
			} catch (Exception e) {
				return e.getClass().getName() + ": " + e.getMessage();
			}

		});

		Spark.get("/exibir-notas", (req, res) -> {
			try {
				String matriculas = req.queryParams("matricula");
				int matricula = Integer.parseInt(matriculas);
				Aluno al = queriesDados.buscaAluno(matricula);
				Turma t = queriesDados.buscaTurma(al.getTurma().getId());
				Nota n = queriesDados.buscaNota(matricula);
				Atividade at = queriesDados.buscaAtividade(n.getAtividade().getId());//--
				Professor p = queriesDados.buscaProfessor(at.getProfessor().getId());
				List<Avaliacao> av = queriesDados.buscaTodasNotas(matricula);

				Map<String, Object> ctx = new HashMap<>();

				ctx.put("matricula", al.getMatricula());
				ctx.put("nome", al.getNomeAluno());
				ctx.put("email", al.getEmail());

				ctx.put("lista", av);

				return ViewUtil.renderiza("/exibe-notas.html", ctx);
			} catch (Exception e) {
				System.out.println("NÃO ENTROU NO EXIBIR");
				return e.getClass().getName() + ": " + e.getMessage();
			}

		});

		System.out.println(String.format("Escutando na porta %d...", Spark.port()));
	}
}
