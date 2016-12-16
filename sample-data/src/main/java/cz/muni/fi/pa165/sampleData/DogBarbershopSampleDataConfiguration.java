package cz.muni.fi.pa165.sampleData;

import cz.muni.fi.pa165.exceptions.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Takes ServiceConfiguration and adds the SampleDataLoadingFacade bean.
 *
 * @author Dominik Gmiterko
 */
@Configuration
@ImportResource("classpath:service-config.xml")
@ComponentScan(basePackageClasses = {SampleDataLoadingFacadeImpl.class})
public class DogBarbershopSampleDataConfiguration {

    final static Logger log = LoggerFactory.getLogger(DogBarbershopSampleDataConfiguration.class);

    @Autowired
    SampleDataLoadingFacade sampleDataLoadingFacade;

    @PostConstruct
    public void dataLoading() throws DAOException {
        log.debug("Loading sample data..");
        sampleDataLoadingFacade.loadData();
    }
}
