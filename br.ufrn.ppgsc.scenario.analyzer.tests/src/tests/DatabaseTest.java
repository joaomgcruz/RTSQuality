package tests;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import br.ufrn.ppgsc.scenario.analyzer.dao.GenericDAO;
import br.ufrn.ppgsc.scenario.analyzer.domain.Execution;
import br.ufrn.ppgsc.scenario.analyzer.domain.Scenario;
import br.ufrn.ppgsc.scenario.analyzer.domain.StartMethod;
import br.ufrn.ppgsc.scenario.analyzer.service.DatabaseService;

public class DatabaseTest {

	@Test
	public void testSaveExecution() {
		Execution execution = new Execution();
		execution.setDate(new Date());
		
		Scenario scenario = new Scenario();
		scenario.setName("Cenario teste");
		scenario.setExecution(execution);
		
		ArrayList<Scenario> scenarios = new ArrayList<Scenario>();
		scenarios.add(scenario);
		execution.setScenarios(scenarios);
		
		ArrayList<StartMethod> startMethods = new ArrayList<StartMethod>();
		StartMethod startMethod = new StartMethod();
		startMethod.setName("testSaveExecution");
		startMethods.add(startMethod);
		startMethod.setScenario(scenario);
		scenario.setStartMethods(startMethods);
		
		GenericDAO<Execution> dao = new DatabaseService<Execution>().getGenericDAO();
		dao.create(execution);
	}

}
