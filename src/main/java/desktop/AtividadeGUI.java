package desktop;

import dados.Queries;
import entidades.Atividade;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class AtividadeGUI {
    private Connection conexao;
    private JPanel atividadePanel;
    private JTextField nomeAtividade;
    private JTextField prazoLimiteDia, prazoLimiteMes, prazoLimiteAno;
    private JTextField notaMaxima;
    private JButton criarAtividade;
    private Atividade atividadeNota;
    private Date data;

    public JPanel criaJPanelAtividade(Connection conexaoBanco){
        conexao = conexaoBanco;
        atividadePanel = new JPanel();
        atividadeNota = new Atividade();

        JPanel panelEdicao1 = new JPanel();
        panelEdicao1.setBorder(BorderFactory.createEmptyBorder(40,50,0,45));
        BoxLayout l = new BoxLayout(panelEdicao1, BoxLayout.X_AXIS);
        panelEdicao1.setLayout(l);

        panelEdicao1.add(new JLabel("Nome da Atividade: "));
        nomeAtividade = new JTextField();
        nomeAtividade.setColumns(10);
        panelEdicao1.add(nomeAtividade);

        JPanel panelEdicao2 = new JPanel();
        panelEdicao2.setBorder(BorderFactory.createEmptyBorder(35,30,0,50));
        BoxLayout l2 = new BoxLayout(panelEdicao2, BoxLayout.X_AXIS);
        panelEdicao2.setLayout(l2);

        panelEdicao2.add(new JLabel("Prazo limite de entrega: "));
        prazoLimiteDia = new JTextField();
        prazoLimiteDia.setColumns(2);
        panelEdicao2.add(prazoLimiteDia);
        panelEdicao2.add(new JLabel(" / "));
        prazoLimiteMes = new JTextField();
        prazoLimiteMes.setColumns(2);
        panelEdicao2.add(prazoLimiteMes);
        panelEdicao2.add(new JLabel(" / "));
        prazoLimiteAno = new JTextField();
        prazoLimiteAno.setColumns(3);
        panelEdicao2.add(prazoLimiteAno);

        JPanel panelEdicao3 = new JPanel();
        panelEdicao3.setBorder(BorderFactory.createEmptyBorder(35,90,40,50)); //--
        BoxLayout l3 = new BoxLayout(panelEdicao3, BoxLayout.X_AXIS);
        panelEdicao3.setLayout(l3);

        panelEdicao3.add(new JLabel("Nota máxima: "));
        notaMaxima = new JTextField();
        notaMaxima.setText("10");
        notaMaxima.setColumns(10);
        panelEdicao3.add(notaMaxima);

        atividadePanel.add(panelEdicao1);
        atividadePanel.add(panelEdicao2);
        atividadePanel.add(panelEdicao3);

        criarAtividade = new JButton("Criar");
        criarAtividade.setBorder(BorderFactory.createEmptyBorder(15,30,15,30));
        criarAtividade.setBackground(new Color(239, 246, 255));
        atividadePanel.add(criarAtividade);

        getBotaoCriarAtividade();
//        getConfereNota(Double.valueOf(notaMaxima.getText()));

        panelEdicao1.setBackground(new Color(89, 165, 201, 169));
        panelEdicao2.setBackground(new Color(89, 165, 201, 169));
        panelEdicao3.setBackground(new Color(89, 165, 201, 169));
        atividadePanel.setBackground(new Color(5, 128, 176, 169));
        return atividadePanel;
    }

    public Boolean getConfereNota(Double nota){

        if(nota > 100 || nota < 0){
            JOptionPane.showMessageDialog(null,
                    "Digite um número de nota valido",
                    "Atenção",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
        return true;
    }

    public Boolean isBefore(Boolean before){
        if(before) {
            System.out.println("funfou");
            JOptionPane.showMessageDialog(null,
                    "DIGITE UMA DATA VÁLIDA.",
                    "ATENÇÃO", JOptionPane.WARNING_MESSAGE
            );
            return false;
        }
        return true;
    }

    public Boolean isBlank(){
        if (nomeAtividade.getText().isBlank() || notaMaxima.getText().isBlank() || prazoLimiteDia.getText().isBlank() ||
                prazoLimiteMes.getText().isBlank() || prazoLimiteAno.getText().isBlank()) {

            JOptionPane.showMessageDialog(null,
                    "ERRO \nINFORMAÇÕES FALTANDO.",
                    "ERROR", JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
        return true;
    }

    public JButton getBotaoCriarAtividade(){
        criarAtividade.addActionListener((al) -> {

            LocalDate dataDeHoje = LocalDate.now();
            //Transforma em int
            Integer prazoLimiteAnoInt = Integer.valueOf(prazoLimiteAno.getText());
            Integer prazoLimiteMesInt = Integer.valueOf(prazoLimiteMes.getText());
            Integer prazoLimiteDiaInt = Integer.valueOf(prazoLimiteDia.getText());
            //Transforma em data
            LocalDate dataAtividade = LocalDate.of(prazoLimiteAnoInt, prazoLimiteMesInt, prazoLimiteDiaInt);
            //DEBBUG
            System.out.println(dataDeHoje.getDayOfMonth() + " " + dataDeHoje.getMonthValue() + " " +
                    dataDeHoje.getYear() + " " + dataDeHoje + " " + dataAtividade
            );

//            getConfereNota(Double.valueOf(notaMaxima.getText()));
//            isBefore(dataAtividade.isBefore(dataDeHoje));

            try {
//            System.out.println(nomeAtividade.getText()+" "+ Integer.valueOf(prazoLimiteAno.getText())+" "+
//                    Integer.valueOf(prazoLimiteMes.getText())+" "+
//                    Integer.valueOf(prazoLimiteDia.getText())+" "+Double.parseDouble(notaMaxima.getText()));

                if (!getConfereNota(Double.valueOf(notaMaxima.getText())) ||
                        !isBefore(dataAtividade.isBefore(dataDeHoje)) ||
                        !isBlank()
                ) {
                    System.out.println("Verificação feita");
                }else{//adiciona no banco

                    //Transforma a Data em Data do sql
                    java.sql.Date dataSql = java.sql.Date.valueOf(dataAtividade);

                    Queries queriesAtividade = new Queries(conexao);
                    queriesAtividade.cadastroAtividade(nomeAtividade.getText(), dataSql, Double.parseDouble(notaMaxima.getText()));

                    JOptionPane.showMessageDialog(null,
                            "     ATIVIDADE CRIADA.",
                            "SUCESSO",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,
                        "ERRO \nFAVOR TENTAR NOVAMENTE! DIGITE NÚMEROS EM ENTREGA E EM NOTA",
                        "ERROR",JOptionPane.ERROR_MESSAGE
                );

                e.printStackTrace();
            }
        });

        return criarAtividade;
    }
}
