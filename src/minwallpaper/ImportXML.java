/*-
 * Classname:             ImportXML.java
 *
 * Version information:   0.3
 *
 * Date:                  06/06/2013 - 14:54:05
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * Importa o arquivo XML
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class ImportXML {

    //lista de imagens
    private List<String> files;
    //arquivo xml
    private File file;
    //duração do slide
    private double duration;

    /**
     * Construtor com arquivo
     *
     * @param file arquivo xml
     */
    public ImportXML(File file) {
        this.file = file;
    }//fim do construtor

    /**
     * Analisa o arquivo XML
     *
     * @throws JDOMException
     * @throws IOException
     */
    public void parseXML() throws JDOMException, IOException {
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(file);
        Element root = document.getRootElement();
        List<Element> statics = root.getChildren("static");
        duration = Double.valueOf(statics.get(0).getChildText("duration"));
        files = new ArrayList();

        for (int i = 0; i < statics.size(); i++) {
            files.add(statics.get(i).getChildText("file"));
        }
    }//fim do método parseXML

    /**
     * Obtêm a lista dos caminhos das imagens
     *
     * @return <code>List</code> com caminhos das imagens
     */
    public List<String> getFiles() {
        return files;
    }//fim do método getFiles

    /**
     * ObTêm duração do slide
     *
     * @return <code>Double</code> duração
     */
    public double getDuration() {
        return duration;
    }//fim do método getDuration
}//fim da classe ImportXML

