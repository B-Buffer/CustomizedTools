package com.customized.tools.cli;

public class TreeNode {

	private boolean isRoot;
	
	private boolean isLeaf;
	
	private String content;
	
	private TreeNode father;
	
	private TreeNode son;

	public TreeNode(String content, TreeNode father, TreeNode son) {
		super();
		this.content = content;
		this.father = father;
		this.son = son;
		
		if(null == son) {
			setLeaf(true);
		}
		
		if(null == father) {
			setRoot(true);
		}
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

	public void setFather(TreeNode father) {
		this.father = father;
	}

	public TreeNode getSon() {
		return son;
	}

	public void setSon(TreeNode son) {
		this.son = son;
	}
}
