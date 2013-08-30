package br.ufrn.dimap.taskanalyzer.history;

public class MethodLimit {

	private String signature;
	private int startLine;
	private int endLine;

	public MethodLimit(String signature, int startLine, int endLine) {
		this.signature = signature;
		this.startLine = startLine;
		this.endLine = endLine;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public int getStartLine() {
		return startLine;
	}

	public void setStartLine(int startLine) {
		this.startLine = startLine;
	}

	public int getEndLine() {
		return endLine;
	}

	public void setEndLine(int endLine) {
		this.endLine = endLine;
	}

}
