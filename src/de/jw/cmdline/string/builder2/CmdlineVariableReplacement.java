package de.jw.cmdline.string.builder2;

import de.jw.validation2.ExecutionResult;


public interface CmdlineVariableReplacement{
	ExecutionResult<String> getReplacementString();
}