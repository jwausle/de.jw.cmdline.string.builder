package de.jw.cmdline.string.builder2.internal.impl;

import de.jw.cmdline.string.builder2.CmdlineVariableInterpreter;
import de.jw.cmdline.string.builder2.CmdlineVariableReplacement;

import de.jw.validation2.ExecutionLog;
import de.jw.validation2.ExecutionResult;

public class InvalidCmdlineVariableReplacement implements CmdlineVariableReplacement {

	private String errorMessage;
	private CmdlineVariableInterpreter cmdlineVariableInterpreter;

	public InvalidCmdlineVariableReplacement(CmdlineVariableInterpreter cmdlineVariable) {
		this(new CmdlineVariableInterpreterError("error",cmdlineVariable));
	}

	public InvalidCmdlineVariableReplacement(CmdlineVariableInterpreterError cmdlineVariableError) {
		this("ERROR occcured.",cmdlineVariableError);
	}

	public InvalidCmdlineVariableReplacement(String string, CmdlineVariableInterpreter stdCmdlineVariableInterpreter) {
		this.errorMessage = string;
		this.cmdlineVariableInterpreter = stdCmdlineVariableInterpreter;
	}

	public InvalidCmdlineVariableReplacement(String string) {
		this(new CmdlineVariableInterpreterError(string));
	}

	@Override
	public ExecutionResult<String> getReplacementString() {
		return new ExecutionResult<String>(ExecutionLog.error(errorMessage + ": " + cmdlineVariableInterpreter));
	}
}