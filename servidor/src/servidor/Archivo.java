/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Anny Chacon
 */
public class Archivo extends JFileChooser{
    
    public Archivo(){
        setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("JPG, PNG, GIF & BMP", "jpg","png","gif","bmp");
        setFileFilter(filtro);
        setVisible(true);
        setLocation(300, 300);
        setSize(500,300);
    }
    public File buscarArchivo(){
        int x = showOpenDialog(this);
        if(x == APPROVE_OPTION){
            return getSelectedFile();
        }
        return null;
    }
}
