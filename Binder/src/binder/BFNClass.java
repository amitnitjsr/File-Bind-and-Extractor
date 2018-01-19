/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package binder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.JOptionPane;

/**
 *
 * @author ACER
 */
public class BFNClass 
{
    int prog=0, max=0;
    public void bind(String infol, String outfol, String Cover, boolean con)
    {
        try {
            File f=new File(infol);
            int l=f.getParent().length();
            File zipout=new File(outfol+"\\"+f.getName()+".zip");
            zipout.createNewFile();
            File f1=new File(Cover);
            File f2=comp(f, zipout, l);
            if(f2.exists())
            {
                concat(f1,f2);
                f2.delete();
                if(con)
                {
                    del(f);
                    f.delete();
                }
            JOptionPane.showMessageDialog(new MScreen(), "Files Binded");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(new MScreen(), "UNABLE TO WRITE\nMake sure the disk is not full or write protected.", "Error", 0);
        }
        catch (NullPointerException ex){
            JOptionPane.showMessageDialog(new MScreen(), "FILES NOT FOUND", "Error", 0);
        }
        
    }
    
    private File comp(File F, File zout, int l)
    {   
        FileOutputStream fos=null;
        try {
            
            fos = new FileOutputStream(zout);
            try (ZipOutputStream zos = new ZipOutputStream(fos)) {
                icomp(zos, F, zout, l);
                zos.finish();
            }
            fos.close();
            
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(new MScreen(), "FILES NOT FOUND", "Error", 0);
        } catch (IOException ex) {
           JOptionPane.showMessageDialog(new MScreen(), "UNABLE TO WRITE.\nMake sure the disk is not full or write protected.", "Error", 0);
        }
        finally{return zout;}
    }
    
    
    
   private void icomp( ZipOutputStream zos, File F, File zout, int l)
    {
        int bl=5*1024;
        byte[] buf=new byte[bl];
        File[] Fcont=F.listFiles();
        
        
        for(int i=0;i<Fcont.length;i++)
        {
            if(Fcont[i].isDirectory())
            {
                icomp(zos, Fcont[i], zout, l);
            }
            else
            {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(Fcont[i]);
                    ZipEntry ze=new ZipEntry(Fcont[i].getPath().substring(l));
                    System.out.println(Fcont[i].toString());
                    
                    
                    
                    System.out.println(Fcont[i].getPath());
                    System.out.println(Fcont[i].getParent());
                    zos.putNextEntry(ze);
                    int length;
                    while ((length = fis.read(buf)) >= 0)
                    {
                        zos.write(buf, 0, length);
                    }
                    fis.close();
                    zos.closeEntry();
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(new MScreen(), "FILES NOT FOUND", "Error", 0);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(new MScreen(), "UNABLE TO WRITE.\nMake sure the disk is not full or write protected.", "Error", 0);
                } 
            }
        }
    }

    private void concat(File f1, File f2)
    {
        FileInputStream fis1 = null;
        try {
            File ff=new File(f2.getParent()+"\\"+f1.getName());
            fis1 = new FileInputStream(f1);
            FileInputStream fis2=new FileInputStream(f2);
            FileOutputStream fos=new FileOutputStream(ff);
            byte[] buffer=new byte[5*1024];
            while(fis1.read(buffer)!=-1)
            {
                fos.write(buffer);
            }
            fis1.close();
            while(fis2.read(buffer)!=-1)
            {
                fos.write(buffer);
            }
            fis2.close();
            fos.close();
           } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(new MScreen(), "FILES NOT FOUND", "Error", 0);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(new MScreen(), "UNABLE TO WRITE.\nMake sure the disk is not full or write protected.", "Error", 0);
        }
    }
    
    
    private void del(File inf)
    {
        File[] fd=inf.listFiles();
        for(int i=0;i<fd.length;i++)
        {
            if(fd[i].isDirectory())
            {
                del(fd[i]);
                fd[i].delete();
            }
            else
            {
                fd[i].delete();
            }
        }
    }
}