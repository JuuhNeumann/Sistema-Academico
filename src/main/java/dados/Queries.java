package dados;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import entidades.Aluno;
import entidades.Atividade;
import entidades.Avaliacao;
import entidades.Nota;
import entidades.Professor;
import entidades.Turma;

public class Queries {

	private static Connection conexaoBD;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	private Aluno aluno;
	private Professor professor;
	private Atividade atividade;
	private Nota nota;

	static final String LISTAR_ALUNO = "SELECT matricula, nome, email, id_turma FROM ALUNO";
	static final String LISTAR_PROFESSOR = "SELECT matricula, nome, email, id_turma FROM ALUNO";
	// PARTE ALUNO SITE
	/*
	 * static final String LISTAR_NOTAS_ALUNO =
	 * "SELECT aluno.matricula, aluno.nome, atividade.nota_aluno FROM aluno " +
	 * "JOIN aluno_atividade ON (aluno.matricula = aluno_atividade.matricula_aluno) "
	 * + "JOIN atividade ON (aluno_atividade.id_atividade = atividade.id);
	 */

	public Queries(Connection conexao) {
		conexaoBD = conexao;
	}

	public static void cadastroAtividade(String nome, Date data_entrega, Double valor_max) throws SQLException {
		System.out.println("chegou");//DEBBUG
		PreparedStatement preparedStatement = conexaoBD.prepareStatement("INSERT INTO atividade (nome, data_de_entrega, valor_maximo, id_professor) VALUES(?, ?, ?, 1);");
		preparedStatement.setString(1, nome);
		preparedStatement.setDate(2,data_entrega);
		preparedStatement.setDouble(3,valor_max);
		preparedStatement.execute();
		System.out.println("entrou no cadastro");
	}

	public int getTotalAtividade() throws SQLException {
		PreparedStatement ps = conexaoBD.prepareStatement("Select count(*) from atividade;");
		ps.execute();
		ResultSet rsCount = ps.getResultSet();

		Integer contador = 0;

		if(rsCount.next()) {
			contador = rsCount.getInt(1);
			System.out.println(rsCount.getInt(1));
		}
		return contador;
	}

	public String[] getNomeAtividade() throws SQLException {
		PreparedStatement preparedStatement = conexaoBD.prepareStatement("SELECT nome FROM atividade;");
		preparedStatement.execute();
		ResultSet rs = preparedStatement.getResultSet();

		String[] atividades = new String[getTotalAtividade()];

		int contador = 0;
		while (rs.next()){
			atividades[contador] = rs.getString(1);
//            System.out.println(atividades[contador]); //DEBUG
			contador++;
		}

		return atividades;
	}

	public void getIdAtividade(String nomeAtividade) throws SQLException {
		PreparedStatement ps = conexaoBD.prepareStatement("SELECT id, valor_maximo FROM atividade WHERE nome='"+nomeAtividade+"';");
		ps.execute();
		ResultSet rs = ps.getResultSet();

		atividade = new Atividade();

		while (rs.next()){
			atividade.setId(rs.getInt(1));
			atividade.setValor_maximo(rs.getDouble(2));

			System.out.println(atividade.getId()+" "+atividade.getValor_maximo());
		}
	}

	public void getMatriculaAluno(String nomeAluno) throws SQLException {
		PreparedStatement ps = conexaoBD.prepareStatement("SELECT matricula FROM aluno WHERE nome='"+nomeAluno+"';");
		ps.execute();
		ResultSet rs = ps.getResultSet();

		aluno = new Aluno();

		while (rs.next()){
			aluno.setMatricula(rs.getInt(1));
			System.out.println("MATRICULA: "+aluno.getMatricula());
		}
	}

