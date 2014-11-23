/*-
 * Classname:             ExportXML.java
 *
 * Version information:   0.3
 *
 * Date:                  06/06/2013 - 14:54:18
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

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

/**
 * Exporta dados para um arquivo XML usando JDOM
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class ExportXML {

    //stram
    private FileWriter fileWriter;

    /**
     * Contrutor com caminho do arquivo
     *
     * @param path
     * @throws IOException
     */
    public ExportXML(String path) throws IOException {
        fileWriter = new FileWriter(path);
    }//fim do construtor

    /**
     * Cria XML
     *
     * @param wallpapers caminhos das imagens
     * @param duration duração do slide
     */
    public void createXML(List<String> wallpapers, double duration) {

        //elemento root
        Element root = new Element("background");
        Document document = new Document(root);

        //inicio
        Element starttime = new Element("starttime");
        Element year = new Element("year");
        year.setText("2009");
        Element month = new Element("month");
        month.setText("08");
        Element day = new Element("day");
        day.setText("04");
        Element hour = new Element("hour");
        hour.setText("00");
        Element minute = new Element("minute");
        minute.setText("00");
        Element second = new Element("second");
        second.setText("00");
        starttime.addContent(year);
        starttime.addContent(month);
        starttime.addContent(day);
        starttime.addContent(hour);
        starttime.addContent(minute);
        starttime.addContent(second);
        root.addContent(starttime);

        //definição das wallpapers
        for (int i = 0; i < wallpapers.size(); i++) {
            Element staticElement = new Element("static");
            Element durationElement = new Element("duration");
            durationElement.setText("" + duration);
            Element fileElement = new Element("file");
            fileElement.setText(wallpapers.get(i));
            staticElement.addContent(durationElement);
            staticElement.addContent(fileElement);

            //transição
            Element transition = new Element("transition");
            Element durationTransition = new Element("duration");
            durationTransition.setText("5.0");
            Element from = new Element("from");
            from.setText(wallpapers.get(i));
            Element to = new Element("to");
            if (i == wallpapers.size() - 1) {
                to.setText(wallpapers.get(0));
            } else {
                to.setText(wallpapers.get(i + 1));
            }
            transition.addContent(durationTransition);
            transition.addContent(from);
            transition.addContent(to);

            root.addContent(staticElement);
            root.addContent(transition);
        }
        XMLOutputter xout = new XMLOutputter();
        //remove declarações de encoding e versão
        xout.getFormat().setOmitEncoding(true);
        xout.getFormat().setOmitDeclaration(true);
        //passa o stream e o documento
        try {
            xout.output(document, fileWriter);
            //fecha
            xout.clone();
            fileWriter.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro: IOException" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//fim do método createXML
}//fim da classe ExportXML

