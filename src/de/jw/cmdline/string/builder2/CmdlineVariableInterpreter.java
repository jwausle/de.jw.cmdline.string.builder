package de.jw.cmdline.string.builder2;

import java.util.Map;

import de.jw.validation2.ExecutionResult;

public interface CmdlineVariableInterpreter {
	ExecutionResult<CmdlineVariableReplacement> interprete(Map<String, Object> parameterMap);
}