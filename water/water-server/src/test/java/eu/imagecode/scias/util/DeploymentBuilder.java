package eu.imagecode.scias.util;

import java.io.File;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import eu.imagecode.scias.service.AnalysisService;
import eu.imagecode.scias.service.BatchService;

public class DeploymentBuilder {

    public static WebArchive createRestServerWar() {
        return ShrinkWrap.create(WebArchive.class)
                .addClass(AnalysisService.class)
                .addClass(BatchService.class)
                .addPackage("eu.imagecode.scias.util")
                .addPackage("eu.imagecode.scias.model.jpa")
                .addPackage("eu.imagecode.scias.model.rest")
                .addPackage("eu.imagecode.scias.testutil")
                .addAsWebInfResource(new File("src/test/resources/META-INF/persistence.xml"), "classes/META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }
}
