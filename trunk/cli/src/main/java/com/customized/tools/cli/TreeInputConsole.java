package com.customized.tools.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class TreeInputConsole extends InputConsole {
	
	private final String name;

	private TreeNode currentNode;
	
	private String cursor = "~";
	
	private String cursorStr ;
	
	private BufferedReader in ;

	public TreeInputConsole(String name, TreeNode currentNode) {
		super();
		this.name = name;
		this.currentNode = currentNode;
		
		updateCursorStr(cursor);
		
		InputStreamReader converter = new InputStreamReader(System.in);
		in = new BufferedReader(converter);
	}

	public TreeNode getCurrentNode() {
		return currentNode;
	}

	protected void updateCurrentNode(TreeNode currentNode) {
		this.currentNode = currentNode;
	}
	
	private void updateCursorStr(String cursor) {
		if(isShowRoot) {
			cursor = "/" ;
		}
		cursorStr = "[" +name + " " + cursor + "]";
	}
	
	public void execute() throws IOException {
		
		TreeNode rootNode = findRootNode(currentNode);
		updateCurrentNode(rootNode);
		String pointer = "";
		
		while(true) {
			
			print(cursorStr);
			
			pointer = in.readLine();
			//TODO
			println(pointer);
			
			switch(type(pointer)){
			case NULL :
				break ;
			case LS :
				handleLS(pointer);
				break;
			case CD :
				handleCD(pointer);
				break;
			case PWD :
				handlePWD(pointer);
				break;
			}
			
			updateCursorStr(getCurrentNode().getName());
			
			
			
		}
	}


	private void handleLS(String pointer) {
		
		String[] array = pointer.split(" ");
		
		boolean isPrintDetail = false;
		
		for (int i = 0 ; i < array.length ; i ++) {
			if(array[i].equals("-l")) {
				isPrintDetail = true ;
			}
		}
		
		if(isShowRoot){
			if(isPrintDetail) {
				println(getCurrentNode());
			} else {
				println(getCurrentNode() + " " + getCurrentNode().getContent());
			}
		} else {
			StringBuffer sb = new StringBuffer();
			List<TreeNode> nodes = currentNode.getSons();
					
			if(isPrintDetail) {
				for(TreeNode node : nodes) {
					sb.append(node.getName() + " " + node.getContent());
					sb.append("\n");
				}
				print(sb.toString());
			} else {
				for(TreeNode node : nodes) {
					sb.append(node.getName());
					sb.append(TAB);
				}
				println(sb.toString());
			}
		}
		
	}
	
	boolean isShowRoot = false;

	private void handleCD(String pointer) {

		String[] array = pointer.split(" ");
		
		if(array.length != 2) {
			error(pointer);
		}
		
		String[] path = array[1].split("/");
		
		for(int i = 0 ; i < path.length ; i ++) {
			if(path[i].equals(".")){
				updateCurrentNode(getCurrentNode());
			} else if(path[i].equals("..")) {
				TreeNode father = getCurrentNode().getFather();
				if(null != father){
					updateCurrentNode(father);
				} else {
					updateCurrentNode(getCurrentNode());
					isShowRoot = true;
				}
			} else {
				String name = path[i];
				TreeNode node = findNode(name);
				if(null == node) {
					prompt(array[1] + " does not exist");
					break;
				}
				updateCurrentNode(node);
			}
		}
		
	}

	private TreeNode findNode(String name) {
		
		TreeNode result = null;
		
		for(TreeNode node : getCurrentNode().getSons()) {
			if(node.getName().compareTo(name) == 0) {
				result = node;
			}
		}
		return result;
	}

	private void error(String pointer) {
		prompt(pointer + " can not be recognized");
	}

	private void handlePWD(String pointer) {
		
		String tmp = getCurrentNode().getName();
		
		TreeNode node = getCurrentNode();
		while((node = getParent(node)) != null) {
			String old = tmp;
			tmp = node.getName() + File.separator + old ;
		}
		
		String result = File.separator + tmp ;
		
		println(result);
		
	}

	private TreeNode getParent(TreeNode node) {
		return node.getFather();
	}

	private Action type(String pointer) {
		
		if(pointer.toLowerCase().startsWith("cd")) {
			return Action.CD ;
		} else if(pointer.toLowerCase().startsWith("ls")) {
			return Action.LS;
		} else if(pointer.toLowerCase().startsWith("pwd")) {
			return Action.PWD;
		} else if(pointer.equals("")){
			return Action.NULL;
		}
		
		return Action.NULL;
	}

	private TreeNode findRootNode(TreeNode node) {
				
		if(null != node.getFather()) {
			return findRootNode(node.getFather());
		} else {
			return node;
		}
	}
	
	private enum Action {
		
		NULL,
		LS,
		CD,
		PWD,
	}
}
