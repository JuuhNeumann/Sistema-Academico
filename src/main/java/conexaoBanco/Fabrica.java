package conexaoBanco;

import java.sql.Connection;

public class Fabrica {

	// M�todo que intermedia a conexao com o banco
	public static Connection conectar() {
		Conexao banco = new Conexao();
		Connection con = banco.conectar();
		return con;
	}
}
