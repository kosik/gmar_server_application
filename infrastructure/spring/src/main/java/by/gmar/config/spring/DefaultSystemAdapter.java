package by.gmar.config.spring;



/**
 *
 * @author s.kosik
 */
public class DefaultSystemAdapter implements SystemAdapter {

    @Override
    public String getenv(final String name) {
        return System.getenv(name);
    }

}
