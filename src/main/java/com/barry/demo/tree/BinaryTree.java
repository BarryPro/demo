package com.barry.demo.tree;

public class BinaryTree {
	public void printNode(TreeNode<String> node){
		System.out.println(node.getData()+" ");

    }
	class TreeNode<T>{
		private T data;
		private TreeNode<T> leftNode;
		private TreeNode<T> rightNode;
		public TreeNode(T data,TreeNode<T> leftNode,TreeNode<T> rightNode){
			this.data=data;
			this.leftNode=leftNode;
			this.rightNode=rightNode;
		}
		public T getData(){
			return data;
			
		}
		public void setData(T data){
			
		}
		public TreeNode<T> getLeftNode(){
			return leftNode;
		}
		public void setLeftNode(TreeNode<T> leftNode){
			this.leftNode=leftNode;
			
		}
		public TreeNode<T> getRightNode(){
			return rightNode;
		}
		public void setRightNode(TreeNode<T> rightNode){
			this.rightNode=rightNode;
		}
		
	}
	
	
	//初始化二叉树
	/*
	 * 				    A
	 * 				/		\
	 * 			 B             C
	 * 			/ \          /   \
	 * 		  D    E       F       G
	 *            / \       \     /
	 * 			 H   I       J   P
	 *  
	 * 
	 * 
	 * 
	 */
	public  TreeNode<String> init(){
		TreeNode<String> D=new TreeNode<String>("D",null,null);
		TreeNode<String> H=new TreeNode<String>("H",null,null);
		TreeNode<String> I=new TreeNode<String>("I",null,null);
		TreeNode<String> J=new TreeNode<String>("J",null,null);
		TreeNode<String> P=new TreeNode<String>("P",null,null);
		TreeNode<String> G=new TreeNode<String>("G",P,null);
		TreeNode<String> F=new TreeNode<String>("F",null,J);
		TreeNode<String> E=new TreeNode<String>("E",H,I);
		TreeNode<String> B=new TreeNode<String>("B",D,E);
		TreeNode<String> C=new TreeNode<String>("C",F,G);
		TreeNode<String> A=new TreeNode<String>("A",B,C);
		return A;
	}
	/*
	 * 				    A
	 * 				/		\
	 * 			 B             C
	 * 			/ \          /   \
	 * 		  D    E       F       G
	 *            / \       \     /
	 * 			 H   I       J   P
	 *  
	 * 
	 * 
	 * 
	 */
	//先序遍历DLR(ABDEHI CFJGP)
	public void xianIterator(TreeNode<String> node){
		this.printNode(node);//D
		if(node.getLeftNode()!=null){//L
			this.xianIterator(node.getLeftNode());
			
		}
		if(node.getRightNode()!=null){
			this.xianIterator(node.getRightNode());
			
		}
	}
	/*
	 * 				    A
	 * 				/		\
	 * 			 B             C
	 * 			/ \          /   \
	 * 		  D    E       F       G
	 *            / \       \     /
	 * 			 H   I       J   P
	 *  
	 * 
	 * 
	 * 
	 */
	//中序遍历二叉树LDR,所有的节点都是LDR (DBHEIA FJCPG)
	public void zhongIterator(TreeNode<String> node){
		if(node.getLeftNode()!=null){//L
			this.zhongIterator(node.getLeftNode());
		}
		this.printNode(node);//D
		if(node.getRightNode()!=null){//R
			this.zhongIterator(node.getRightNode());
		}
		
	}
	/*
	 * 				    A
	 * 				/		\
	 * 			 B             C
	 * 			/ \          /   \
	 * 		  D    E       F       G
	 *            / \       \     /
	 * 			 H   I       J   P
	 *  
	 * 
	 * 
	 * 
	 */
	//后序遍历二叉树LRD(DHIEB JFPGCA)
	
	public void houIterator(TreeNode<String> node){
		if(node.getLeftNode()!=null){//L
			this.houIterator(node.getLeftNode());
		}
		if(node.getRightNode()!=null){//R
			this.houIterator(node.getRightNode());
		}
		this.printNode(node);//D
	}
	public static void main(String[] args) {
		BinaryTree binaryTree=new BinaryTree();
		TreeNode<String> node=binaryTree.init();
		System.out.println("先序遍历DLR的情况");
		binaryTree.xianIterator(node);
		
		System.out.println("中序LDR遍历的情况");
		binaryTree.zhongIterator(node);
		
		System.out.println("后序遍历LRD的情况");
		binaryTree.houIterator(node);
	}
}
