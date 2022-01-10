package entidades;

public class Professor {
    private Integer id;
    private Integer id_turma;
    private String nome;
    private String email;
    
    private Turma turma;

    public Professor() {
	}
    
    public Professor(Integer id, Turma turma, String nome, String email) {
		this.id = id;
		this.turma = turma;
		this.nome = nome;
		this.email = email;
	}
    
    public Professor(Integer id, Integer id_turma, String nome, String email) {
		this.id = id;
		this.id_turma = id_turma;
		this.nome = nome;
		this.email = email;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_turma() {
        return id_turma;
    }

    public void setId_turma(Integer id_turma) {
        this.id_turma = id_turma;
    }

    public String getNomeProfessor() {
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

	@Override
	public String toString() {
		return "Professor [id=" + id + ", id_turma=" + id_turma + ", nome=" + nome + ", email=" + email + ", turma="
				+ turma + "]";
	}

}
