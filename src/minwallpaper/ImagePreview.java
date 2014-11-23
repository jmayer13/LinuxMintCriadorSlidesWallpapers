/*-
 * Classname:             ImagePreview.java
 *
 * Version information:   0.4
 *
 * Date:                  07/06/2013 - 14:04:29
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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

/**
 * Cria componente para visualizar as wallpapers
 *
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class ImagePreview {

    //miniatura do wallpaper
    private ImageIcon imageIcon = null;
    //arquivo
    private File file = null;
    //componente de visualização
    private JComponent preview;

    /**
     * Construtor com fileChosser
     *
     * @param fc JFileChosser
     */
    public ImagePreview(JFileChooser fc) {
        //inicializa componente
        preview = new JComponent() {
            //sobrescreve o método para adicionar imageIcon
            @Override
            protected void paintComponent(Graphics graphics) {
                if (imageIcon != null) {
                    //define e centraliza imagem
                    int x = (getWidth() - imageIcon.getIconWidth()) / 2;
                    int y = (getHeight() - imageIcon.getIconHeight()) / 2;
                    imageIcon.paintIcon(this, graphics, x, y);
                }
            }
        };

        //pega tamanho da tela
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) dim.getWidth();//largura
        int y = (int) dim.getHeight();//altura
        //define o tamanho da tela
        preview.setPreferredSize(new Dimension((int) (x * 0.6) - 250, (int) ((y * 0.8) - 150)));

        //adiciona ouvinte para mudança no filechosser
        fc.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                //atualiza componente
                update(evt);
            }
        });
    }//fim do construtor

    /**
     * Atualiza imagem do componente
     *
     * @param e PropertyChangeEvent
     */
    public void update(PropertyChangeEvent e) {

        //sentrinela para update
        boolean update = false;
        String property = e.getPropertyName();
        //se o diretótio for mudado
        if (property.equals(JFileChooser.DIRECTORY_CHANGED_PROPERTY)) {
            //limpa imagem
            file = null;
            update = true;
            //se arquivos forem selecionados
        } else if (property.equals(JFileChooser.SELECTED_FILES_CHANGED_PROPERTY)) {
            //pega arquivos selecionados
            File[] files = (File[]) e.getNewValue();
            //se for um único arquivo mostra a miniatura
            if (files != null) {
                if (files.length > 1) {
                    file = null;
                } else {
                    file = files[0];
                }
            }
            update = true;
        }

        //atualiza a imagem do componente
        if (update) {
            imageIcon = null;
            if (preview.isShowing()) {
                //se for um diretório ou nulo não atualiza
                if (file == null || file.isDirectory()) {
                    imageIcon = null;

                } else {
                    //carrega imagem do arquivo
                    ImageIcon tmpIcon = new ImageIcon(file.getPath());
                    //calcula o tamanho da imagem
                    double iconWidth = tmpIcon.getIconWidth();
                    double iconHeight = tmpIcon.getIconHeight();
                    double width = preview.getWidth();
                    double height = preview.getHeight();
                    if (iconWidth > preview.getWidth() || iconHeight > preview.getHeight()) {
                        if ((iconWidth - preview.getWidth()) / preview.getWidth() >= (iconHeight - preview.getHeight()) / preview.getHeight()) {
                            double te = preview.getWidth() * 100 / iconWidth;
                            height = iconHeight * te / 100;
                            assert (height > preview.getHeight()) : "Falha no calculo de renderização de imagens";
                        } else {
                            double te = preview.getHeight() * 100 / iconHeight;
                            width = iconWidth * te / 100;
                            assert (width > preview.getWidth()) : "Falha no calculo de renderização de imagens";
                        }
                        //redefine a imagem com o algoritmo eoo manaho especificados
                        imageIcon = new ImageIcon(tmpIcon.getImage().getScaledInstance((int) width, (int) height, Image.SCALE_AREA_AVERAGING));
                    } else {
                        imageIcon = tmpIcon;
                    }
                }
                preview.repaint();
            }
        }
    }//fim do método update

    /**
     * ObTêm componente para visualização
     *
     * @return <code>JComponent</code>
     */
    public JComponent getPreview() {
        return preview;
    }//fim do método getPreview
}//fim da classe ImagePreview

