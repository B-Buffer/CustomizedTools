package com.customized.tools.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	
	private boolean isDebug = false ;
	
	public TreeInputConsole(String name, TreeNode currentNode){
		this(name, currentNode, false);
	}


	public TreeInputConsole(String name, TreeNode currentNode, Boolean isDebug) {
		super();
		this.name = name;
		this.currentNode = currentNode;
		this.isDebug = isDebug ;
		
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

	public boolean isDebug() {
		return isDebug;
	}

	/**
	 * Threshold for debug TreeNode information.
	 *   if isDebug is true, all exist TreeNode will be printed
	 *   if isDebug is false, debug logic will be ignored
	 * @param isDebug
	 */
	public void setDebug(boolean isDebug) {
		this.isDebug = isDebug;
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
	
	public void start() throws IOException {
		
		isRunning = true;
		
		String pointer = "";
		
		while (isRunning) {
			
			printWholeTreeNodes();
						
			// always print cursor string, simulate Linux Commands
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
	
	private boolean isRunning;
	
	public void stop() {
		isRunning = false;
	}

	private PrintWriter out = null ;
	
	private void printWholeTreeNodes() {

		if (!isDebug())
			return;
		
		if(null == out) {
			try {
				prompt("Debug TreeNode Content is enable");
				File file = new File(System.currentTimeMillis() + ".log");
				out = new PrintWriter(new FileWriter(file), true);
				prompt("TreeNode Content Stack will output to " + file.getName());
			} catch (IOException e) {
				throw new TreeInputConsoleException("instantiate PrintWriter error", e);
			}
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss SSS]").format(new Date()) + " TreeNode Content Stack:");
		sb.append("\n");
		recursiveOrder(rootNode, sb, 0);
		
		out.print(sb.toString());
		out.flush();
	}
	
	private void recursiveOrder(TreeNode node, StringBuffer sb, int index) {
		
		String preBlank = countBlank(index ++);
		sb.append(preBlank + node.getName() + " - [" + node.getContent() + "]" );
		sb.append("\n");
		
		for(TreeNode son : node.getSons()) {
			recursiveOrder(son, sb, index);
		}
		
	}

	private String countBlank(int size) {

		String tab = "    ";
		String sum = "";
		for(int i = 0 ; i < size ; i ++) {
			sum += tab ;
		}
		return sum;
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
		println(getAbsolutePath());
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
			if(node.getName().compareTo("/") == 0){
				tmp = node.getName() +  old ;
			} else {
				tmp = node.getName() + File.separator + old ;
			}
		}	
		
		return tmp; 
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
		} else if(pointer.toLowerCase().equals("pwd")) {
			return Action.PWD;
		} else if(pointer.toLowerCase().startsWith("rm")) {
			return Action.RM;
		} else if(pointer.toLowerCase().equals("add")) {
			return Action.ADD;
		} else if(pointer.toLowerCase().equals("help")) {
			return Action.HELP;
		} else if(pointer.equals("")){
			return Action.NULL;
		} else {
			return Action.OTHER;
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
