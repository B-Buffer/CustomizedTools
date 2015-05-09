package com.customized.tools.renderer;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TreeNode<T> implements Iterable<TreeNode<T>> {
	
	private T data;
	
    private TreeNode<T> parent;
    
    private List<TreeNode<T>> children;
    
    public TreeNode(T data) {
        this.data = data;
        this.children = new LinkedList<TreeNode<T>>();
    }
    
    public TreeNode<T> addChild(T child) {
        TreeNode<T> childNode = new TreeNode<T>(child);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }
    
    public TreeNode<T> addChild(TreeNode<T> childNode) {
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }
    
    
	public T getData() {
		return data;
	}
	
	public String getDataAsString() {
		return data.toString();
	}

	public TreeNode<T> getParent() {
		return parent;
	}

	public List<TreeNode<T>> getChildren() {
		return children;
	}

	@Override
	public Iterator<TreeNode<T>> iterator() {
		return children.iterator();
	}



}
