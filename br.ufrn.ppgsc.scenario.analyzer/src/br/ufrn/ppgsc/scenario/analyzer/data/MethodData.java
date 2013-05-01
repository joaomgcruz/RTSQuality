package br.ufrn.ppgsc.scenario.analyzer.data;

import java.util.List;

public interface MethodData extends AbstractData {
	
	public String getVersion();

	public void setVersion(String version);
	
	public String getSignature();

	public void setSignature(String signature);

	public ClassData getDeclaringClass();

	public void setDeclaringClass(ClassData declaringClass);

	public ScenarioData getScenario();

	public void setScenario(ScenarioData scenario);
	
	public List<MethodData> getMethodInvocations();

	public List<MethodData> getMethodParents();

	public List<AbstractQAData> getQualityAttributes();

	public void setQualityAttributes(List<AbstractQAData> qualityAttributes);

}
