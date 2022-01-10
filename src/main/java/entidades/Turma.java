package entidades;

public class Turma {
    private Integer id;
    private String nome;
    
    private Atividade atividade;
    private Professor professor;
    
    public Turma() {
	}
    
    public Turma(Integer id, String nome, Atividade atividade) {
		this.id = id;
		this.nome = nome;
		this.atividade = atividade;
	}

    public Turma(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeTurma() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	@Override
	public String toString() {
		return "Turma [id=" + id + ", nome=" + nome + ", atividade=" + atividade + ", professor=" + professor + "]";
	}
	
}
