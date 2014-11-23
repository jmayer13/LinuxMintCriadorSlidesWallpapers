
/*-
 * Classname:             NumberDocumentFilter.java
 *
 * Version information:   1.0
 *
 * Date:                  15/02/2013 - 15:14:16
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

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Filtro numérico
 *
 * @see javax.swing.text.DocumentFilter;
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class NumberDocumentFilter extends DocumentFilter {

    /**
     * Construtor vazio
     */
    public NumberDocumentFilter() {
    }//fim do construtor

    /**
     * Chamado antes da inserção do texto no documento
     *
     * @param filterBypass FilterBypass que podem ser usados para transformar o
     * Document
     * @param offset o deslocamento > = 0 no documento para inserir o conteúdo
     * @param string a string para inserção
     * @param attr os atributos para associar com o conteúdo inserido.
     * @throws BadLocationException a posição de inserção dada não é uma posição
     * válida dentro do documento
     */
    @Override
    public void insertString(FilterBypass filterBypass, int offset, String string, AttributeSet attr)
            throws BadLocationException {

        for (int i = 0; i < string.length(); i++) {
            //verifica se é letra (se não continua)
            if (Character.isDigit(string.charAt(i)) == false) {
                return;
            }

        }
        filterBypass.insertString(offset, string, attr);

    }//fim do método insertString

    /**
     * Chamado antes de substituir uma região de texto no documento
     *
     * @param fb FilterBypass que podem ser usados para transformar o Document
     * @param offset Localização no Documento
     * @param length Comprimento de texto para excluir
     * @param string Texto para inserir, nulo indica nenhum texto para inserir
     * @param attrs indicando atributos do texto inserido,
     * @throws BadLocationException a posição de inserção dada não é uma posição
     * válida dentro do documento
     */
    @Override
    public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs)
            throws BadLocationException {
        for (int i = 0; i < string.length(); i++) {
            if (Character.isDigit(string.charAt(i)) == false) {
                return;
            }
        }

        fb.replace(offset, length, string, attrs);
    }
}//fim da classe NumberDocumentFilter

