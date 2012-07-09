package de.jw.cmdline.string.builder.impl;

import de.jw.validation.ExecutionResult;
import de.jw.validation.ExecutionLog;

public class InvalidCmdlineVariableReplacement implements CmdlineVariableReplacement {

	public InvalidCmdlineVariableReplacement(CmdlineVariableInterpreter cmdlineVariable) {
	}

	public InvalidCmdlineVariableReplacement(CmdlineVariableInterpreterError cmdlineVariableError) {

	}

	public InvalidCmdlineVariableReplacement(String string, CmdlineVariableInterpreter stdCmdlineVariableInterpreter) {
		
	}

	@Override
	public ExecutionResult<String> getReplacementString() {
		return new ExecutionResult<String>(ExecutionLog.error(""));
	}
}