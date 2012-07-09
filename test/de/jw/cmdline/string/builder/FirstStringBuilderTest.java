package de.jw.cmdline.string.builder;

import java.util.Arrays;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Maps;

import de.jw.cmdline.string.builder.impl.CmdlineStringBuilders;

public class FirstStringBuilderTest {
	 @Test
	public void fistCmdLineReplacementTest() {
		Map<String, Object> parameterMap = Maps.newLinkedHashMap();
		parameterMap.put("cmd", "path/to/cmd.exe");
		parameterMap.put("long-option", "LONG_OPTION");
		parameterMap.put("shortoption", "SHORTOPTION");
		parameterMap.put("list-option", Arrays.asList("eins", "zwei"));
		parameterMap.put("ignore-test", "IGNORE_TEST");
		parameterMap.put("opt-option", "OPT_OPTION");
		
		String cmdlineWithVariables = "${cmd} " +
				"--${long-option} -${shortoption} " +
				"-- --I ${list-option;-I ;before} " +
				"${ignore-test} " +
				"opt(--${opt-option}) opt(--${opt2-option}) " +
				"--${no-parameter-setted} -F -D";
		
		String cmdline = CmdlineStringBuilders.replaceParameterInCmdline(parameterMap, cmdlineWithVariables)//
				.replaceAll("opt\\((\\-)*\\$\\{.*\\}\\)","")// 'opt(${.*}) -> ""'
				.replaceAll("opt\\((.*)\\)", "$1")// 'opt(.*) -> .*'
				;
		System.out.println(cmdline.matches(".*\\$\\{.*\\}.*") + ": " + cmdline);
	}

	@Test
	public void stringReplaceTest() {
		System.out.println("replace ${cmd} $cmd?".replaceAll("\\$\\{cmd\\}", "CMD"));
		System.out.println("${cmd}".replaceAll("\\$", "\\\\\\$"));
		System.out.println(CmdlineStringBuilders.toRegExp("${cmd}"));
		
//		System.out.println("\\\\\\\\\\\\\\\\\\\\".replaceAll("(\\\\)*", "\\\\\\"));
	
		System.out.println("'opt(${opt-option})'".replaceAll("opt\\((\\-)*\\$\\{.*\\}\\)",""));
		System.out.println("${test}".matches("\\$\\{.*\\}"));
	}
}
