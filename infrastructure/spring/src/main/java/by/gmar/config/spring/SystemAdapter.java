package by.gmar.config.spring;

/**
 * An adapter to JDK's {@link System} class to enforce clean boundaries with third-party APIs.
 *
 * @author s.kosik
 */
public interface SystemAdapter {

    String getenv(String name);
}
