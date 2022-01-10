package entidades;

import java.util.ArrayList;
import java.util.List;

public class Aluno {
	private Integer matricula;
	private Integer id_turma;
	private String nome;
	private String email;

	private Turma turma;
	private List<Avaliacao> avaliacao = new ArrayList<>();

	public Aluno() {
	}
	
	public Aluno(Integer matricula, Turma turma, Avaliacao avaliacao, String nome, String email) {
		this.matricula = matricula;
		this.turma = turma;
		this.nome = nome;
		this.email = email;
	}

	public Aluno(Integer matricula, Turma turma, String nome, String email) {
		this.matricula = matricula;
		this.turma = turma;
		this.nome = nome;
		this.email = email;
	}

	public Aluno(Integer matricula, int id_turma, String nome, String email) {
		this.matricula = matricula;
		this.id_turma = id_turma;
		this.nome = nome;
		this.email = email;
	}
	

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	public Integer getId_turma() {
		return id_turma;
	}

	public void setId_turma(Integer id_turma) {
		this.id_turma = id_turma;
	}

	public String getNomeAluno() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}
	
	public List<Avaliacao> getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(List<Avaliacao> avaliacao) {
		this.avaliacao = avaliacao;
	}

	@Override
	public String toString() {
		return "Aluno [matricula=" + matricula + ", id_turma=" + id_turma + ", nome=" + nome + ", email=" + email
				+ ", turma=" + turma + ", avaliacao=" + avaliacao + "]";
	}

}
