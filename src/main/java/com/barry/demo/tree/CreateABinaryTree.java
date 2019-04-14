package com.barry.demo.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;

import java.util.List;
import java.util.Queue;
import java.util.Stack;






public class CreateABinaryTree {
	private TreeNode root;

	public CreateABinaryTree() {
	}

	/**
	 * Constructs a binary tree in order of elements in an array.
	 * After the number of nodes in the level have maxed, the next 
	 * element in the array would be a child of leftmost node.
	 * 
	 * http://codereview.stackexchange.com/questions/31334/least-common-ancestor-for-binary-search-tree/31394?noredirect=1#comment51044_31394
	 */
	public CreateABinaryTree(List<Integer> items) {
		this();
		createTreeByDeque(items);
	}
	
	public CreateABinaryTree(Integer[] items) {
		this();
		this.createTreeByArray(items);
	}

	private static class TreeNode {
		TreeNode left;
		int value;
		TreeNode right;

		TreeNode(TreeNode left, int value, TreeNode right) {
			this.left = left;
			this.value = value;
			this.right = right;
		}
	}

	private void createTreeByDeque (List<Integer> items) {   
		root = new TreeNode(null, items.get(0), null);

		Queue<TreeNode> queue = new ArrayDeque<TreeNode>();
		queue.add(root);

		int half = items.size() / 2;

		for (int i = 0; i < half; i++) {
			if (items.get(i) != null) {
				TreeNode current = queue.poll();//返回root节点，并删除它，如此反复，最终把所有节点都串到root上
				int left = 2 * i + 1;
				int right = 2 * i + 2;

				if (items.get(left) != null) {
					current.left = new TreeNode(null, items.get(left), null);
					queue.add(current.left);
				}
				if (right < items.size() && items.get(right) != null) {
					current.right = new TreeNode(null, items.get(right), null);
					queue.add(current.right);
				}
			}
		}
		
	
	}
	
	private void createTreeByArray (Integer[] items) {   
		root = new TreeNode(null, items[0], null);

		Queue<TreeNode> queue = new ArrayDeque<TreeNode>();
		queue.add(root);

		int half = items.length / 2;

		for (int i = 0; i < half; i++) {
			if (items[i] != null) {//存在元素
				TreeNode current = queue.poll();//返回当前元素并移走
				int left = 2 * i + 1;//左节点
				int right = 2 * i + 2;//右节点

				if (items[left] != null) {//数组中存左节点
					current.left = new TreeNode(null, items[left], null);
					queue.add(current.left);
				}
				if (right < items.length && items[right] != null) {
					current.right = new TreeNode(null, items[right], null);
					queue.add(current.right);
				}
			}
		}
		
	
	}
	/*
	 * 深度优先，用的Stack
	 * 
	 * 
	 * 	 				  0
	 *  				/  \
	 *  			    1  2
	 *  			   / \ / \
	 *                3  4 5 6
	 *               /\  /
	 *              7  8 9
	 * 结果是：0 1 3 7 8 4 9 2 5 6       DLR      
	 */
	public void depthOrderTraversal(){  
		if(root==null){  
			System.out.println("empty tree");  
			return;  
		}        
		//Stack：后进先出
		Stack<TreeNode> stack=new Stack<TreeNode>();  //引用ArrayDeque也行或LinkedList
		
		stack.push(root);     
		System.out.print("深度优先：");  
		while(stack.isEmpty()==false){  
			TreeNode node=stack.pop();  
			System.out.print(node.value+"    ");  
			if(node.right!=null){  //先根 在左 在右 DLR，如果颠倒此句，就是DRL
				stack.push(node.right);  
			} 
			if(node.left!=null){  
				stack.push(node.left);  
			} 
			
		}  
		System.out.print("\n");  
	}  
	/*
	 * 
	 * 广度优先遍历 
	 * 采用非递归实现 
	 * 需要辅助数据结构：队列 
	 * 
	 *  				 0
	 *  				/  \
	 *  			    1  2
	 *  			   / \ / \
	 *                3  4 5 6
	 *               /\  /
	 *              7  8 9
	 *  结果是：0 1 2 3 4 5 6 7 8 9
	 */
	public void levelOrderTraversal(){  
		if(root==null){  
			System.out.println("空·树");  
			return;  
		}  
		ArrayDeque<TreeNode> queue=new ArrayDeque<TreeNode>();  
		queue.add(root);  
		System.out.print("广度优先：");  
		while(queue.isEmpty()==false){  
			TreeNode node=queue.remove();  
			System.out.print(node.value+"    ");  
			if(node.left!=null){  
				queue.add(node.left);  //队列方式：先进先出
			}  
			if(node.right!=null){  
				queue.add(node.right);  
			}  
		}  
		System.out.print("\n");  
	}  

	public static void main(String[] args) {
		List<Integer> list=new ArrayList<Integer>();
		list.add(0);
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(6);
		list.add(7);
		list.add(8);
		list.add(9);
		Integer [] is=new Integer[]{0,1,2,3,4,5,6,7,8,9};
		CreateABinaryTree c=new CreateABinaryTree();
		//c.createTreeByDeque(list);
		c.createTreeByArray(is);
		c.levelOrderTraversal();
		c.depthOrderTraversal();
	}
}
