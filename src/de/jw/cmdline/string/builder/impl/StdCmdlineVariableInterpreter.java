package de.jw.cmdline.string.builder.impl;

import java.util.Map;

import de.jw.validation.ExecutionResult;
import de.jw.validation.ExecutionLog;

public class StdCmdlineVariableInterpreter implements CmdlineVariableInterpreter {
	final String stdVariable;

	public StdCmdlineVariableInterpreter(String variable) {
		this.stdVariable = variable;
	}

	@Override
	public ExecutionResult<CmdlineVariableReplacement> interprete(Map<String, Object> parameterMap) {
		Object stdVariableValue = parameterMap.get(stdVariable);
		if (stdVariableValue == null) {
			String errorMsg = "no parameter value in parameterMap for stdVariable key: " + stdVariable;
			return ExecutionLog.error("").<CmdlineVariableReplacement> asExecutionResult(
					new InvalidCmdlineVariableReplacement(errorMsg, this));
		}

		return ExecutionLog.ok().<CmdlineVariableReplacement> asExecutionResult(//
				new ValidCmdlineVariableReplacement(stdVariableValue.toString()));
	}
}