package de.jw.cmdline.string.builder.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.jw.validation.ExecutionResult;

public class CmdlineStringBuilders {

	public static final String CLOSE_BRACKET_PATTERN = "}";
	public static final String OPEN_BRACKET_PATTERN = "{";

	public static Map<String, String> extractVariables(String cmdLineWithVariables) {
		Preconditions.checkNotNull(cmdLineWithVariables, "cmdLineWithVariables-String must not be null.");

		final LinkedHashMap<String, String> map = Maps.newLinkedHashMap();

		final int cmdLineWithVariablesLength = cmdLineWithVariables.length();
		int nextStartIndex = 0;
		while (nextStartIndex < cmdLineWithVariablesLength) {

			int indexOfOpenBracket = cmdLineWithVariables.indexOf(OPEN_BRACKET_PATTERN, nextStartIndex);
			if (indexOfOpenBracket == -1)
				break;
			int indexOfCloseBracket = cmdLineWithVariables.indexOf(CLOSE_BRACKET_PATTERN, nextStartIndex) + 1;

			String variableWithBrackets = cmdLineWithVariables.substring(indexOfOpenBracket, indexOfCloseBracket);
			String variable = variableWithBrackets.substring(1, variableWithBrackets.length() - 1);

			if (cmdLineWithVariables.charAt(indexOfOpenBracket - 1) == '$')
				map.put("$" + variableWithBrackets, variable);
			else
				System.out.println("Missing '$' before '{' for cmdline part: " + variableWithBrackets
						+ ". This variable will ignored.");

			nextStartIndex = indexOfCloseBracket;
		}

		return map;
	}

	public static String replaceParameterInCmdline(Map<String, Object> parameterMap, String cmdlineWithVariables) {
		Map<String, String> cmdlineVariablesByPattern = extractVariables(cmdlineWithVariables);

		Map<String, ExecutionResult<CmdlineVariableReplacement>> cmdlineVariableReplacementByPattern = //
		Maps.transformValues(Maps.transformValues(cmdlineVariablesByPattern, //
				new CmdlineStringBuilder_.MatchCmdlineVariableType()),//
				new CmdlineStringBuilder_.TransformCmdlineVariableReplacementByInterpreter(parameterMap));

		String cmdline = cmdlineWithVariables;

		final List<InvalidCmdlineVariableReplacement> errors = Lists.newLinkedList();

		final Set<String> patterns = cmdlineVariableReplacementByPattern.keySet();
		for (String pattern : patterns) {
			final CmdlineVariableReplacement replacement = cmdlineVariableReplacementByPattern.get(pattern).getResult();
			if (replacement instanceof InvalidCmdlineVariableReplacement) {
				errors.add((InvalidCmdlineVariableReplacement) replacement);
				continue;
			}

			final String regExpString = CmdlineStringBuilders.toRegExp(pattern);
			final String replacementString = replacement.getReplacementString().getResult();

			cmdline = cmdline.replaceAll("opt(" + regExpString + ")", replacementString)//
					.replaceAll(regExpString, replacementString);
		}
		return cmdline;
	}

	public static String toRegExp(String string) {
		return string.replaceAll("\\$", "\\\\\\$")//
				.replaceAll("\\{", "\\\\\\{")//
				.replaceAll("\\}", "\\\\\\}")//
		;
	}

}
