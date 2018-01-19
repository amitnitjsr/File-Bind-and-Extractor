/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package binder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import javax.swing.JOptionPane;

/**
 *
 * @author ACER
 */
class EXFNClass 
{
    public void extract(String bfpath, String extpath, boolean con)
    {
        System.out.println("Extract");
        File bf=new File(bfpath);
        int flag=iextract(bf, extpath);
        if(con&&flag==0)
        {
            bf.delete();
            JOptionPane.showMessageDialog(new MScreen(), "Files Extracted");
        }
        if((!con)&&(flag==0))
        {
            JOptionPane.showMessageDialog(new MScreen(), "Files Extracted");
        }
    }

    private int iextract(File bf, String extpath) 
    {
        try {
            int bl=1024;
            byte[] buf=new byte[bl];
            System.out.println("iExtract");
            ZipFile zf1=new ZipFile(bf);
            Enumeration <? extends ZipEntry> ent1= zf1.entries();
            while(ent1.hasMoreElements())
            {
                ZipEntry ze=ent1.nextElement();
                InputStream is=zf1.getInputStream(ze);
                File ftemp=new File(extpath+"\\"+ze.toString());
                
                if(!(ftemp.getParentFile().exists()))
                {
                    ftemp.getParentFile().mkdirs();
                }
                ftemp.createNewFile();
                FileOutputStream fos=new FileOutputStream(ftemp);
                int length;
                while ((length = is.read(buf)) >= 0)
                {
                    fos.write(buf, 0, length);
                }
                fos.close();
                is.close();
            }
            zf1.close();
        } catch (ZipException ex) {
            JOptionPane.showMessageDialog(new MScreen(), "Selected file is not a binded file.", "Error", 0);
            return 1;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(new MScreen(), "UNABLE TO WRITE.\nMake sure the disk is not full or write protected.", "Error", 0);
            return 1;
        }
        return 0;
    }
}