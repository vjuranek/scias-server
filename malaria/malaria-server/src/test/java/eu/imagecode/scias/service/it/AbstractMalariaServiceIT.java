package eu.imagecode.scias.service.it;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.persistence.CreateSchema;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import eu.imagecode.scias.util.DeploymentBuilder;

@CreateSchema({"prepare_db.sql"})
public class AbstractMalariaServiceIT {
    
    //stations
    protected static final int STATION1_ID = 1;
    protected static final int STATION2_ID = 2;
    protected static final String STATION1_UUID = "aaaaaaaa-1111-2222-3333-bbbbbbbbbbbb";
    protected static final String STATION2_UUID = "f8ffba00-9134-4828-b34d-c03b4b2ee736";
    
    //patients
    protected static final String PATIENT1_NAME = "joe";
    protected static final String PATIENT2_NAME = "marry";

    @Deployment
    public static WebArchive createDeployment() {
        return DeploymentBuilder.createRestServerWar();
    }

}
