package com.customized.tools.renderer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Assume there are 3 l3holder
 * 	  * l3holder_1 - "├──"
 *    * l3holder_2 - "└──"
 *    * l3holder_3 - "   "
 * and 2 l2holder
 *    * l1holder_1 - "│"
 *    * l1holder_2 = " "
 * 
 * Algorithm used for count prefix:
 * 
 * 		prefix = (index -1) * (l1holder + l3holder_3) +  (l3holder + l1holder_2)
 * 
 * For the first section:
 *  index    - the deep of tree
 *  l1holder - if node have buddy node, l1holder_1 should be use, else l1holder_2 should be use
 *  
 * For the second section:
 *  l3holder - if node have buddy node and not the last node to be print, l3holder_1 should be use, else l3holder_2 should be use.
 *
 */
public class TreeRenderer implements Renderer {
	
	// placeholder l3holder_1
	private final String PLACE_HOLDER_1 = "├──" ;
	
	// placeholder l3holder_2
	private final String PLACE_HOLDER_2 = "└──" ;
	
	// placeholder l3holder_3
	private final String PLACE_HOLDER_3 = "   " ;
	
	// placeholder l1holder_1
	private final String PLACE_HOLDER_4 = "│" ;
	
	// placeholder l1holder_2
	private final String PLACE_HOLDER_5 = " " ;
	
	private TreeNode<?> node;
	
	private OutputDevice out;
	
	public TreeRenderer(TreeNode<?> node, OutputDevice out) {
		this.node = node;
		this.out = out;
	}

	@Override
	public void renderer() {
		flush();
	}

	@Override
	public void flush() {
		flush(node, 0);
		cache = null;
	}

	private void flush(TreeNode<?> node, int index) {
		
		out.print(assemblePrefix(node));
		
		out.print(assemble31Holder(index, node));
		
		out.println(node.getDataAsString());
		
		index ++ ;
		
		cacheChildren(index, node);
		
		for(TreeNode<?> leaf : node.getChildren()){
			flush(leaf, index);
		}
		
	}
	
	private void cacheChildren(Integer key, TreeNode<?> node) {
		
		if(node.getChildren().size() <= 1) {
			return;
		}
		
		HashSet<TreeNode<?> > set = cache.get(key);
		if(set == null){
			set = new HashSet<TreeNode<?>> ();
			cache.put(key, set);
		}
		for(TreeNode<?> leaf : node.getChildren()) {
			set.add(leaf);
		}
	}

	Map<Integer, HashSet<TreeNode<?> >> cache = new HashMap<Integer, HashSet<TreeNode<?>>> ();

	private String assemble31Holder(int index, TreeNode<?> node) {
		
		String prefix = "" ;
		
		if(index <= 0 || node.getParent() == null) {
			return "";
		}
		
		if(existBuddy(node) && !isLast(node, index)){
			prefix = PLACE_HOLDER_1 + PLACE_HOLDER_5 ;
		} else {
			prefix = PLACE_HOLDER_2 + PLACE_HOLDER_5 ;
		}
		return prefix;
	}
	
	private boolean isLast(TreeNode<?> node, Integer key){
		
		HashSet<TreeNode<?>> set = cache.get(key);
		if(set == null) {
			return true ;
		}
		
		set.remove(node);
		
		if(set.size() >= 1) {
			return false;
		}
		
		return true;
	}

	private String assemblePrefix(TreeNode<?> node) {
		if(node.getParent() == null) {
			return "";
		} else {
			String prefix = "";
			TreeNode<?> tnode = node;
			while((tnode = tnode.getParent()) != null){
				String holder = assemble13Holder(tnode) ;
				prefix = holder + prefix;
			}
			return prefix;
		}
	}
	
	private String assemble13Holder( TreeNode<?> node) {
		
		if(node.getParent() == null) {
			return "";
		}
		
		String prefix = "" ;
		
		if(existBuddy(node)) {
			prefix = PLACE_HOLDER_4 + PLACE_HOLDER_3  ;
		} else {
			prefix = PLACE_HOLDER_5 + PLACE_HOLDER_3  ;
		}
		
		return prefix ;
	}
	
	private boolean existBuddy(TreeNode<?> node) {
		
		if(node.getParent() == null){
			return false ;
		}
		
		return node.getParent().getChildren().size() > 1;
	}

}
