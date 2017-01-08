package cz.muni.fi.pa165.sampleData;

import cz.muni.fi.pa165.exceptions.FacadeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

/**
 * @author Dominik Gmiterko
 */
public class SampleDataInitBean {

    private final static Logger LOGGER = LoggerFactory.getLogger(SampleDataInitBean.class);

    private final SampleDataLoadingFacade sampleDataLoadingFacade;

    public SampleDataInitBean(SampleDataLoadingFacade sampleDataLoadingFacade) {
        this.sampleDataLoadingFacade = sampleDataLoadingFacade;
    }

    @PostConstruct
    public void dataLoading() throws FacadeException {
            LOGGER.info("Loading sample data..");

            sampleDataLoadingFacade.loadData();

            LOGGER.info("Sample data loaded.");
    }
}
