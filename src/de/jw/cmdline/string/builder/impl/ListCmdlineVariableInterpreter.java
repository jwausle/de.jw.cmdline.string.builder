package de.jw.cmdline.string.builder.impl;

import java.util.List;
import java.util.Map;

import com.google.common.base.Preconditions;

import de.jw.validation.ExecutionResult;
import de.jw.validation.ExecutionLog;

public class ListCmdlineVariableInterpreter implements CmdlineVariableInterpreter {
	final String listVariable;

	public ListCmdlineVariableInterpreter(String listVariable) {
		this.listVariable = listVariable;
	}

	@Override
	public ExecutionResult<CmdlineVariableReplacement> interprete(Map<String, Object> parameterMap) {
		String[] listVariableParts = listVariable.split(";");
		Preconditions.checkNotNull(listVariableParts, "listVariableParts must init.");
		Preconditions.checkArgument(listVariableParts.length >= 2, "mi 2 listVariableParts expected.");

		String variableName = listVariableParts[0];
		String listSeperator = listVariableParts[1];
		String listSeperatorStrategy = listVariableParts.length > 2 ? listVariableParts[2] : "before";

		ExecutionLog ok = ExecutionLog.ok();
		Object listObjectValues = parameterMap.get(variableName);
		if (listObjectValues == null)
			return ok.<CmdlineVariableReplacement> asExecutionResult(//
					new ValidCmdlineVariableReplacement("${" + listSeperator + "}"));
		if (!(listObjectValues instanceof List))
			return ok.<CmdlineVariableReplacement> asExecutionResult(//
					new ValidCmdlineVariableReplacement("${" + listSeperator + "}"));

		@SuppressWarnings("rawtypes")
		List listValues = (List) listObjectValues;
		if (listValues.isEmpty())
			return ok.<CmdlineVariableReplacement> asExecutionResult(//
					new ValidCmdlineVariableReplacement("${" + listSeperator + "}"));

		StringBuilder replacement = new StringBuilder();
		for (Object value : listValues) {
			if ("before".equals(listSeperatorStrategy))
				replacement.append(listSeperator);

			replacement.append(value.toString());

			if ("after".equals(listSeperator))
				replacement.append(listSeperator);
		}

		return ok.<CmdlineVariableReplacement> asExecutionResult(//
				new ValidCmdlineVariableReplacement(replacement.toString()));
	}
}