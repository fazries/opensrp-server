package org.opensrp.repository.it;

import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;

import org.junit.Before;
import org.mockito.Mock;
import org.opensrp.scheduler.HealthSchedulerService;
import org.opensrp.util.TestResourceLoader;

public class ActionCreationIntegrationTest extends TestResourceLoader{
    public ActionCreationIntegrationTest() throws IOException {
		super();
	}

	@Mock
    private HealthSchedulerService scheduler;
    
    @Before
    public void setUp() throws Exception {
        initMocks(this);
        
    }
    
    public static void main(String[] args) {
		System.out.println(Boolean.valueOf("0"));
	}

}
