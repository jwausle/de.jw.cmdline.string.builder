package de.jw.cmdline.string.builder.impl;

import de.jw.validation.ExecutionResult;


public interface CmdlineVariableReplacement{
	ExecutionResult<String> getReplacementString();
}