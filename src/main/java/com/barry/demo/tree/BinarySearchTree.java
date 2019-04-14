package com.barry.demo.tree;

/*
 * Java实现二叉搜索树
 * 1.若他的左子树不空，则左子树上所有节点的值均小于它的根节点的值；
   2.若他的右子树不空，则右子树上所有节点的值均大于它的根节点的值。
   3.它的左、右子树也分别为排序二叉树。
 */
public class BinarySearchTree<T extends Comparable<? super T>> {
	//结点数据结构
	static class BinaryNode<T>{
		T data;
		BinaryNode<T> left;
		BinaryNode<T> right;
		public BinaryNode(T data){
			this(data,null,null);

		}
		public BinaryNode(T data,BinaryNode<T> left,BinaryNode<T> right){
			this.data=data;
			this.left=left;
			this.right=right;

		}
		public BinaryNode(){
			data=null;
			this.left=left;
			this.right=right;
		}

	}
	private BinaryNode<T> rootTree;

	//构造一颗空的二叉查找树
	public BinarySearchTree(){
		rootTree=null;
	}
	//清空二叉查找树
	public void clear(){
		rootTree=null;
	}
	//判断是否为空
	public boolean isEmpty(){
		return rootTree==null;
	}

	//找到二叉查找树中的最小值
	public T findMin(){
		if(isEmpty()){
			System.out.println("二叉树为空");
			return null;

		}else{
			return findMin(rootTree).data;
		}
	}
	//查询出最小元素所在的结点
	public BinaryNode<T> findMin(BinaryNode<T> node){
		if(node==null){
			return null;
		}else if(node.left==null){
			return node;
		}
		return findMin(node.left);//递归查找

	}
//	//查询二叉查找树中的最大值
//	public T findMax(){
//		if(isEmpty()){
//			System.out.println("二叉树为空");
//			return null;
//		}else{
//			return findMax(rootTree).data;
//		}
//
//	}
//	//查询出最大元素所在的结点
//	public BinaryNode<T> findMax(BinaryNode<T> node){//也可以向findMin似得，用递归写
//		if(node!=null){
//			while(node.right!=null){
//				node=node.right;
//			}
//		}
//		return node;
//	}
	//查找指定的元素，默认从根节点处开始查询
//	public boolean contains(T t){
//		return contains(t,rootTree);
//	}
//	//从某个节点处开始查找元素
//	public boolean contains(T t,BinaryNode<T> node){
//		if(node==null){
//			return false;
//		}
//		int result=t.compareTo(node.data);
//		if(result>0){
//			return contains(t,node.right);//递归查询
//		}else if(result<0){
//			return contains(t,node.left);
//		}else{
//			return true;
//		}
//
//	}
	//插入元素
	public void insert(T t){
		rootTree=insert(t,rootTree);
	}

	//在某个位置开始判断插入元素
	public BinaryNode<T> insert (T t,BinaryNode<T> node){

		if(node==null){
			//新构造一个二叉查找树
			return new BinaryNode<T>(t,null,null);
		}
		int result=t.compareTo(node.data);

		if(result<0){
			node.left=insert(t,node.left);

		}else if(result>0){
			node.right=insert(t,node.right);
		}
		return node;//递归添加
	}
	//删除元素
	public void remove(T t){
		rootTree=remove(t,rootTree);
	}
	/*
	 * 			 6
	 * 		  /     \
	 * 		2        8
	 *     /  \     /  \
	 *    1    4       9
	 *        /  
	 *       3  
	 * 
	 */
	//在某个位置开始判断删除某个结点
	public BinaryNode<T> remove(T t,BinaryNode<T> node){
		if(node==null){
			return node;//没找到
		}
		int result=t.compareTo(node.data);//将要删除的节点和当前节点的值进行比较
		if(result>0){//要删除的节点大于当前节点
			node.right=remove(t,node.right);//将要删除的节点和右节点比较
		}else if(result<0){//要比较的节点小于当前节点
			node.left=remove(t,node.left);////将要删除的节点和左节点比较
			
		}else if(result==0&& node.left!=null && node.right!=null){//找到后，假如要删除的节点左右子节点都存在
			
			node.data=findMin(node.right).data;//找到最小节点之后 把数据给其要删除的节点
			node.right=remove(node.data,node.right);//删除这个最小节点，当然也需要经历 ：找到最小节点之后 把数据给其要删除的节点
			
		}else{//找到要删除的节点 result=0  或是,//只有左子树，或只有右子树 或左右子树都没有 把他的节点删除，
			//删除节点的最简单形式
			
			
			node=(node.left!=null)?node.left:node.right;//
		}
		return node;			
	}
	/*
	 * 			 6
	 * 		  /     \
	 * 		2        8
	 *     /  \     /  \
	 *    1    4       9
	 *        /  
	 *       3  
	 * 
	 *  1.若他的左子树不空，则左子树上所有节点的值均小于它的根节点的值；
  		2.若他的右子树不空，则右子树上所有节点的值均大于它的根节点的值。
   		3.它的左、右子树也分别为排序二叉树。
	 */
	public BinaryNode<Integer> init(){
		BinaryNode<Integer> node3=new BinaryNode<Integer>(3);
		BinaryNode<Integer> node1=new BinaryNode<Integer>(1);
		BinaryNode<Integer> node4=new BinaryNode<Integer>(4,node3,null);
		BinaryNode<Integer> node2=new BinaryNode<Integer>(2,node1,node4);
		BinaryNode<Integer> node9=new BinaryNode<Integer>(9);
		BinaryNode<Integer> node8=new BinaryNode<Integer>(8,null,node9);
		
		BinaryNode<Integer> root=new BinaryNode<Integer>(6,node2,node8);
		return root;
	}

	//DLR 先序遍历
	public void preOrder(BinaryNode node){
		if(node!=null){
			System.out.println(node.data);
			preOrder(node.left);
			preOrder(node.right);
		}
	}
	public static void main(String[] args) {
		BinarySearchTree searchTree=new BinarySearchTree();
		BinaryNode<Integer> node=searchTree.init();
		searchTree.rootTree=node;
		searchTree.preOrder(searchTree.rootTree);
		searchTree.remove(2);
		System.out.println("-----删除后---------");
		searchTree.preOrder(searchTree.rootTree);

	}

}
