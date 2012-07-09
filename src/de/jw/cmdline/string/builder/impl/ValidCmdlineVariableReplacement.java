package de.jw.cmdline.string.builder.impl;

import de.jw.validation.ExecutionResult;
import de.jw.validation.ExecutionLog;


public class ValidCmdlineVariableReplacement implements CmdlineVariableReplacement{

	private String variableReplacement;

	public ValidCmdlineVariableReplacement(String stdVariable) {
		this.variableReplacement = stdVariable;
	}

	@Override
	public ExecutionResult<String> getReplacementString() {
		return ExecutionLog.ok().asExecutionResult(variableReplacement);
	}
	
}