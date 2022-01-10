package desktop;

import entidades.Aluno;
import entidades.Atividade;
import entidades.Professor;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class InterfacePrincipal extends JFrame {
    private Connection conexaoBanco;
    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel = new JPanel();
    private TelaInicial telaInicial;
    private AtividadeGUI atividadeGUI;
    private Notas notas;
    private Aluno aluno;
    private Atividade atividade;
    private Professor professor;


    public InterfacePrincipal(Connection conexao){
        conexaoBanco = conexao;

        cardPanel.setLayout(cardLayout);
        setContentPane(cardPanel);

        telaInicial = new TelaInicial();
        add(getTelaInicial(),"inicial");

        atividadeGUI = new AtividadeGUI();
        add(getTelaAtividade(), "atividade");

        notas = new Notas();
        add(getTelaNota(), "notas");

        botoesIniciais();

        exibe("inicial");
        setTitle("Sistema Academico");

        setSize(400,350); //---------
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //para de rodar ao clicar no x
        setLocationRelativeTo(null); //centraliza
        setVisible(true);
    }
    private void exibe(String nome){
        this.cardLayout.show(this.cardPanel, nome);
    }
    private JPanel getTelaInicial(){
        return telaInicial.criaJPanelInicial();
    }
    private JPanel getTelaAtividade(){
        return atividadeGUI.criaJPanelAtividade(conexaoBanco);
    }
    private JPanel getTelaNota(){
        return notas.criaJPanelNota(conexaoBanco);
    }

    private void botoesIniciais(){
        telaInicial.getBotaoAtividade().addActionListener((al) -> {
            setTitle("Criação de atividade");
            exibe("atividade");
        });

        telaInicial.getBotaoNota().addActionListener((al) -> {
            setTitle("Envio de notas");
            exibe("notas");
        });
    }
}
