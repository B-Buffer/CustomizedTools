package com.customized.tools.cli;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
	
	private final String name;

	private boolean isRoot;
	
	private boolean isLeaf;
	
	private String content;
	
	private final TreeNode father;
	
	private List<TreeNode> sons;

	public TreeNode(String name, String content, TreeNode father, TreeNode son) {
		super();
		this.name = name;
		this.content = content;
		this.father = father;
		sons = new ArrayList<TreeNode>();
		
		addSon(son);
		
		updateLeaf();
		
		if(null == name) {
			throw new TreeInputConsoleException("TreeNode name can not be null");
		}
		
		if(null == father) {
			setRoot(true);
		}
	}

	private void updateLeaf() {
		if (sons.size() == 0)
			setLeaf(true);
	}

	public String getName() {
		return name;
	}

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public TreeNode getFather() {
		return father;
	}

	public List<TreeNode> getSons() {
		return sons;
	}

	public void setSons(List<TreeNode> sons) {
		this.sons = sons;
		updateLeaf();
	}
	
	public TreeNode addSon(TreeNode son) {
		
		if(null == son) {
			return this;
		}
		
		sons.add(son);
		updateLeaf();
		
		return this;
	}

	public String toString() {
		return getName();
	}

	
}
