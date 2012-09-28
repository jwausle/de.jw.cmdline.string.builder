package de.jw.cmdline.string.builder2;

import java.util.LinkedHashMap;
import java.util.Map;

public class CmdlineStringBuilder {
	public static CmdlineStringBuilder create() {
		return new CmdlineStringBuilder();
	}

	private String cmdlineWithParameter = "";
	private Map<String, Object> cmdLineParameter = new LinkedHashMap<String, Object>();

	public CmdlineStringBuilder forCmdline(String cmdlineStringWithVariables) {
		this.cmdlineWithParameter = cmdlineStringWithVariables;
		return this;
	}

	public CmdlineStringBuilder addAllParameter(Map<String, Object> all) {
		cmdLineParameter.putAll(all);
		return this;
	}

	public CmdlineStringBuilder addParameter(String key, Object value) {
		cmdLineParameter.put(key, value);
		return this;
	}

	public String build() {
		String cmdlineWithReplacedParameter = CmdlineStringBuilders.replaceParameterInCmdline(cmdLineParameter,
				cmdlineWithParameter);
		
		// remove opt(...) variables.
		String cmdline = cmdlineWithReplacedParameter//
				.replaceAll("opt\\([-:\\w]*\\$\\{[-:; \\w]*\\}\\) ", "");
		;

		return cmdline;
	}
}
