package conexaoBanco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

	private Connection conexao = null;

	private String url = "jdbc:postgresql://ec2-23-23-133-10.compute-1.amazonaws.com:5432/d60h5v6hhdfprq?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
	private String usuario = "ojrssqyxcuvchb";
	private String senha = "7e0451c25f74472b37a98164416b78031b107a4957060254fe4c55260e372a20";

	// Metodo que faz a conexao com o banco de dados
	public Connection conectar() {
		try {
			conexao = DriverManager.getConnection(url, usuario, senha);

			System.out.println("CONEXAO FEITA COM SUCESSO!!");

		} catch (SQLException e) {
			System.out.println("FALHA AO SE CONECTAR COM O BANCO DE DADOS");
			System.out.println(e);
		}
		return conexao;
	}
}
