package cz.muni.fi.pa165.sampleData;

import cz.muni.fi.pa165.exceptions.FacadeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.annotation.PostConstruct;

/**
 * @author Dominik Gmiterko
 */
@Configuration
@ImportResource("classpath:sample-data-config.xml")
@ComponentScan(basePackageClasses = {SampleDataLoadingFacadeImpl.class})
public class SampleDataConfiguration {

    final static Logger log = LoggerFactory.getLogger(SampleDataConfiguration.class);
    private boolean sampleDataLoaded = false;

    @Autowired
    SampleDataLoadingFacade sampleDataLoadingFacade;

    @PostConstruct
    public void dataLoading() throws FacadeException {
        if(!sampleDataLoaded) {
            sampleDataLoaded = true;
            log.info("Loading sample data..");
            sampleDataLoadingFacade.loadData();
            log.info("Sample data loaded.");
        } else {
            log.info("Sample data already loaded!");
        }
    }
}
