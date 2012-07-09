package de.jw.cmdline.string.builder.impl;

import java.util.Map;

import de.jw.validation.ExecutionResult;

public class CmdlineVariableInterpreterError implements CmdlineVariableInterpreter {
	final String error;

	public CmdlineVariableInterpreterError(String error) {
		this.error = error;
	}

	@Override
	public ExecutionResult<CmdlineVariableReplacement> interprete(Map<String, Object> parameterMap) {
		throw new UnsupportedOperationException("interprete not callable for: " + CmdlineVariableInterpreterError.class.getSimpleName());
	}
}