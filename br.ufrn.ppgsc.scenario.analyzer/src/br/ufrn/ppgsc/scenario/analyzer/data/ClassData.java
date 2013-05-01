package br.ufrn.ppgsc.scenario.analyzer.data;

public interface ClassData extends AbstractData {

	public ComponentData getDeclaringComponent();

	public void setDeclaringComponent(ComponentData declaringComponent);

}
