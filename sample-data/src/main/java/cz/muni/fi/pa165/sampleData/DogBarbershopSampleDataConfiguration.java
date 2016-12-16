package cz.muni.fi.pa165.sampleData;

import cz.muni.fi.pa165.exceptions.DAOException;
import cz.muni.fi.pa165.exceptions.FacadeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.annotation.PostConstruct;

/**
 * Takes ServiceConfiguration and adds the SampleDataLoadingFacade bean.
 *
 * @author Dominik Gmiterko
 */
@Configuration
//@ImportResource("classpath:service-config.xml")
@ComponentScan(basePackageClasses = {SampleDataLoadingFacadeImpl.class})
public class DogBarbershopSampleDataConfiguration {

    final static Logger log = LoggerFactory.getLogger(DogBarbershopSampleDataConfiguration.class);

    static boolean initialized = false;

    @Autowired
    SampleDataLoadingFacade sampleDataLoadingFacade;

    @PostConstruct
    public void sampleData() throws FacadeException {
        if(!initialized) {
            log.info("Loading sample data..");
            sampleDataLoadingFacade.loadData();
            initialized = true;
        } else {
            log.warn("Sample data already loaded!");
        }
    }
}
