package com.zh.jsengine;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import com.alibaba.fastjson.JSONObject;

public class TestNashorn {

	public static void main(String[] args) throws FileNotFoundException {
		FileReader fr = new FileReader(TestNashorn.class.getResource("/conf/js/quzhou.js").getPath());
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		ScriptEngine se = scriptEngineManager.getEngineByName("nashorn");
		try {
			ScriptContext sc = se.getContext();
			JSONObject location = new JSONObject();
			location.put("href", "http://quzhou.19lou.com/forum-900-thread-165601343792152480-1-1.html");
			JSONObject document = new JSONObject();
			document.put("location", location);
			document.put("cookie", "");
			Bindings bindings = new SimpleBindings();
			bindings.put("document", document);
			se.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
			Object o = se.eval(fr);
			System.out.println(JSONObject.toJSON(o));
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
}
