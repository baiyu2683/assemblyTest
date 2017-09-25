package com.zh.jsengine;

import com.alibaba.fastjson.JSONArray;
import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import jdk.nashorn.internal.runtime.regexp.joni.constants.Arguments;

import javax.script.*;

/**
 * Created by zh on 2017-08-18.
 */
public class TestArguments {

    public static void main(String[] args) throws ScriptException {
        NashornScriptEngineFactory nashornScriptEngineFactory = new NashornScriptEngineFactory();
        ScriptEngine scriptEngine = nashornScriptEngineFactory.getScriptEngine();
        scriptEngine.eval("function setCookie(a,b,c){return arguments;}");
        Object o = scriptEngine.eval("setCookie(1,2,3,4)");
        Arguments arguments = null;
        System.out.println(1);
    }
}
