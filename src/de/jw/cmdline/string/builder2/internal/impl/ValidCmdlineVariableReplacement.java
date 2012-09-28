package de.jw.cmdline.string.builder2.internal.impl;

import de.jw.cmdline.string.builder2.CmdlineVariableReplacement;
import de.jw.validation2.ExecutionLog;
import de.jw.validation2.ExecutionResult;


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