	public Integer setNotas(String nomeAluno, Double notaAluno, String nomeAtividade){
		Integer count=0;
		try {
			System.out.println("Nome aluno: "+nomeAluno+" Nota aluno: "+notaAluno+" Atividade: "+nomeAtividade); //DEBUG
			PreparedStatement ps = conexaoBD.prepareStatement("INSERT INTO nota (id_atividade, matricula_aluno, nota_aluno) VALUES (?,?,?);");

			getIdAtividade(nomeAtividade);
			getMatriculaAluno(nomeAluno);

			if (notaAluno > atividade.getValor_maximo()){
				JOptionPane.showMessageDialog(null,"Confira o arquivo novamente. " +
								"\nNota do(a) aluno(a) "+nomeAluno+" não correspondente com o valor estipulado",
						"ERROR",JOptionPane.ERROR_MESSAGE);
				count = 1;
			}else{
				ps.setInt(1,atividade.getId());
				ps.setInt(2,aluno.getMatricula());
				ps.setDouble(3,notaAluno);
				ps.execute();
				count = 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public void infoAluno(Aluno alunoArg) throws SQLException {
		ps = conexaoBD.prepareStatement(LISTAR_ALUNO);
		ps.execute();
		rs = ps.getResultSet();

//        aluno = alunoArg;

		while (rs.next()) {
			aluno.setMatricula(rs.getInt(1));
			aluno.setNome(rs.getString(2));
			aluno.setEmail(rs.getString(3));
			aluno.setId_turma(rs.getInt(4));
		}
	}

	public void infoProfessor(Professor profArg) throws SQLException {
		ps = conexaoBD.prepareStatement(LISTAR_PROFESSOR);
		ps.execute();
		rs = ps.getResultSet();

		professor = profArg;

		while (rs.next()) {
			professor.setId(rs.getInt(1));
			professor.setNome(rs.getString(2));
			professor.setEmail(rs.getString(3));
			professor.setId_turma(rs.getInt(4));
		}
	}

	// Metodo que pega uma lista de notas dos alunos do banco
	/*
	 * public Aluno listarNotasAlunos(Aluno al) { atividade = new Atividade(); try {
	 * ps = conexaoBD.prepareStatement(LISTAR_NOTAS_ALUNO); ps.execute(); rs =
	 * ps.getResultSet();
	 * 
	 * while (rs.next()) { aluno.setMatricula(rs.getInt(1));
	 * aluno.setNome(rs.getString(2)); nota.setNota_aluno(rs.getDouble(3)); } return
	 * al;
	 * 
	 * } catch (SQLException e) { JOptionPane.showMessageDialog(null, "ERROR :" +
	 * e.getSQLState(), "DATABASE ERROR: ", JOptionPane.ERROR_MESSAGE); } return al;
	 * }
	 */

	// Metodo para buscar uma matricula no banco
	public Aluno buscaMatricula(String matriculaa) {
		int matricula = Integer.parseInt(matriculaa);
		try {
			ps = conexaoBD.prepareStatement("SELECT * FROM aluno WHERE matricula = ?");
			ps.setInt(1, matricula);
			ps.execute();
			rs = ps.getResultSet();

			while (rs.next()) {
				int matricula_aluno = rs.getInt("matricula");
				String nome = rs.getString("nome");
				String email = rs.getString("email");
				int id_turma = rs.getInt("id_turma");
				Turma turmazinha = buscaTurma(id_turma);
				Aluno aluno = new Aluno(matricula_aluno, turmazinha, nome, email);
				return aluno;

			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Matricula nao encontrada!");
		}
		return null;

	}

	// Metodo para buscar um professor no banco
	public Professor buscaProfessor(int idProfessor) {
		try {
			ps = conexaoBD.prepareStatement("SELECT * FROM professor WHERE id = ?");
			ps.setInt(1, idProfessor);
			ps.execute();
			rs = ps.getResultSet();

			if (rs.next()) {
				int idzinho = rs.getInt("id");
				String nomezinho = rs.getString("nome");
				String emailzinho = rs.getString("email");
				int id_turma = rs.getInt("id_turma");
				Turma turmazinha = buscaTurma(id_turma);
				Professor professorzinho = new Professor(idzinho, turmazinha, nomezinho, emailzinho);
				return professorzinho;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Professor nao encontrado!");
		}
		return null;
	}

	// Metodo para buscar uma turma no banco
	public Turma buscaTurma(int idTurma) {
		try {
			ps = conexaoBD.prepareStatement("SELECT * FROM turma WHERE id = ?");
			ps.setInt(1, idTurma);
			ps.execute();
			rs = ps.getResultSet();

			if (rs.next()) {
				int idzinho = rs.getInt("id");
				String nomezinho = rs.getString("nome");
				Turma turmazinha = new Turma(idzinho, nomezinho);
				return turmazinha;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Turma nao encontrada!");
		}
		return null;
	}

	// Metodo para buscar uma atividade no banco
	public Atividade buscaAtividade(int idAtividade) {
		try {
			ps = conexaoBD.prepareStatement("SELECT * FROM atividade WHERE id = ?");
			ps.setInt(1, idAtividade);
			ps.execute();
			rs = ps.getResultSet();

			if (rs.next()) {
				int idzinho = rs.getInt("id");
				String nomezinho = rs.getString("nome");
				double valor_maximo = rs.getDouble("valor_maximo");
				Date data_entrega = rs.getDate("data_de_entrega");
				int id_professor = rs.getInt("id_professor");
				Professor professorzinho = buscaProfessor(id_professor);
				Atividade atividadezinha = new Atividade(idzinho, nomezinho, valor_maximo, data_entrega,
						professorzinho);
				return atividadezinha;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Atividade nao encontrada!");
		}
		return null;
	}

	// Metodo para buscar um aluno no banco
	public Aluno buscaAluno(int matricula) {
		try {
			ps = conexaoBD.prepareStatement("SELECT * FROM aluno WHERE matricula = ?");
			ps.setInt(1, matricula);
			ps.execute();
			rs = ps.getResultSet();

			if (rs.next()) {
				int matriculazinha = rs.getInt("matricula");
				String nomezinho = rs.getString("nome");
				String emailzinho = rs.getString("email");
				int id_turma = rs.getInt("id_turma");
				Turma turmazinha = buscaTurma(id_turma);
				Aluno alunozinho = new Aluno(matriculazinha, turmazinha, nomezinho, emailzinho);
				return alunozinho;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Aluno nao encontrado!");
		}
		return null;
	}

	// Metodo para buscar uma nota no banco
	public Nota buscaNota(int matriculaAluno) {
		try {
			ps = conexaoBD.prepareStatement("select * from aluno join nota on (aluno.matricula = nota.matricula_aluno) where matricula = ?"); //----
			ps.setInt(1, matriculaAluno);
			ps.execute();
			rs = ps.getResultSet();
			System.out.println("Entrou em busca nota: "+matriculaAluno);
			if (rs.next()) {
				System.out.println("Entrou no while");
				int idzinho = rs.getInt("id");
				int matricula_aluno = rs.getInt("matricula_aluno");
				double nota_aluno = rs.getDouble("nota_aluno");
				int id_atividade = rs.getInt("id_atividade");
				Atividade atividadezinha = buscaAtividade(id_atividade);
				Nota notazinha = new Nota(idzinho, atividadezinha, matricula_aluno, nota_aluno);
				return notazinha;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Nota do aluno nao encontrada!");
		}
		return null;
	}
	
	// Metodo para buscar uma nota no banco
    public List<Avaliacao> buscaTodasNotas(int matricula) {
        List<Avaliacao> avalia = new ArrayList<>();
        try {
            ps = conexaoBD.prepareStatement("SELECT aluno.matricula, aluno.email, atividade.id, atividade.nome, atividade.valor_maximo, nota.nota_aluno FROM aluno, atividade, nota WHERE matricula = matricula_aluno AND atividade.id = id_atividade AND matricula = ?");

            ps.setInt(1, matricula);
            ps.execute();
            rs = ps.getResultSet();

            while (rs.next()) {
                int matriculaAluno = rs.getInt("matricula");
                String emailAluno = rs.getString("email");
                int idAtividade = rs.getInt("id");
                String nomeAtividade = rs.getString("nome");
                double valor_maximoAtividade = rs.getDouble("valor_maximo");
                double notaNota = rs.getDouble("nota_aluno");

                Nota nota = new Nota(notaNota);
                Atividade atividade = new Atividade(idAtividade, nomeAtividade, valor_maximoAtividade, null, null, nota);
                Turma turma = new Turma(1, nomeAtividade, atividade);
                Avaliacao avaliacao = new Avaliacao(atividade, nota);
                Aluno aluno = new Aluno(matriculaAluno, turma, null, nomeAtividade, emailAluno);
                aluno.getAvaliacao().add(avaliacao);
                avalia.add(avaliacao);
            }
            for(Avaliacao x: avalia) { 
				System.out.println(x.getAtividade().getNomeAtividade());
			}
            return avalia;
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Notas do aluno nao encontrada!");
        }
        return null;
    }
}
