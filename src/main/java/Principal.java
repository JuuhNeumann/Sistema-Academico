import desktop.InterfacePrincipal;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Principal {

    public static void main(String[] args){
        Conexao BD = new Conexao();
        Connection conexao = BD.conectar();
        new InterfacePrincipal(conexao);
    }
}
