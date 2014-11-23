 /*-
 * Classname:             MainController.java
 *
 * Version information:   0.3
 *
 * Date:                  06/06/2013 - 14:53:16
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      COPYRIGHT 2013 Jonas Mayer
 */
/*
 * Este arquivo é parte do programa Criador de Slides se Wallpapers para Linux 
 * Mint - CSWM
 * 
 * CSWM é um software livre; você pode redistribui-lo e/ou 
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como 
 * publicada pela Fundação do Software Livre (FSF); na versão 2 da 
 * Licença, ou qualquer versão.
 * 
 * Este programa é distribuido na esperança que possa ser  util, 
 * mas SEM NENHUMA GARANTIA; sem uma garantia implicita de ADEQUAÇÂO a qualquer
 * MERCADO ou APLICAÇÃO EM PARTICULAR. Veja a
 * Licença Pública Geral GNU para maiores detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU 
 * junto com este programa, se não, escreva para a Fundação do Software
 * Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA * 
 */
package minwallpaper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 * Controle principal
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class MainController {

    //visão principal
    private MainFrame mainFrame;
    //ultimo diretório salvo
    private String lastSave;
    //ultimo diretório adicionado
    private String lastAddDirectory;
    //path do ultimo arquivo adicionado
    private String pathFile;

    /**
     * Construtor sem parâmetros
     */
    public MainController() {
        //inicializa aplicação
        start();
    }//fim do construtor

    /**
     * Inicia aplicação
     */
    private void start() {

        //inicializa view
        mainFrame = new MainFrame();
        //adiciona lsiteners
        //adicionar
        mainFrame.setAddButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add();
            }
        });
        //limpar
        mainFrame.setCleanButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> filesPath = new ArrayList();
                mainFrame.setWallpapers(filesPath);
                mainFrame.setWatcher(true);
            }
        });
        //fechar 
        mainFrame.setCloseButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
        //fechar janela
        mainFrame.setCloseWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
        //remover
        mainFrame.setRemoveButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String wallpaper = mainFrame.getSelectedWallpaper();
                if (wallpaper == null) {
                    JOptionPane.showMessageDialog(null, "Selecione um wallpaper", "Atenção!", JOptionPane.INFORMATION_MESSAGE);

                } else {
                    mainFrame.removeWallpaper(wallpaper);
                    mainFrame.setWatcher(true);
                }

            }
        });
        //menu fechar
        mainFrame.setCloseMenuItemActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
        //menu novo
        mainFrame.setNewMenuItemActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = close();
                if (response != 2) {
                    start();
                    mainFrame.setWatcher(false);
                }
            }
        });
        //menu abrir
        mainFrame.setOpenMenuItemActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                open();
            }
        });
        //menu salvar como
        mainFrame.setSaveAsMenuItemActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAs();
            }
        });
        //menu salvar
        mainFrame.setSaveMenuItemActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        //menu sobre
        mainFrame.setAboutMenuItemActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AboutFrame aboutFrame = new AboutFrame();
            }
        });
        //menu instruções
        mainFrame.setInstructionMenuItemActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new File("manual.pdf");
                try {
                    java.awt.Desktop.getDesktop().open(file);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Manual não emcontrado!");
                }
            }
        });
    }//fim do método start

    /**
     * Fecha view
     *
     * @return <code>Integer</cdoe> com resposta do usuário
     */
    private int close() {
        if (mainFrame.getWatcher()) {
            int response = JOptionPane.showConfirmDialog(null, "Deseja salvar o documento antes de fechar? ", "Atenção!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (response == 0) {
                save();
                mainFrame.close();
                mainFrame.setWatcher(false);
            } else if (response == 1) {
                mainFrame.close();
                mainFrame.setWatcher(false);
            }
            return response;
        } else {
            mainFrame.close();
        }
        return 0;
    }//fim do método close

    /**
     * Aciona o fileshosser
     */
    private void add() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setMultiSelectionEnabled(true);
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
        //cria componente para visualização de imagens
        ImagePreview ip = new ImagePreview(fileChooser);
        fileChooser.setAccessory(ip.getPreview());

        //inicia filechosser e analisa resposta
        int response = fileChooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            //define sentilela como ativa
            mainFrame.setWatcher(true);
            //obtêm arquivos selecionados
            File[] files = fileChooser.getSelectedFiles();

            if (files != null) {
                //salva ultimo diretório
                lastAddDirectory = files[0].getAbsolutePath();
                //adiciona arquivos a lista
                List<String> filesPath = new ArrayList();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
                        //se for um diretório varre-o em busca de imagens
                        File[] directoryFiles = files[i].listFiles(new java.io.FileFilter() {
                            @Override
                            public boolean accept(File f) {
                                if (f.isDirectory()) {
                                    return false;
                                }
                                String extension = null;
                                try {
                                    extension = f.getName().substring(f.getName().lastIndexOf('.') + 1);
                                } catch (Exception ex) {
                                    extension = null;
                                }
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
                        });
                        for (int j = 0; j < directoryFiles.length; j++) {
                            filesPath.add(directoryFiles[j].getPath());
                        }
                    } else {
                        filesPath.add(files[i].getPath());
                    }
                }
                //passa as wallpapers para a view
                mainFrame.addWallpapers(filesPath);
            }
        }
    }//fim do método add

    /**
     * Abre arquivo XML e o analisa
     */
    private void open() {
        //reinicia view
        int r = close();
        if (r != 2) {
            start();

            //cria e inicia filechosser
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setAcceptAllFileFilterUsed(false);
            //cria filtro para somente arquivos xml
            fileChooser.addChoosableFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {

                    if (f.isDirectory()) {
                        return true;
                    }
                    String extension = null;
                    try {
                        extension = f.getName().substring(f.getName().lastIndexOf('.') + 1);
                    } catch (Exception ex) {
                        extension = null;
                    }
                    if (extension != null) {
                        if (extension.equals("xml")) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                    return false;
                }

                //descrição do filtro
                @Override
                public String getDescription() {
                    return "Arquivos XML";
                }
            });

            //inicia folechosser e importa XML
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                //importa e passa dados para a view
                ImportXML importXML = new ImportXML(file);
                try {
                    importXML.parseXML();
                    mainFrame.setDuration((int) importXML.getDuration());
                    mainFrame.setWallpapers(importXML.getFiles());
                    pathFile = file.getPath();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Não foi possivel abrir!" + ex.getMessage(), "ERRO!", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        }
    }//fim do método open

    /**
     * Salva as wallpapers em um arquivo XML
     */
    private void save() {
        if (pathFile != null) {
            try {
                ExportXML exportXML = new ExportXML(pathFile);
                exportXML.createXML(mainFrame.getWallpapers(), mainFrame.getDuration());
                mainFrame.setWatcher(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Não foi possivel salvar!" + ex.getMessage(), "ERRO!", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            saveAs();
        }
    }//fim do método save

    /**
     * Salva os dados em um arquivo novo
     */
    private void saveAs() {

        //cria filechosser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //abre no iltimo diretório
        if (lastSave != null) {
            fileChooser.setCurrentDirectory(new File(lastSave));
        }
        //inicia filechosser e exporta wallpapers
        int response = fileChooser.showSaveDialog(fileChooser);
        if (response == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            lastSave = file.getAbsolutePath();
            try {
                //testa se arquivo termina com .xml
                String extension = file.getName().substring(file.getName().lastIndexOf('.') + 1);
                ExportXML exportXML;
                if (extension.equals("xml")) {
                    exportXML = new ExportXML(file.getPath());
                } else {
                    exportXML = new ExportXML(file.getPath() + ".xml");
                }
                //exporta dados e duração
                exportXML.createXML(mainFrame.getWallpapers(), mainFrame.getDuration());
                mainFrame.setWatcher(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Não foi possivel salvar!" + ex.getMessage(), "ERRO!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//fim do método saveAs
}//fim da classe MainController

