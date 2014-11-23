/*-
 * Classname:             MainFrame.java
 *
 * Version information:   1.0
 *
 * Date:                  06/06/2013 - 14:53:00
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;

/**
 * Visão principal
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class MainFrame {

    //janela principal
    private JFrame frame;
    //barra de menu
    private JMenuBar menuBar;
    //menu arquivo
    private JMenu fileMenu;
    //menu ajuda
    private JMenu helpMenu;
    //item novo
    private JMenuItem newMenuItem;
    //item abrir
    private JMenuItem openMenuItem;
    //item save
    private JMenuItem saveMenuItem;
    //item salvar como
    private JMenuItem saveAsMenuItem;
    //item fechar
    private JMenuItem closeMenuItem;
    //item sobre
    private JMenuItem aboutMenuItem;
    //item manual
    private JMenuItem instructionMenuItem;
    //label wallpapaers
    private JLabel wallpapersLabel;
    //scrollpane para a lista
    private JScrollPane scrollPane;
    //lista de wallpapaers
    private JList wallpapersList;
    //botão adicionar
    private JButton addButton;
    //botão remover
    private JButton removeButton;
    //botão limpar
    private JButton cleanButton;
    //label duração
    private JLabel durationLabel;
    //campo de texto para o intervalo inicial ou duração
    private JTextField timeField;
    //unidade de tempo da campo da duração/intervalo inicial
    private JComboBox unitField;
    //caixa de checagem para randomizar o tempo entre os intervalos
    private JCheckBox randomCheck;
    //separador
    private JLabel untilLabel;
    //rótulo interativo para a caixa de checagem randomica
    private JLabel randomLabel;
    //campo com intervalo máximo
    private JTextField maxTimeField;
    //campo para unidade do intervalo máximo
    private JComboBox maxUnitField;
    //lista de caminhos para as imagens
    private List<String> wallpapers;
    //botão fechar
    private JButton closeButton;
    //caixa de checagem para abilitar randomização da ordem
    private JCheckBox randomOrderCheck;
    //rótulo interativo para ordem aleatória
    private JLabel randomOrderLabel;
    //label grau
    private JLabel lengthLabel;
    ///grau de pseudo-randomização
    private JComboBox lengthField;
    //largura
    private int x = 0;
    //altura
    private int y = 0;
    //sentinela de alteração
    private boolean watcher = false;
    //valor temporário para checagem de alteração nos campos
    private String tempValue;

    /**
     * Construtor sem parâmetros
     */
    public MainFrame() {
        //inicializa componentes
        frame = new JFrame("Criador de Wallpapers de Slides para o Mint");
        menuBar = new JMenuBar();
        fileMenu = new JMenu("Arquivo");
        helpMenu = new JMenu("Ajuda");
        newMenuItem = new JMenuItem("Novo");
        openMenuItem = new JMenuItem("Abrir...");
        saveMenuItem = new JMenuItem("Salvar");
        saveAsMenuItem = new JMenuItem("Salvar como...");
        closeMenuItem = new JMenuItem("Fechar");
        aboutMenuItem = new JMenuItem("Sobre");
        instructionMenuItem = new JMenuItem("Instruções");
        wallpapersLabel = new JLabel("Wallpapers");
        scrollPane = new JScrollPane();
        wallpapersList = new JList();
        addButton = new JButton("Adicionar");
        removeButton = new JButton("Remover");
        cleanButton = new JButton("Limpar");
        durationLabel = new JLabel("Duração:");
        timeField = new JTextField();
        unitField = new JComboBox(new Object[]{"segundos", "minutos", "horas"});
        randomCheck = new JCheckBox();
        untilLabel = new JLabel("-");
        randomLabel = new JLabel("Randômica");
        maxTimeField = new JTextField();
        maxUnitField = new JComboBox(new Object[]{"segundos", "minutos", "horas"});
        closeButton = new JButton("Fechar");
        randomOrderCheck = new JCheckBox();
        randomOrderLabel = new JLabel("Ordem aleatória");
        lengthLabel = new JLabel("Grau:");
        lengthField = new JComboBox(new Object[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        wallpapers = new ArrayList();

        //configurações iniciais
        frame.setLayout(null);
        unitField.setSelectedIndex(0);
        timeField.setText("5");
        randomOrderCheck.setSelected(true);
        wallpapersLabel.setHorizontalAlignment(JTextField.CENTER);
        randomCheck.setSelected(false);
        maxTimeField.setEnabled(false);
        maxUnitField.setEnabled(false);

        //filtro numérico
        DocumentFilter numberDocumentFilter = new NumberDocumentFilter();
        ((AbstractDocument) timeField.getDocument()).setDocumentFilter(numberDocumentFilter);
        ((AbstractDocument) maxTimeField.getDocument()).setDocumentFilter(numberDocumentFilter);

        //definição de teclas atalho para os menus
        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveAsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        closeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        instructionMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, ActionEvent.SHIFT_MASK));

        //tamanho e localização
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        x = (int) dim.getWidth();//largura
        y = (int) dim.getHeight();//altura
        frame.setBounds((x - 550) / 2, (y - 470) / 2, 550, 470);
        menuBar.setBounds(0, 0, 550, 30);
        wallpapersLabel.setBounds((frame.getWidth() - 150) / 2, 32, 150, 30);
        scrollPane.setBounds(10, 65, 370, 250);
        addButton.setBounds(405, 67, 120, 30);
        removeButton.setBounds(405, 112, 120, 30);
        cleanButton.setBounds(405, 162, 120, 30);
        durationLabel.setBounds(10, 320, 90, 30);
        timeField.setBounds(90, 320, 80, 30);
        unitField.setBounds(175, 320, 120, 30);
        closeButton.setBounds(430, 400, 100, 30);
        randomOrderCheck.setBounds(10, 390, 30, 30);
        randomOrderLabel.setBounds(40, 390, 150, 30);
        randomCheck.setBounds(20, 355, 30, 30);
        randomLabel.setBounds(50, 355, 120, 30);
        untilLabel.setBounds(305, 320, 10, 30);
        maxTimeField.setBounds(320, 320, 80, 30);
        maxUnitField.setBounds(405, 320, 120, 30);
        lengthLabel.setBounds(210, 390, 50, 30);
        lengthField.setBounds(260, 390, 50, 30);

        //fonte
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16);
        Font smallFont = new Font(Font.SANS_SERIF, Font.BOLD, 14);
        fileMenu.setFont(font);
        helpMenu.setFont(font);
        newMenuItem.setFont(font);
        openMenuItem.setFont(font);
        saveMenuItem.setFont(font);
        saveAsMenuItem.setFont(font);
        closeMenuItem.setFont(font);
        wallpapersLabel.setFont(font);
        wallpapersList.setFont(smallFont);
        addButton.setFont(font);
        removeButton.setFont(font);
        cleanButton.setFont(font);
        durationLabel.setFont(font);
        timeField.setFont(font);
        unitField.setFont(smallFont);
        closeButton.setFont(font);
        randomOrderCheck.setFont(font);
        randomOrderLabel.setFont(font);
        aboutMenuItem.setFont(font);
        instructionMenuItem.setFont(font);
        randomLabel.setFont(smallFont);
        maxTimeField.setFont(font);
        maxUnitField.setFont(smallFont);
        lengthLabel.setFont(smallFont);
        lengthField.setFont(smallFont);
        untilLabel.setFont(font);

        //cor
        frame.getContentPane().setBackground(Color.WHITE);
        fileMenu.setForeground(Color.BLACK);
        helpMenu.setForeground(Color.BLACK);
        newMenuItem.setForeground(Color.BLACK);
        openMenuItem.setForeground(Color.BLACK);
        saveMenuItem.setForeground(Color.BLACK);
        saveAsMenuItem.setForeground(Color.BLACK);
        closeMenuItem.setForeground(Color.BLACK);
        aboutMenuItem.setForeground(Color.BLACK);
        instructionMenuItem.setForeground(Color.BLACK);
        wallpapersLabel.setForeground(Color.BLACK);
        scrollPane.setBackground(Color.WHITE);
        wallpapersList.setForeground(Color.BLACK);
        addButton.setForeground(Color.BLACK);
        removeButton.setForeground(Color.BLACK);
        cleanButton.setForeground(Color.BLACK);
        durationLabel.setForeground(Color.BLACK);
        timeField.setForeground(Color.BLACK);
        unitField.setForeground(Color.BLACK);
        unitField.setBackground(Color.WHITE);
        closeButton.setForeground(Color.BLACK);
        randomOrderCheck.setForeground(Color.BLACK);
        randomOrderCheck.setBackground(Color.WHITE);
        randomOrderLabel.setForeground(Color.BLACK);
        randomCheck.setForeground(Color.BLACK);
        randomCheck.setBackground(Color.WHITE);
        randomLabel.setForeground(Color.BLACK);
        maxTimeField.setForeground(Color.BLACK);
        maxUnitField.setForeground(Color.BLACK);
        maxUnitField.setBackground(Color.WHITE);
        lengthLabel.setForeground(Color.BLACK);
        lengthField.setForeground(Color.BLACK);
        lengthField.setBackground(Color.WHITE);
        untilLabel.setForeground(Color.BLACK);

        //junção
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(closeMenuItem);
        helpMenu.add(aboutMenuItem);
        helpMenu.add(instructionMenuItem);
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        frame.add(menuBar);
        frame.add(wallpapersLabel);
        scrollPane.setViewportView(wallpapersList);
        frame.add(scrollPane);
        frame.add(addButton);
        frame.add(removeButton);
        frame.add(cleanButton);
        frame.add(durationLabel);
        frame.add(timeField);
        frame.add(unitField);
        frame.add(closeButton);
        frame.add(randomOrderCheck);
        frame.add(randomOrderLabel);
        frame.add(randomLabel);
        frame.add(randomCheck);
        frame.add(untilLabel);
        frame.add(maxTimeField);
        frame.add(maxUnitField);
        frame.add(lengthLabel);
        frame.add(lengthField);

        //configurações finais
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //label dinamicos
        randomOrderLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                randomOrderCheck.setSelected(!randomOrderCheck.isSelected());
                watcher = true;
                lengthField.setEnabled(randomOrderCheck.isSelected());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                randomOrderLabel.setForeground(Color.BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                randomOrderLabel.setForeground(Color.BLACK);
            }
        });
        randomLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                randomCheck.setSelected(!randomCheck.isSelected());
                watcher = true;
                maxTimeField.setEnabled(randomCheck.isSelected());
                maxUnitField.setEnabled(randomCheck.isSelected());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                randomLabel.setForeground(Color.BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                randomLabel.setForeground(Color.BLACK);
            }
        });

        //eventos para checagem de alterações
        ActionListener watcerListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                watcher = true;
            }
        };
        randomOrderCheck.addActionListener(watcerListener);
        randomCheck.addActionListener(watcerListener);
        lengthField.addActionListener(watcerListener);

        //ativação de componentes dependestes de checkBoxes
        randomCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                maxTimeField.setEnabled(randomCheck.isSelected());
                maxUnitField.setEnabled(randomCheck.isSelected());

            }
        });
        randomOrderCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                lengthField.setEnabled(randomOrderCheck.isSelected());

            }
        });

        //eventos de verificação de alteração nos valores
        FocusListener fl = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                JTextField tf = (JTextField) e.getSource();
                tempValue = tf.getText();
            }

            @Override
            public void focusLost(FocusEvent e) {
                JTextField tf = (JTextField) e.getSource();
                if (!tf.getText().equals(tempValue)) {
                    watcher = true;
                }
            }
        };
        timeField.addFocusListener(fl);
        maxTimeField.addFocusListener(fl);
    }//fim do construtor

    /**
     * Fecha janela
     */
    public void close() {
        frame.dispose();
    }//fim do método close

    /**
     * Define lista de wallpapers
     *
     * @param wallpapers lista de wallpapers
     */
    public void setWallpapers(List wallpapers) {
        this.wallpapers = wallpapers;
        refreshWallpapersList();
    }//fim do método setWallpapers

    /**
     * Adiciona wallpapers a lista
     *
     * @param wallpapers lista a ser adicionada
     */
    public void addWallpapers(List wallpapers) {
        this.wallpapers.addAll(wallpapers);
        refreshWallpapersList();
    }//fim do método addWallpapers

    /**
     * Define evento para o menu novo
     *
     * @param actionListener ouvinte
     */
    public void setNewMenuItemActionListener(ActionListener actionListener) {
        newMenuItem.addActionListener(actionListener);
    }//fim do método setNewMenuItemActionListener

    /**
     * Define evento para o menu abrir
     *
     * @param actionListener ouvinte
     */
    public void setOpenMenuItemActionListener(ActionListener actionListener) {
        openMenuItem.addActionListener(actionListener);
    }//fim do método setOpenMenuItemActionListener

    /**
     * Define evento para o menu salvar
     *
     * @param actionListener ouvinte
     */
    public void setSaveMenuItemActionListener(ActionListener actionListener) {
        saveMenuItem.addActionListener(actionListener);
    }//fim do método setSaveMenuItemActionListener

    /**
     * Define evento para o menu salvar como...
     *
     * @param actionListener ouvinte
     */
    public void setSaveAsMenuItemActionListener(ActionListener actionListener) {
        saveAsMenuItem.addActionListener(actionListener);
    }//fim do método setSaveAsMenuItemActionListener

    /**
     * Define evento para o menu fechar
     *
     * @param actionListener ouvinte
     */
    public void setCloseMenuItemActionListener(ActionListener actionListener) {
        closeMenuItem.addActionListener(actionListener);
    }//fim do método setCloseMenuItemActionListener

    /**
     * Define evento para o botão adicionar
     *
     * @param actionListener ouvinte
     */
    public void setAddButtonActionListener(ActionListener actionListener) {
        addButton.addActionListener(actionListener);
    }//fim do método setAddButtonActionListener

    /**
     * Define evento para o botão remover
     *
     * @param actionListener ouvinte
     */
    public void setRemoveButtonActionListener(ActionListener actionListener) {
        removeButton.addActionListener(actionListener);
    }//fim do método setRemoveButtonActionListener

    /**
     * Define evento para o botão limpar
     *
     * @param actionListener ouvinte
     */
    public void setCleanButtonActionListener(ActionListener actionListener) {
        cleanButton.addActionListener(actionListener);
    }//fim do método setCleanButtonActionListener

    /**
     * Define evento para o botão fechar
     *
     * @param actionListener ouvinte
     */
    public void setCloseButtonActionListener(ActionListener actionListener) {
        closeButton.addActionListener(actionListener);
    }//fim do método setCloseButtonActionListener

    /**
     * Define evento para o fechamento da janela
     *
     * @param windowsListener ouvinte
     */
    public void setCloseWindowListener(WindowListener windowsListener) {
        frame.addWindowListener(windowsListener);
    }//fim do método setCloseWindowListener

    /**
     * Define evento para o menu sobre
     *
     * @param actionListener ouvinte
     */
    public void setAboutMenuItemActionListener(ActionListener actionListener) {
        aboutMenuItem.addActionListener(actionListener);
    }//fim do método setAboutMenuItemActionListener

    /**
     * Define evento para o menu instruções
     *
     * @param actionListener
     */
    public void setInstructionMenuItemActionListener(ActionListener actionListener) {
        instructionMenuItem.addActionListener(actionListener);
    }//fim do método setInstructionMenuItemActionListener

    /**
     * Define estado da sentinela
     *
     * @param watcher estado da sentinela de alteração
     */
    public void setWatcher(boolean watcher) {
        this.watcher = watcher;
    }//fim do método setWatcher

    /**
     * Obtêm estado da sentinela
     *
     * @return <code>Boolean</code> com estado da sentinela de alteração
     */
    public boolean getWatcher() {
        return watcher;
    }//fim do método getWatvher

    /**
     * Atualiza lista de wallpapers
     */
    private void refreshWallpapersList() {

        //converte para vetor
        Vector v = new Vector();
        for (int i = 0; i < wallpapers.size(); i++) {
            String path = wallpapers.get(i);

            v.add(path);
        }
        //atualiza lista
        wallpapersList.setListData(v);
    }//fim do método refreshWallpapersList

    /**
     * Obtêm a wallpaper selecionada na lista
     *
     * @return <code>String</code> wallpaper selecionada
     */
    public String getSelectedWallpaper() {
        return (String) wallpapersList.getSelectedValue();
    }//fim do método getSelectedWallpaper

    /**
     * Remove wallpaper da lista
     *
     * @param wallpaper
     */
    public void removeWallpaper(String wallpaper) {
        //remove
        wallpapers.remove(wallpaper);
        //atualiza
        refreshWallpapersList();
    }//fim do método removeWallpaper

    /**
     * Obtêm a duração
     *
     * @return <code>Double</code> duração
     */
    public double getDuration() {
        //calcula duração em segundos
        double duration = 5;
        try {
            duration = Double.valueOf(timeField.getText());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //se for minutos
        if (unitField.getSelectedIndex() == 1) {
            duration = duration * 60;
            ///horas
        } else if (unitField.getSelectedIndex() == 2) {
            duration = duration * 3600;
        }
        //se for randomica seleciona um valor em segundos entre o intervalo
        if (randomCheck.isSelected()) {
            Random random = new Random();
            double maxDuration = 5;
            try {
                maxDuration = Double.valueOf(maxTimeField.getText());
            } catch (Exception ex) {
                ex.printStackTrace();
                return 0;
            }
            if (maxUnitField.getSelectedIndex() == 1) {
                maxDuration = maxDuration * 60;
            } else if (maxUnitField.getSelectedIndex() == 2) {
                maxDuration = maxDuration * 3600;
            }
            duration = random.nextInt((int) (duration + maxDuration)) - duration;
        }
        return duration;
    }//fim do método getDuration

    /**
     * Obtêm lista de wallpapers
     *
     * @return <code></code>
     */
    public List<String> getWallpapers() {
        //se ordem randomica estiver ativada randomiza
        if (randomOrderCheck.isSelected()) {
            Collections.shuffle(wallpapers);

            //se o grau for maior que um randomiza ordem e as adiciona
            if (lengthField.getSelectedIndex() > 0) {
                int cicles = lengthField.getSelectedIndex();
                List<String> uniqueWallpapers = new ArrayList();
                uniqueWallpapers.addAll(wallpapers);

                for (int i = 0; i <= cicles; i++) {
                    Collections.shuffle(uniqueWallpapers);
                    wallpapers.addAll(uniqueWallpapers);
                }
            }
        }
        return wallpapers;
    }//fim do método getWallpapers

    /**
     * Define duração do slide
     *
     * @param duration duração
     */
    public void setDuration(int duration) {

        //calcula formato apropriado
        if (duration % 3600 == 0) {
            timeField.setText("" + duration / 3600);
            unitField.setSelectedIndex(2);
        } else if (duration % 60 == 0) {
            timeField.setText("" + duration / 60);
            unitField.setSelectedIndex(1);
        } else {
            timeField.setText(String.valueOf(duration));
            unitField.setSelectedIndex(0);
        }
    }//fim do método setDuration

    //método para testes
    public static void main(String[] args) throws IOException {

        MainFrame mainFrame = new MainFrame();
        List<String> list = new ArrayList();
        for (int i = 0; i < 100; i++) {
            list.add("teste");
        }
        mainFrame.setWallpapers(list);
    }
}//fim da classe MainFrame

