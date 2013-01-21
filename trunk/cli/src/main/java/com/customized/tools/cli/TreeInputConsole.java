package com.customized.tools.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TreeInputConsole extends InputConsole {
	
	private final String name;

	private TreeNode currentNode;
	
	private List<TreeNode> rootNodes = new ArrayList<TreeNode>();
	
	private String cursor = "~";
	
	private String cursorStr ;
	
	private BufferedReader in ;

	public TreeInputConsole(String name, TreeNode currentNode) {
		super();
		this.name = name;
		this.currentNode = currentNode;
		
		updateCursorStr(currentNode);
		
		InputStreamReader converter = new InputStreamReader(System.in);
		in = new BufferedReader(converter);
	}

	public TreeNode getCurrentNode() {
		return currentNode;
	}

	public List<TreeNode> getRootNodes() {
		return rootNodes;
	}

	protected void updateCurrentNode(TreeNode currentNode) {
		this.currentNode = currentNode;
	}
	
	private void updateCursorStr(TreeNode node) {
		if(null == node) {
			cursor = "/";
		} else {
			cursor = node.getName() ;
		}
		cursorStr = "[" +name + " " + cursor + "]";
	}
	
	public void execute() throws IOException {
		
		String pointer = "";
		
		while (true) {
			
			addTreeNodeToRootNodeList(getCurrentNode());
			
			print(cursorStr);
			
			pointer = in.readLine();
			
			switch (type(pointer)) {
			
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
			case RM :
				handleRM(pointer);
				break;
			case ADD :
				handleADD(pointer);
				break;
			case HELP :
				handleHELP(pointer);
				break;
			case OTHER :
				promptErrorCommand(pointer);
				break;
			}
			
			updateCursorStr(getCurrentNode());		
		}
	}

	public void handleLS(String pointer) {
		
		String[] array = pointer.split(" ");
		
		boolean isPrintDetail = false;
		
		for (int i = 0 ; i < array.length ; i ++) {
			if(array[i].equals("-l")) {
				isPrintDetail = true ;
			}
		}
		
		StringBuffer sb = new StringBuffer();
		List<TreeNode> nodes = getPrintNodes();
				
		if(isPrintDetail) {
			for(TreeNode node : nodes) {
				sb.append(node.getName() + " " + node.getContent());
				sb.append("\n");
			}
			if (nodes.size() > 0)
				print(sb.toString());
			else 
				println(sb.toString());
		} else {
			for(TreeNode node : nodes) {
				sb.append(node.getName());
				sb.append(TAB);
			}
			println(sb.toString());
		}
	}
	
	public void handlePWD(String pointer) {
		
		if(getCurrentNode() == null) {
			println("/");
		} else {
			String tmp = getCurrentNode().getName();
			
			TreeNode node = getCurrentNode();
			while((node = getFatherNode(node)) != null) {
				String old = tmp;
				tmp = node.getName() + File.separator + old ;
			}			
			println(File.separator + tmp);
		}	
	}
	
	public void handleCD(String pointer) {

		String[] array = pointer.split(" ");
		
		if(array.length != 2) {
			promptErrorCommand(pointer);
			return ;
		}
		
		String[] path = array[1].split("/");
		
		for(int i = 0 ; i < path.length ; i ++) {
			if(path[i].equals(".")){
				updateCurrentNode(getCurrentNode());
			} else if(path[i].equals("..")) {
				if(getCurrentNode() != null) {
					updateCurrentNode(getCurrentNode().getFather());
				}
			} else {
				String name = path[i];
				TreeNode node = findNode(name);
				if(null == node) {
					prompt("[" + array[1] + "] does not exist");
					break;
				}
				updateCurrentNode(node);
			}
		}
	}
	
	public void handleRM(String pointer) {
		
		String[] array = pointer.split(" ");
		
		if(array.length < 2) {
			promptErrorCommand(pointer);
			return ;
		}
		
		if(getCurrentNode() == null && getRootNodes().size() > 0) {
			List<TreeNode> list = new ArrayList<TreeNode>();
			for(int i = 1 ; i < array.length ; i ++) {
				String name = array[i];
				for(int j = 0 ; j < list.size() ; j ++) {
					if(name.compareTo(list.get(i).getName()) == 0) {
						getRootNodes().remove(list.get(i));
					}
				}
			}
			return ;
		}
		
		if(getCurrentNode().getSons().size() == 0) {
			return ;
		}
		
		if(array.length == 2 && array[1].equals("*")) {
			getCurrentNode().getSons().clear();
			return ;
		}
		
		List<TreeNode> list = new ArrayList<TreeNode>();
		list.addAll(getCurrentNode().getSons());
		
		for(int i = 1 ; i < array.length ; i ++) {
			String name = array[i];
			for(int j = 0 ; j < list.size() ; j ++) {
				if(name.compareTo(list.get(j).getName()) == 0) {
					getCurrentNode().getSons().remove(list.get(j));
				}
			}
		}
	}
	
	public void handleADD(String pointer) {
		
		String[] array = pointer.split(" ");
		
		if(array.length != 1) {
			promptErrorCommand(pointer);
			return ;
		}
		
		String name = readString("Enter Node Name:", true);
		String content = readString("Enter Node Content:", false);
		TreeNode node = new TreeNode(name, content, getCurrentNode(), null);
		if(getCurrentNode() == null) {
			getRootNodes().add(node);
		} else {
			getCurrentNode().getSons().add(node);
		}
	}
	
	public void handleHELP(String pointer) {
		println("[<ls>] list all nodes");
		println("[<ls> <-l>] list all nodes with contents");
		println("[<cd> <PATH>] redirect via PATH");
		println("[<pwd>] show current path");
		println("[<rm> <NODE_NAME> <*>] delete node, * hints delete all son nodes");
		println("[<add>] add new node");
	}

	private List<TreeNode> getPrintNodes() {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		
		if( getCurrentNode() == null && getRootNodes().size() > 0) {
			nodes.addAll(getRootNodes());
		} else if(getCurrentNode() != null) {
			nodes.addAll(getCurrentNode().getSons());
		}
		return nodes;
	}

	private TreeNode findNode(String name) {
		
		TreeNode result = null;
		
		if(getCurrentNode() != null) {
			for(TreeNode node : getCurrentNode().getSons()) {
				if(node.getName().compareTo(name) == 0) {
					result = node;
				}
			}
		} else if(getCurrentNode() == null && getRootNodes().size() > 0) {
			for(TreeNode node : getRootNodes()) {
				if(node.getName().compareTo(name) == 0) {
					result = node;
				}
			}
		}
		return result;
	}

	public void promptErrorCommand(String pointer) {
		prompt("[" + pointer + "] can not be recognized");
	}

	private TreeNode getFatherNode(TreeNode node) {
		return node.getFather();
	}

	private ConsoleAction type(String pointer) {
		
		if(pointer.toLowerCase().startsWith("cd")) {
			return ConsoleAction.CD ;
		} else if(pointer.toLowerCase().startsWith("ls")) {
			return ConsoleAction.LS;
		} else if(pointer.toLowerCase().startsWith("pwd")) {
			return ConsoleAction.PWD;
		} else if(pointer.toLowerCase().startsWith("rm")) {
			return ConsoleAction.RM;
		} else if(pointer.toLowerCase().startsWith("add")) {
			return ConsoleAction.ADD;
		} else if(pointer.toLowerCase().startsWith("help")) {
			return ConsoleAction.HELP;
		} else if(pointer.equals("")){
			return ConsoleAction.NULL;
		} else {
			return ConsoleAction.HELP;
		}
		
	}
	
	public void addTreeNodeToRootNodeList(TreeNode node) {
		
		if(getRootNodes().size() > 0) {
			return ;
		}
		
		if(null != node) {
			TreeNode rootNode = findRootNode(node);
			getRootNodes().add(rootNode);
		}
	}

	public TreeNode findRootNode(TreeNode node) {
				
		if(null != node.getFather()) {
			return findRootNode(node.getFather());
		} else {
			return node;
		}
	}
	
}
