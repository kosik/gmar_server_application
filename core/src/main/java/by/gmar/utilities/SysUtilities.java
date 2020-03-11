package by.gmar.utilities;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtilsBean2;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaBeanPropertyMapDecorator;
import org.apache.commons.beanutils.LazyDynaBean;
import org.springframework.util.StringUtils;

/**
 *
 * @author s.kosik
 */
public class SysUtilities {

    public static boolean validatePhone(String number) {
        if (number != null && !number.matches("\\d{12}")) {
            return false;
        }
        return true;
    }

    public static List<String> commaSeparate(String string) {
        return Arrays.asList(StringUtils.tokenizeToStringArray(string, ","));
    }

    public static Map<String, Object> copyData(DynaBean dynaWrapper, String... fields) {
        DynaBean dynaBean = new LazyDynaBean();
        for(String item : fields){
            Object value = dynaWrapper.get(item);
            dynaBean.set(item, value);
        }
        
        Map<String, Object> introspectedResult = new DynaBeanPropertyMapDecorator(dynaBean, false);
        
        return introspectedResult;
    }
    
    public static Map<String, String> convertBeanFields(Map<String, Object> income) throws ConversionException, Exception{
        Map<String, String> result = new HashMap<>();
        ConvertUtilsBean2 converter = new ConvertUtilsBean2();
        
        for(String item : income.keySet()){
            Object data = income.get(item);
            if(null != data){
                String convertedData = (String)converter.convert(data, String.class);
                result.put(item, convertedData);         
            }
        }
        
        return result; 
    }
    
            /**
         * copies properties from one object to another
         *
         * @param src
         *            the source object
         * @param dest
         *            the destination object
         * @param properties
         *            a list of property names that are to be copied. Each value has
         *            the format "srcProperty destProperty". For example,
         *            "name fullName" indicates that you want to copy the src.name
         *            value to dest.fullName. If both the srcProperty and
         *            destProperty property have the same name, you can omit the
         *            destProperty. For example, "name" indicates that you want to
         *            copy src.name to dest.name.
         *
         * @throws IllegalAccessException
         * @throws InvocationTargetException
         * @throws NoSuchMethodException
         */
        public static void copyProperties(Object src, Object dest,
                        String... properties) throws IllegalAccessException,
                        InvocationTargetException, NoSuchMethodException {
                for (String property : properties) {
                        String[] arr = property.split(" ");
                        String srcProperty;
                        String destProperty;
                        if (arr.length == 2) {
                                srcProperty = arr[0];
                                destProperty = arr[1];
                        } else {
                                srcProperty = property;
                                destProperty = property;
                        }
                        BeanUtils.setProperty(dest, destProperty, BeanUtils.getProperty(
                                        src, srcProperty));
                }
        }
    
    
    public static URL resolveUrl(final String pOriginalUrl) {
        final ClassLoader lClassLoader = Thread.currentThread().getContextClassLoader();
        URL lActualUrl = lClassLoader.getResource(pOriginalUrl);
        if (null != lActualUrl) {
            return lActualUrl;
        } else {
            try {
                lActualUrl = new URL(pOriginalUrl);
                return lActualUrl;
            } catch (final MalformedURLException exOnResolve) {
                try {
                    final File lFile = new File(pOriginalUrl);
                    lActualUrl = lFile.toURL();
                    if (lFile.exists() && lFile.isFile() && lFile.canRead()) {
                        return lActualUrl;
                    } else {
                        return null;
                    }
                } catch (final MalformedURLException exOnResolveFile) {
                    return null;
                }
            }
        }
    }

    public static Properties loadProperties(final String fileName) throws Exception {
        Properties props = null;
        URL repositoryFileURL = SysUtilities.resolveUrl(fileName);

        if (null == repositoryFileURL) {
            throw new Exception("The repository properties does not found. Possible application was started in wrong way");
        } else {
            props = new Properties();
            props.load(repositoryFileURL.openStream());
        }

        return props;
    }

    public static boolean mkDir(final String path, final String dirName) {
        //TODO: chek for slashes
        if (null == path || null == dirName) {
            throw new RuntimeException("The input data is not actual");
        }
        File dir = new File(path + dirName);
        return dir.mkdir();

    }
    
    public boolean copy(File source, File destination) {
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
	   * Reads data from the input and writes it to the output, until the end of the input
	   * stream.
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
              out.close();
	  }
}
