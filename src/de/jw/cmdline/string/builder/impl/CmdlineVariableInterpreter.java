package de.jw.cmdline.string.builder.impl;

import java.util.Map;

import de.jw.validation.ExecutionResult;

public interface CmdlineVariableInterpreter {
	ExecutionResult<CmdlineVariableReplacement> interprete(Map<String, Object> parameterMap);
}