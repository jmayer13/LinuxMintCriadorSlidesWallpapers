
/*- 
 * Classname:             TestImageResise.java 
 * 
 * Version information:   1.0 
 * 
 * Date:                  06/09/2013 - 13:37:26 
 * 
 * author:                Jonas Mayer (jmayer13@hotmail.com) 
 * Copyright notice:      (informações do método, pra que serve, idéia principal) 
 */
package minwallpaper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

/**
 * Classe responsável por demostrar o algoritmo de redimensionamento de imagem
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class TestImageResise {

    private JPanel panel;
    private JFrame frame;
    private JButton button;
    private int x;
    private int y;
    private String filePatch = null;
    private String lastAddDirectory = null;

    public TestImageResise() {
        createView();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.setDragEnabled(false);
                fileChooser.setFileHidingEnabled(true);

                //abre no ultimo diretório aberto
                if (lastAddDirectory != null) {
                    fileChooser.setCurrentDirectory(new File(lastAddDirectory));
                }

                //adiciona filtro para imagens
                fileChooser.addChoosableFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {

                        //de for diretório permite
                        if (f.isDirectory()) {
                            return true;
                        }

                        //pega extenção
                        String extension = null;
                        try {
                            extension = f.getName().substring(f.getName().lastIndexOf('.') + 1);
                        } catch (Exception ex) {
                            extension = null;
                        }

                        //verifica se é uma imagem
                        if (extension != null) {
                            if (extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("gif") || extension.equalsIgnoreCase("jpeg")
                                    || extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("bmp")) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                        return false;
                    }

                    //define descrição do filtro
                    @Override
                    public String getDescription() {
                        return "Apenas imagens";
                    }
                });

                //inicia filechosser e analisa resposta
                int response = fileChooser.showOpenDialog(null);
                if (response == JFileChooser.APPROVE_OPTION) {
                    //obtêm arquivos selecionados
                    File file = fileChooser.getSelectedFile();

                    if (file != null) {
                        //salva ultimo diretório
                        lastAddDirectory = file.getAbsolutePath();
                        filePatch = file.getPath();
                        ImageIcon tmpIcon = new ImageIcon(filePatch);
                        frame.setBounds((x - tmpIcon.getIconWidth() + 30) / 2, (y - tmpIcon.getIconHeight() + 90) / 2, tmpIcon.getIconWidth() + 30, tmpIcon.getIconHeight() + 90);
                        panel.repaint();
                    }
                }

            }
        });
    }

    private void createView() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        x = (int) dim.getWidth();//largura
        y = (int) dim.getHeight();//altura
        frame = new JFrame("Teste do algoritmo de redimencionamento de imagens") {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                if (panel != null) {
                    panel.setBounds(10, 50, frame.getWidth() - 30, frame.getHeight() - 90);
                }
                if (button != null) {
                    button.setBounds((frame.getWidth() - 150) / 2, 10, 150, 30);
                }
            }
        };
        button = new JButton("Abrir Imagem");

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (filePatch != null) {
                    //carrega imagem do arquivo
                    ImageIcon tmpIcon = new ImageIcon(filePatch);
                    //calcula o tamanho da imagem
                    double iconWidth = tmpIcon.getIconWidth();
                    double iconHeight = tmpIcon.getIconHeight();
                    double widthPanel = panel.getWidth();
                    double heightPanel = panel.getHeight();
                    double rr = 0;
                    if ((iconWidth - widthPanel) / widthPanel > (iconHeight - heightPanel) / heightPanel) {
                        rr = widthPanel * 100 / iconWidth;
                    } else if ((iconWidth - widthPanel) / widthPanel == (iconHeight - heightPanel) / heightPanel) {

                        if (widthPanel < heightPanel) {
                            rr = widthPanel * 100 / iconWidth;
                        } else {
                            rr = heightPanel * 100 / iconHeight;
                        }
                    } else {
                        rr = heightPanel * 100 / iconHeight;
                    }
                    iconHeight = iconHeight * rr / 100;
                    iconWidth = iconWidth * rr / 100;

                    //redefine a imagem com o algoritmo eo manaho especificados
                    //nã me pergunte mais por alguma razão obscura eu tive que fazer isso
                    Image image = new ImageIcon(tmpIcon.getImage().getScaledInstance((int) iconWidth, (int) iconHeight, Image.SCALE_SMOOTH)).getImage();
                    int x = (int) (widthPanel - iconWidth) / 2;
                    int y = (int) (heightPanel - iconHeight) / 2;
                    panel.setToolTipText(rr + "%");
                    g.drawImage(image, x, y, (int) iconWidth, (int) iconHeight, Color.WHITE, this);

                }
            }
        };
        frame.setLayout(null);

        frame.setBounds(10, 10, x - 50, y - 60);
        button.setBounds((frame.getWidth() - 150) / 2, 10, 150, 30);
        panel.setBounds(10, 50, x - 70, y - 150);


        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        frame.add(button);
        frame.add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String args[]) {
        new TestImageResise();
    }
}//fim da classe TestImageResise 

