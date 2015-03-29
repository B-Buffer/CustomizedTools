package com.customized.tools.cli.jboss;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jboss.as.cli.Util;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.Property;

public class CLIServiceImpl implements CLIService {
	
	static Map<String, String> tools = new HashMap<>();
	
	static {
		tools.put("jarClassSearcher", "jarClassSearcher");
		tools.put("fileSearcher", "fileSearcher");
		tools.put("fileChangeMonitor", "fileChangeMonitor");
		tools.put("dbConnectionTester", "dbConnectionTester");
		tools.put("gcViewer", "gcViewer");
		tools.put("tda", "tda");
	}
	
	@Override
	public ModelNode getComposites(ModelNode composite) {
		
		final ModelNode results = new ModelNode();
		results.get(Util.OUTCOME).set(Util.SUCCESS);
		results.get(Util.RESULT).setEmptyList();
		
//		ModelNode step = new ModelNode();
//		step.get(key).set(tools.get(key));
		for(String key : tools.keySet()){
			ModelNode step = new ModelNode();
			step.get(key).set(tools.get(key));
			results.get(Util.RESULT).add(step);
		}
		return results;
	}

	@Override
	public void close() throws IOException {
		System.out.println("TODO--");
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ModelNode result = new CLIServiceImpl().getComposites(null);
		for(ModelNode node : result.get(Util.RESULT).asList()){
			Property props = node.asProperty();
			String key = props.getName();
			String value = node.get(key).asString();
			System.out.println(key + " => " + value);
		}
	}

	

}
