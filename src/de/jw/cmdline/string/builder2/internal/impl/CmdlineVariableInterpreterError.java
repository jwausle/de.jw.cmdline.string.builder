package de.jw.cmdline.string.builder2.internal.impl;

import java.util.Map;

import de.jw.cmdline.string.builder2.CmdlineVariableInterpreter;
import de.jw.cmdline.string.builder2.CmdlineVariableReplacement;
import de.jw.validation2.ExecutionResult;

public class CmdlineVariableInterpreterError implements CmdlineVariableInterpreter {
	final String error;
	final Object optionalContext;

	public CmdlineVariableInterpreterError(String error) {
		this(error,null);
	}

	public CmdlineVariableInterpreterError(String error2, Object object) {
		this.error = error2;
		this.optionalContext = object;
	}

	@Override
	public ExecutionResult<CmdlineVariableReplacement> interprete(Map<String, Object> parameterMap) {
		throw new UnsupportedOperationException("interprete not callable for: " + CmdlineVariableInterpreterError.class.getSimpleName());
	}
}