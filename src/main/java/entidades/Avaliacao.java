package entidades;

public class Avaliacao {
	private Atividade atividade;
	private Nota nota;

	public Avaliacao(Atividade atividade, Nota nota) {
		super();
		this.atividade = atividade;
		this.nota = nota;
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	public Nota getNota() {
		return nota;
	}

	public void setNota(Nota nota) {
		this.nota = nota;
	}

	@Override
	public String toString() {
		return " *** Nome da atividade: " + atividade.getNomeAtividade() 
	+ "	| " + "Valor maximo: " + atividade.getValor_maximo()
	+ "	| " + "Nota do aluno: " + nota.getNota_aluno();
	}
}
