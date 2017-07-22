package eu.imagecode.scias.conf;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@Singleton
@Startup
public class MalariaConfigProducer {
    public final String CONFIG_FILE = "scias.properties";

    protected Properties props = new Properties();

    @PostConstruct
    public void init() {
        try {
            props.load(getClass().getClassLoader().getResourceAsStream(CONFIG_FILE));
        } catch (IOException e) {
            // TODO log error
        }
    }

    @Produces
    @MalariaConf
    public String provideMalariaConf(InjectionPoint ip) {
        String propKey = ip.getAnnotated().getAnnotation(MalariaConf.class).value();
        //TODO check property exists
        return props.getProperty(propKey);
    }
}
