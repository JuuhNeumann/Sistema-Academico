package entidades;

public class Nota {
    private Integer id;
    private Integer id_atividade;
    private Integer matricula_aluno;
    private Double nota_aluno;
    
    private Atividade atividade;
    private Aluno matriculas_aluno;
    
    public Nota() {
  	}
    
    public Nota(Double nota_aluno) {
		this.nota_aluno = nota_aluno;
	}
    

    public Nota(Integer id, Atividade atividade, Integer matricula_aluno, Double nota_aluno) {
		this.id = id;
		this.atividade = atividade;
		this.matricula_aluno = matricula_aluno;
		this.nota_aluno = nota_aluno;
	}
    
    public Nota(Integer id, Atividade atividade, Aluno matriculas_aluno, Double nota_aluno) {
		this.id = id;
		this.atividade = atividade;
		this.matriculas_aluno = matriculas_aluno;
		this.nota_aluno = nota_aluno;
	}
    
    public Nota(Integer id, Integer id_atividade, Integer matricula_aluno, Double nota_aluno) {
		this.id = id;
		this.id_atividade = id_atividade;
		this.matricula_aluno = matricula_aluno;
		this.nota_aluno = nota_aluno;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_atividade() {
        return id_atividade;
    }

    public void setId_atividade(Integer id_atividade) {
        this.id_atividade = id_atividade;
    }

    public Integer getMatricula_aluno() {
        return matricula_aluno;
    }

    public void setMatricula_aluno(Integer matricula_aluno) {
        this.matricula_aluno = matricula_aluno;
    }


    public Double getNota_aluno() {
        return nota_aluno;
    }

    public void setNota_aluno(Double nota_aluno) {
        this.nota_aluno = nota_aluno;
    }

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	public Aluno getMatriculas_aluno() {
		return matriculas_aluno;
	}

	public void setMatriculas_aluno(Aluno matriculas_aluno) {
		this.matriculas_aluno = matriculas_aluno;
	}

	@Override
	public String toString() {
		return "Nota [id=" + id + ", id_atividade=" + id_atividade + ", matricula_aluno=" + matricula_aluno
				+ ", nota_aluno=" + nota_aluno + ", atividade=" + atividade + ", matriculas_aluno=" + matriculas_aluno
				+ "]";
	}
	
}
