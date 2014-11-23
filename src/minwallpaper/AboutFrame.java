/*-
 * Classname:             AboutFrame.java
 *
 * Version information:   0.3
 *
 * Date:                  23/05/2013 - 15:02:15
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Frame responsável por mostrar algumas informações sobre o programa
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class AboutFrame {

    //janela principal
    private JFrame frame;
    //àrea de texto em que as informações são adicionadas
    private JTextArea textArea;
    //largura da tela
    private int x;
    //altura da tela
    private int y;
    //título da tela
    private String title;

    /**
     * Construtor sem parâmetros
     */
    public AboutFrame() {
        //define título
        title = "Sobre";
        //cria visão
        createView();
        //preenche visão
        fillsView();
    }//fim do construtor

    /**
     * Cria visão
     */
    private void createView() {
        //inicializa componentes
        frame = new JFrame(title);
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //define opções de layout
        frame.setLayout(null);
        textArea.setEditable(false);
        textArea.setLineWrap(true);//quebra de linha
        textArea.setWrapStyleWord(true);//impede separação de palavras

        //define posição e tamanho
        takeScreenSize();
        frame.setBounds((x - 500) / 2, (y - 350) / 2, 500, 350);
        scrollPane.setBounds(10, 10, 460, 300);

        //configurações gráficas 
        textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        textArea.setBackground(Color.WHITE);

        //junção de componentes
        scrollPane.setViewportView(textArea);
        frame.add(scrollPane);

        //configurações finais
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //adiciona evento ao fechar a janela
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });
    }//fim do método createView

    /**
     * Busca tamanho da tela
     */
    public void takeScreenSize() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        x = (int) dim.getWidth();//largura
        y = (int) dim.getHeight();//altura
    }//fim do método takeScreenSize

    /**
     * Preenche visão com dados
     */
    private void fillsView() {
        textArea.append("Criador de Slides se Wallpapers para Linux Mint - CSWM\n");
        textArea.append("Versão: 0.2\n");
        textArea.append("Data:15/06/2013 \n");
        textArea.append("\n");
        textArea.append("O CSWM é um programa para criar slides para trocar automaticamente as wallpapers na distribuição Linux Mint.");
        textArea.append("\n");
        textArea.append("Distribuições testadas: Linux Mint 15 (MATE)\n\n");
        textArea.append("Direitos Autorais Reservados (c) 2013 Jonas Mayer\n");
        textArea.append(" CSWM é um software livre; você pode redistribui-lo e/ou "
                + " modifica-lo dentro dos termos da Licença Pública Geral GNU como "
                + " publicada pela Fundação do Software Livre (FSF); na versão 2 da "
                + " Licença, ou qualquer versão.");
        textArea.append("\n\nEmail: jmayer13@hotmail.com");

    }//fim do método fillsView

    //método para testes
    public static void main(String[] args) {
        new AboutFrame();
    }
}//fim da classe AboutFrame

