package desktop;

import dados.Queries;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Notas {
    private JPanel notaPanel;
    private JLabel nomeAtividade;
    private JButton uploadArquivo;
    private JButton enviarNota;
    private JButton baixarModelo;
    private String dir = "";
    private Connection conexao;
    private String[] atividades;
    private JComboBox atividadesExistentes;

    public JPanel criaJPanelNota(Connection conexaoBanco){
        conexao = conexaoBanco;
        notaPanel = new JPanel();

        JPanel panelEdicao1 = new JPanel();
        panelEdicao1.setBorder(BorderFactory.createEmptyBorder(40,40,0,50));
        BoxLayout l = new BoxLayout(panelEdicao1, BoxLayout.X_AXIS);
        panelEdicao1.setLayout(l);

        JLabel txtModelo = new JLabel("Planilha de notas pronta:   ");
        panelEdicao1.add(txtModelo);

        baixarModelo = new JButton("Baixar modelo .csv");
        baixarModelo.setBorder(BorderFactory.createEmptyBorder(4,20,4,20));
        baixarModelo.setBackground(new Color(239, 246, 255));
        panelEdicao1.add(baixarModelo);

        JPanel panelEdicao2 = new JPanel();
        panelEdicao2.setBorder(BorderFactory.createEmptyBorder(50,65,30,40));
        BoxLayout l2 = new BoxLayout(panelEdicao2, BoxLayout.X_AXIS);
        panelEdicao2.setLayout(l2);

        try {
            Queries queriesAtividade = new Queries(conexao);
            atividades = queriesAtividade.getNomeAtividade();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        atividadesExistentes = new JComboBox<>(atividades);
        atividadesExistentes.setBackground(new Color(239, 246, 255));
        panelEdicao2.add(atividadesExistentes);

        panelEdicao2.add(Box.createRigidArea(new Dimension(35,0)));

        uploadArquivo = new JButton("Upload arquivo");
        uploadArquivo.setBorder(BorderFactory.createEmptyBorder(4,31,4,29));
        uploadArquivo.setBackground(new Color(239, 246, 255));
        panelEdicao2.add(uploadArquivo);

        nomeAtividade = new JLabel("Dir: ");
        nomeAtividade.setBorder(BorderFactory.createEmptyBorder(0,130,25,120));

        enviarNota = new JButton("Lançar notas");
        enviarNota.setBorder(BorderFactory.createEmptyBorder(15,35,15,35));
        enviarNota.setBackground(new Color(239, 246, 255));

        notaPanel.add(panelEdicao1);
        notaPanel.add(panelEdicao2);
        notaPanel.add(nomeAtividade);
        notaPanel.add(enviarNota);

        getBotaoUpload();
        getBotaoModelo();
        getBotaoEnviarNotas();

        panelEdicao1.setBackground(new Color(89, 165, 201, 169));
        panelEdicao2.setBackground(new Color(89, 165, 201, 169));
        notaPanel.setBackground(new Color(5, 128, 176, 169));
        return notaPanel;
    }

    public void getBotaoEnviarNotas(){
        enviarNota.addActionListener((al) -> {
            //Pegar o diretório do arquivo OK. Abrir o arquivo OK. Separar as informações do arquivo OK. Pegar o nome da atividadeOK.
            //Salvar essas informações no banco OK.
            //Fazer verificação para nota máxima OK

            System.out.println(atividadesExistentes.getSelectedItem());//DEBUG
            JOptionPane.showMessageDialog(null,"Aguarde alguns instantes." +
                    "\nClique em OK para prosseguir.","AGUARDE",JOptionPane.WARNING_MESSAGE);

            try {
                File arquivoComNotas = new File(dir);
                Scanner scanner = new Scanner(arquivoComNotas);
                String splitVirgula;
                String[] infoNotas;
                Double doubleJuncaoDecimais;
                Queries queries = new Queries(conexao);
                Integer countErrors = 0;

                while (scanner.hasNextLine()){
                    splitVirgula = scanner.nextLine();
                    splitVirgula = splitVirgula.replace('"',' '); //Tira as aspas dos números com casa decimal
                    infoNotas = splitVirgula.split(",");

                    if(infoNotas.length == 3){
                        String juncaoDecimais = infoNotas[1]+"."+infoNotas[2];
                        doubleJuncaoDecimais = Double.valueOf(juncaoDecimais);
                        countErrors += queries.setNotas(infoNotas[0], doubleJuncaoDecimais, atividadesExistentes.getSelectedItem().toString());
                    }else{
                        countErrors += queries.setNotas(infoNotas[0], Double.parseDouble(infoNotas[1]), atividadesExistentes.getSelectedItem().toString());
                    }
                }

                if (countErrors == 0){
                    JOptionPane.showMessageDialog(null,
                            "Notas inseridas com sucesso",
                            "SUCCESS",
                            JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null,
                            "Falha ao inserir as notas.",
                            "FAIL",
                            JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "ERRO \nFAVOR TENTAR NOVAMENTE! VERIFIQUE O ARQUIVO NOVAMENTE.",
                        "ERROR",JOptionPane.ERROR_MESSAGE
                );
                e.printStackTrace();
            }
        });
    }

    public void getBotaoUpload(){
        uploadArquivo.addActionListener((al) -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Selecione arquivos .csv", "csv"
            );
            fileChooser.setFileFilter(filter);

            int retorno = fileChooser.showOpenDialog(null);

            if(retorno == JFileChooser.APPROVE_OPTION){
                dir = fileChooser.getSelectedFile().getAbsolutePath();
                nomeAtividade.setText(nomeAtividade.getText() + dir);
                System.out.println(dir);
            }
        });
    }

    public void getBotaoModelo(){
        baixarModelo.addActionListener((al) -> {

            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfc.setDialogTitle("Selecione um diretório para salvar a cópia do modelo: ");
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int returnValue = jfc.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                if (jfc.getSelectedFile().isDirectory()) {
                    System.out.println("You selected the directory: " + jfc.getSelectedFile());
                    File src = new File("src\\main\\java\\desktop\\Modelo.csv");
                    File target = new File(jfc.getSelectedFile() + "\\CopiaModelo.csv");

                    try {
                        copy(src, target);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);  // Transferindo bytes de entrada para saída
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    public String getDir() { //pego o dir pra saber onde esta o arquivo no pc do usuario
        return dir;
    }
}
