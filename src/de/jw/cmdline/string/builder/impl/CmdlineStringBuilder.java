package de.jw.cmdline.string.builder.impl;

import java.util.Map;

public class CmdlineStringBuilder {
	private String cmdlineWithParameter;
	private Map<String, Object> cmdLineParameter;

	public String build() {
		String cmdlineWithReplacedParameter = CmdlineStringBuilders.replaceParameterInCmdline(cmdLineParameter,
				cmdlineWithParameter);
		
		return cmdlineWithReplacedParameter;
	}
}
