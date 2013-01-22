package com.customized.tools.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * TreeInputConsole
 * 
 * @author kylin
 *
 */
public class TreeInputConsole extends InputConsole {
	
	private final String name;

	private TreeNode currentNode;
	
	/**
	 * A TreeInputConsole has one root node, root node no father node
	 */
	private TreeNode rootNode = new TreeNode("/", "", null, null);
	
	private String cursor = "~";
	
	private String cursorStr ;
	
	private BufferedReader in ;

	public TreeInputConsole(String name, TreeNode currentNode) {
		super();
		this.name = name;
		this.currentNode = currentNode;
		
		// register TreeNode to rootNode, if TreeNode didn't have a parent
		if(null != currentNode && null == currentNode.getFather()){
			currentNode.setFather(rootNode);
			rootNode.getSons().add(currentNode);
		}
		
		updateCursorStr(currentNode);
		
		InputStreamReader converter = new InputStreamReader(System.in);
		in = new BufferedReader(converter);
	}

	public TreeNode getCurrentNode() {
		
		if(null == currentNode) {
			currentNode = getRootNode();
		}
		
		return currentNode;
	}

	private TreeNode getRootNode() {
		return rootNode;
	}

	public void updateCurrentNode(TreeNode currentNode) {
		
		if(null == currentNode) {
			currentNode = getRootNode();
		}
		
		this.currentNode = currentNode;
	}
	
	public void execute() throws IOException {
		
		String pointer = "";
		
		while (true) {
						
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
				handleOther(pointer);
				break;
			}
			
			updateCursorStr(getCurrentNode());		
		}
	}

	protected void handleLS(String pointer) {
		
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
	
	protected void handlePWD(String pointer) {
		
		String tmp = getCurrentNode().getName();
		
		TreeNode node = getCurrentNode();
		while((node = getFatherNode(node)) != null) {
			String old = tmp;
			if(node.getName().compareTo("/") == 0){
				tmp = node.getName() +  old ;
			} else {
				tmp = node.getName() + File.separator + old ;
			}
		}	
		
		println(tmp);
	}
	
	protected void handleCD(String pointer) {

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
	
	protected void handleRM(String pointer) {
		
		String[] array = pointer.split(" ");
		
		if(array.length < 2) {
			promptErrorCommand(pointer);
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
	
	protected void handleADD(String pointer) {
		
		String[] array = pointer.split(" ");
		
		if(array.length != 1) {
			promptErrorCommand(pointer);
			return ;
		}
		
		String name = readString("Enter Node Name:", true);
		String content = readString("Enter Node Content:", false);
		TreeNode node = new TreeNode(name, content, getCurrentNode(), null);
		getCurrentNode().getSons().add(node);
	}
	
	protected void handleHELP(String pointer) {
		println("[<ls>] list all nodes");
		println("[<ls> <-l>] list all nodes with contents");
		println("[<cd> <PATH>] redirect via PATH");
		println("[<pwd>] show current path");
		println("[<rm> <NODE_NAME> <*>] delete node, * hints delete all son nodes");
		println("[<add>] add new node");
	}
	
	protected void handleOther(String pointer) {
		promptErrorCommand(pointer) ;
	}
	
	protected void promptErrorCommand(String pointer) {
		prompt("[" + pointer + "] can not be recognized");
	}
	
	protected String getAbsolutePath() {
				
		String tmp = getCurrentNode().getName();
		
		TreeNode node = getCurrentNode();
		while((node = getFatherNode(node)) != null) {
			String old = tmp;
			tmp = node.getName() + File.separator + old ;
		}			
		
		return File.separator + tmp; 
	}
	
	protected void addTreeNode(TreeNode treeNode) {
		getCurrentNode().addSon(treeNode);
	}
	
	protected void removeTreeNode(String name) {
		
		remove(getCurrentNode().getSons(), name);
	}
	
	private void remove(List<TreeNode> nodes, String name) {
		
		int size = nodes.size();
		
		for(int i = 0 ; i < size ; i ++) {
			if(nodes.get(i).getName().compareTo(name) == 0) {
				nodes.remove(i);
				break;
			}
		}
	}

	private void updateCursorStr(TreeNode node) {
		if(null == node) {
			cursor = "/";
		} else {
			cursor = node.getName() ;
		}
		cursorStr = "[" +name + " " + cursor + "]";
	}

	private List<TreeNode> getPrintNodes() {
		return getCurrentNode().getSons();
	}

	private TreeNode findNode(String name) {
		
		TreeNode result = null;
		
		for(TreeNode node : getCurrentNode().getSons()) {
			if(node.getName().compareTo(name) == 0) {
				result = node ;
				break;
			}
		}
		
		return result;
	}

	private TreeNode getFatherNode(TreeNode node) {
		return node.getFather();
	}

	private Action type(String pointer) {
		
		if(pointer.toLowerCase().startsWith("cd")) {
			return Action.CD ;
		} else if(pointer.toLowerCase().startsWith("ls")) {
			return Action.LS;
		} else if(pointer.toLowerCase().startsWith("pwd")) {
			return Action.PWD;
		} else if(pointer.toLowerCase().startsWith("rm")) {
			return Action.RM;
		} else if(pointer.toLowerCase().startsWith("add")) {
			return Action.ADD;
		} else if(pointer.toLowerCase().startsWith("help")) {
			return Action.HELP;
		} else if(pointer.equals("")){
			return Action.NULL;
		} else {
			return Action.HELP;
		}
		
	}
	
	private enum Action {

		NULL,
		LS,
		CD,
		PWD,
		RM,
		ADD,
		HELP,
		OTHER,
	}
	
}
