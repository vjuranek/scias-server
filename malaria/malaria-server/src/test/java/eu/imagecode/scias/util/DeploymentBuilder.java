package eu.imagecode.scias.util;

import java.io.File;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import eu.imagecode.scias.service.AnalysisService;
import eu.imagecode.scias.service.BatchService;
import eu.imagecode.scias.service.it.AbstractMalariaServiceIT;

public class DeploymentBuilder {

    public static WebArchive createRestServerWar() {
        
        JavaArchive jpaJar = ShrinkWrap.createFromZipFile(JavaArchive.class, new File("target/test-libs/malaria-jpa.jar"));
        jpaJar.delete("/META-INF/persistence.xml");
        jpaJar.addAsManifestResource(new File("src/test/resources/META-INF/persistence.xml"));
        
        return ShrinkWrap.create(WebArchive.class)
                .addAsLibraries(
                    new File("target/test-libs/malaria-rest.jar"))
                .addAsLibrary(jpaJar)
                .addClass(AbstractMalariaServiceIT.class)
                .addPackage("eu.imagecode.scias.service")
                .addPackage("eu.imagecode.scias.util")
                .addPackage("eu.imagecode.scias.testutil")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }
}
