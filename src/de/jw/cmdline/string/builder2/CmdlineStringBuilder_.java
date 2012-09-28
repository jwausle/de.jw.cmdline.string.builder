package de.jw.cmdline.string.builder2;

import java.util.Map;

import com.google.common.base.Function;

import de.jw.cmdline.string.builder2.internal.impl.CmdlineVariableInterpreterError;
import de.jw.cmdline.string.builder2.internal.impl.InvalidCmdlineVariableReplacement;
import de.jw.cmdline.string.builder2.internal.impl.ListCmdlineVariableInterpreter;
import de.jw.cmdline.string.builder2.internal.impl.StdCmdlineVariableInterpreter;
import de.jw.validation2.ExecutionLog;
import de.jw.validation2.ExecutionResult;

public class CmdlineStringBuilder_ {

	public static final class MatchCmdlineVariableType implements
			Function<String, ExecutionResult<CmdlineVariableInterpreter>> {
		@Override
		public ExecutionResult<CmdlineVariableInterpreter> apply(String variable) {
			if (variable == null) {
				String error = "variable must not be null.";
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
				ExecutionResult<CmdlineVariableInterpreter> cmdlineVariableArg) {
			CmdlineVariableInterpreter cmdlineVariable = cmdlineVariableArg.getResult();

			if (cmdlineVariable instanceof CmdlineVariableInterpreterError) {
				final InvalidCmdlineVariableReplacement invalidReplacement = new InvalidCmdlineVariableReplacement(
						cmdlineVariable);

				ExecutionResult<CmdlineVariableReplacement> invalidResult = cmdlineVariableArg//
						.reset(ExecutionLog//
								.error("Error before transformation catched: " + cmdlineVariable)//
								.<CmdlineVariableReplacement> asExecutionResult(invalidReplacement)//
						);

				return invalidResult;
			}

			if (cmdlineVariable instanceof StdCmdlineVariableInterpreter)
				return cmdlineVariableArg.reset(cmdlineVariable.interprete(parameterMap));

			if (cmdlineVariable instanceof ListCmdlineVariableInterpreter)
				return cmdlineVariableArg.reset(cmdlineVariable.interprete(parameterMap));

			throw new IllegalArgumentException("unknown cmdlineVariable type: " + cmdlineVariable.getClass());
		}
	}

}
