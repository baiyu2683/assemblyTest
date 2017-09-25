package com.zh.jsengine;

import jdk.nashorn.internal.runtime.regexp.joni.constants.Arguments;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;

/**
 * Created by zh on 2017-08-18.
 */
public class TestRhino {
    public static void main(String[] args) {
        Context ctx=Context.enter();
        Scriptable scope=ctx.initStandardObjects();
        Object result=ctx.evaluateString(scope, "function setCookie(a,b,c){return setCookie.arguments.length;}", null, 0,null);
        Object result1=ctx.evaluateString(scope, "setCookie(1,2,3,4,5)", null, 0,null);
        System.out.println("result1="+result1);
    }
}
