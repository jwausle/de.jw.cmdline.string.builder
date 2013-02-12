package de.jw.cmdline.string.builder.impl;

import java.util.Map;

import com.google.common.base.Function;

import de.jw.validation.ExecutionResult;
import de.jw.validation.ExecutionLog;

public class CmdlineStringBuilder_ {

	public static final class MatchCmdlineVariableType implements
			Function<String, ExecutionResult<CmdlineVariableInterpreter>> {
		@Override
		public ExecutionResult<CmdlineVariableInterpreter> apply(String variable) {
			if (variable == null) { 
				String error = "variable  must not be null.";
				return ExecutionLog.error(error).<CmdlineVariableInterpreter> asExecutionResult(
						new CmdlineVariableInterpreterError(error));
			}
			if (variable.isEmpty()) {
				String error = "variable must not be empty.";
				return ExecutionLog.error(error).<CmdlineVariableInterpreter> asExecutionResult(
						new CmdlineVariableInterpreterError(error));
			}
			if (variable.contains(";"))
				return ExecutionLog.ok().<CmdlineVariableInterpreter> asExecutionResult(
						new ListCmdlineVariableInterpreter(variable));
			else
				return ExecutionLog.ok().<CmdlineVariableInterpreter> asExecutionResult(
						new StdCmdlineVariableInterpreter(variable));
		}
	}

	public static final class TransformCmdlineVariableReplacementByInterpreter implements
			Function<ExecutionResult<CmdlineVariableInterpreter>, ExecutionResult<CmdlineVariableReplacement>> {
		private final Map<String, Object> parameterMap;

		public TransformCmdlineVariableReplacementByInterpreter(Map<String, Object> parameterMap) {
			this.parameterMap = parameterMap;
		}

		@Override
		public ExecutionResult<CmdlineVariableReplacement> apply(
				ExecutionResult<CmdlineVariableInterpreter> cmdlineVariable2) {
			CmdlineVariableInterpreter cmdlineVariable = cmdlineVariable2.getResult();
			if (cmdlineVariable instanceof CmdlineVariableInterpreterError)
				return cmdlineVariable2.reset(ExecutionLog.error("Error before transformation catched: " + cmdlineVariable)
						.<CmdlineVariableReplacement> asExecutionResult(
								new InvalidCmdlineVariableReplacement(cmdlineVariable)));

			if (cmdlineVariable instanceof  StdCmdlineVariableInterpreter)
				return cmdlineVariable2.reset(cmdlineVariable.interprete(parameterMap));

			if (cmdlineVariable instanceof ListCmdlineVariableInterpreter)
				return cmdlineVariable2.reset(cmdlineVariable.interprete(parameterMap));

			throw new IllegalArgumentException("unknown cmdlineVariable type: " + cmdlineVariable.getClass());
		}
	}

}
