package br.ufrn.ppgsc.scenario.analyzer.service;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

import br.ufrn.ppgsc.scenario.analyzer.dao.GenericDAO;
import br.ufrn.ppgsc.scenario.analyzer.dao.GenericDAOHibernateImpl;
import br.ufrn.ppgsc.scenario.analyzer.domain.Execution;
import br.ufrn.ppgsc.scenario.analyzer.domain.ScenarioQualityAnnotation;
import br.ufrn.ppgsc.scenario.analyzer.domain.StartMethod;
import br.ufrn.ppgsc.scenario.analyzer.domain.builder.ExecutionBuilder;
import br.ufrn.ppgsc.scenario.analyzer.domain.builder.ScenarioBuilder;
import br.ufrn.ppgsc.scenario.analyzer.domain.builder.ScenarioQualityAnnotationBuilder;
import br.ufrn.ppgsc.scenario.analyzer.domain.builder.StartMethodBuilder;


public class DatabaseService <T extends Serializable> {

	public GenericDAO<T> getGenericDAO() {
		GenericDAO<T> dao = new GenericDAOHibernateImpl<T>();
		return dao;
	}
	
	/**
	 * Método que realiza o salvamento dos resultados de uma análise no banco de dados.
	 * @param method
	 */
	public static void saveResults(Method method, Long executionTime, Boolean failed) {
		Execution execution = ExecutionBuilder.build();
		br.ufrn.ppgsc.scenario.analyzer.domain.Scenario scenario = ScenarioBuilder.build(method, execution);
		StartMethod startMethod = StartMethodBuilder.build(method, scenario, executionTime, failed);
		scenario.addToStartMethods(startMethod);
		execution.addToScenarios(scenario);
		List<ScenarioQualityAnnotation> scenatioQualityAnnotations = ScenarioQualityAnnotationBuilder.build(method, scenario);
		scenario.setScenarioQualityAnniotations(scenatioQualityAnnotations);
		GenericDAO<Execution> dao = new DatabaseService<Execution>().getGenericDAO();
		dao.create(execution);
	}
	
}
