package br.ufrn.ppgsc.scenario.analyzer.d.data;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

public class RuntimeNode {

	private Member member; // Constructor, Field, Method
	private long time;
	private Throwable exception;
	private List<RuntimeNode> children;

	public RuntimeNode(Member member) {
		this.member = member;
		this.children = new ArrayList<RuntimeNode>();
		this.time = 0;
		this.exception = null;
	}

	public void addChild(RuntimeNode node) {
		children.add(node);
	}

	public void removeChild(RuntimeNode node) {
		children.remove(node);
	}

	public RuntimeNode[] getChildren() {
		return children.toArray(new RuntimeNode[0]);
	}

	public Member getMember() {
		return member;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}

}
