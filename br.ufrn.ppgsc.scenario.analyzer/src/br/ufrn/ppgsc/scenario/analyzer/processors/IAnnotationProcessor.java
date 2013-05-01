package br.ufrn.ppgsc.scenario.analyzer.processors;

import br.ufrn.ppgsc.scenario.analyzer.util.JDTWALADataStructure;

public interface IAnnotationProcessor {

	public void process(JDTWALADataStructure data);

	public void addProcessorQA(Class<? extends AbstractProcessorQA> cls);

}
