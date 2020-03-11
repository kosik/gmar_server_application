/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package by.gmar.utilities;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;

/**
 *
 * @author s.kosik
 */
public class DataWriterUtile {

    private String filePathName;
    private PrintWriter printWriter;
    private FileOutputStream fileOutputStream;

    public FileOutputStream createFileOutputStream(final String filePath, final String fileName) throws Exception {
        if (null == filePath || null == fileName) {
            return null;
        }

        //TODO: to check for end slash
        filePathName = filePath + fileName;
        fileOutputStream = new FileOutputStream(filePathName);

        return fileOutputStream;
    }

    public PrintWriter createPrintWriter(final String filePath, final String fileName) throws Exception {
        if (null == filePath || null == fileName) {
            return null;
        }

        //TODO: to check for end slash
        filePathName = filePath + fileName;
        File file = new File(filePath + fileName);
        this.printWriter = new PrintWriter(file);
        return printWriter;
    }

    public String getFilePathName() {
        return filePathName;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    /**
     * this is possible to override
     *
     * @param data
     * @return
     */
    public boolean saveData(final String data) {
        getPrintWriter().print(data);
        getPrintWriter().flush();
        getPrintWriter().close();
        return true;
    }

    public FileOutputStream getFileOutputStream() {
        return fileOutputStream;
    }

    /**
     * Copies file contents from source to destination. Makes up for the lack of
     * file copying utilities in Java
     */
    public static boolean copy(File source, File destination) {
        BufferedInputStream fin = null;
        BufferedOutputStream fout = null;
        try {
            int bufSize = 8 * 1024;
            fin = new BufferedInputStream(new FileInputStream(source), bufSize);
            fout = new BufferedOutputStream(new FileOutputStream(destination), bufSize);
            copyPipe(fin, fout, bufSize);
        } catch (IOException ioex) {
            return false;
        } catch (SecurityException sx) {
            return false;
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException cioex) {
                }
            }
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException cioex) {
                }
            }
        }
        return true;
    }

    /**
     * Save URL contents to a file.
     */
    public static boolean copy(URL from, File to) {
        BufferedInputStream urlin = null;
        BufferedOutputStream fout = null;
        try {
            int bufSize = 8 * 1024;
            urlin = new BufferedInputStream(
                    from.openConnection().getInputStream(),
                    bufSize);
            fout = new BufferedOutputStream(new FileOutputStream(to), bufSize);
            copyPipe(urlin, fout, bufSize);
        } catch (IOException ioex) {
            return false;
        } catch (SecurityException sx) {
            return false;
        } finally {
            if (urlin != null) {
                try {
                    urlin.close();
                } catch (IOException cioex) {
                }
            }
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException cioex) {
                }
            }
        }
        return true;
    }

    /**
     * Reads data from the input and writes it to the output, until the end of
     * the input stream.
     *
     * @param in
     * @param out
     * @param bufSizeHint
     * @throws IOException
     */
    public static void copyPipe(InputStream in, OutputStream out, int bufSizeHint)
            throws IOException {
        int read = -1;
        byte[] buf = new byte[bufSizeHint];
        while ((read = in.read(buf, 0, bufSizeHint)) >= 0) {
            out.write(buf, 0, read);
        }
        out.flush();
    }

}
