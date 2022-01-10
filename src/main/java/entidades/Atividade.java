package entidades;

import java.util.Date;

public class Atividade {
    private int id;
    private String nome;
    
    private Double valor_maximo;
    private Date data_de_entrega;
    private int id_professor;
    
    private Professor professor;
    private Nota nota;
    
    public Atividade() {
	}
    
    public Atividade(int id, String nome, Double valor_maximo, Date data_de_entrega, Professor professor, Nota nota) {
		this.id = id;
		this.nome = nome;
		this.valor_maximo = valor_maximo;
		this.data_de_entrega = data_de_entrega;
		this.professor = professor;
		this.nota = nota;
	}

    public Atividade(int id, String nome, Double valor_maximo, Date data_de_entrega, Professor professor) {
		this.id = id;
		this.nome = nome;
		this.valor_maximo = valor_maximo;
		this.data_de_entrega = data_de_entrega;
		this.professor = professor;
	}
    
    public Atividade(int id, String nome, Double valor_maximo, Date data_de_entrega, int id_professor) {
		this.id = id;
		this.nome = nome;
		this.valor_maximo = valor_maximo;
		this.data_de_entrega = data_de_entrega;
		this.id_professor = id_professor;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeAtividade() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor_maximo() {
        return valor_maximo;
    }

    public void setValor_maximo(Double valor_maximo) {
        this.valor_maximo = valor_maximo;
    }

    public Date getData_de_entrega() {
        return data_de_entrega;
    }

    public void setData_de_entrega(Date data_de_entrega) {
        this.data_de_entrega = data_de_entrega;
    }

    public int getId_professor() {
        return id_professor;
    }

    public void setId_professor(int id_professor) {
        this.id_professor = id_professor;
    }

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Nota getNota() {
		return nota;
	}

	public void setNota(Nota nota) {
		this.nota = nota;
	}

	@Override
	public String toString() {
		return "Atividade [id=" + id + ", nome=" + nome + ", valor_maximo=" + valor_maximo + ", data_de_entrega="
				+ data_de_entrega + ", id_professor=" + id_professor + ", professor=" + professor + ", nota=" + nota
				+ "]";
	}
	
}
