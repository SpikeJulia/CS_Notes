
class Tree<T>{

	class TreeNode<T>{
		public T data;
		public TreeNode<T> leftNode;
		public TreeNode<T> rightNode

		public TreeNode(T data){
			this.data = data;
		}
	}

	//递归创建二叉树
	public TreeNode<T> createTree(LinkedList<T> treeData){
		TreeNode<T> root = null;
		T data = treeData.removeFirst();
		if(data != null){
			root = new TreeNode(data);
			root.leftNode.createTree(treeData);
			root.rightNode.createTree(treeData);
		}
		return root;
	}

	//递归先序遍历二叉树
	public void preOrder(TreeNode tree){
		if (tree != null){
			System.out.println(data);
			preOrder(tree.leftNode);
			preOrder(tree.rightNode);
		}
	}

	//递归中序遍历
	public void midOrder(TreeNode tree){
		if(tree != null){
			midOrder(tree.leftNode);
			System.out.println(tree.data);
			midOrder(tree.rightNode);	
		}
	}

	//递归后序遍历
	public void lastOrder(TreeNode tree){
		if(tree != null){
			midOrder(tree.leftNode);
			midOrder(tree.rightNode);
			System.out.println(tree.data);
		}
	}

	//非递归先序遍历
	public void preOrder(TreeNode root){
		Stack<TreeNode> stack = new Stack();
		List<NodeTree> nodes = new ArrayList();
		stack.push(root);
		while(! stack.isEmpty()){
			root = stack.pop();
			nodes.add(root);
			while (root.rightNode ！= null) {
				stack.push(root.rightNode);
				
			}
			while(root.leftNode != null) {
				stack.push(root.leftNode);
			}
		}
		System.out.pirntln(nodes);
	}

	//非递归中序遍历
	public void midOrder(TreeNode root){
		Stack<TreeNode> stack = new Stack();
		List<NodeTree> nodes = new ArrayList();

		stack.push(root);
		while(root != null || !stack.isEmpty()){
			while (root != null) {
				stack.push(root.leftNode);
				root = root.leftNode;
			}

			if (!stack.isEmpty()) {
				root = stack.pop();
				nodes.add(root);
				stack.push(root.rightNode);
			}
		}
	}

	//非递归后序遍历
	public void lastOrder(TreeNode root){
		Stack<TreeNode> stack = new Stack();
		List<NodeTree> nodes = new ArrayList();
		NodeTree pre = null;
		NodeTree node = root;


		while(node != null || !stack.isEmpty()){
			//一次性压入左节点
			while (node != null) {
				stack.push(root);
				root = root.leftNode;
			}

			//获取栈顶元素
			node = stack.peek();

			//如果栈顶节点的右孩子不为空，说明还得把右子树压入栈中，这里需要注意
			//node.right!=pre这个条件，因为我们在遍历的过程中，对于（子树）根节点的判断会存在两次
			//第一次是弹出左孩子节点后，对根节点进行是否有右孩子的判断，如果有，则将右孩子压栈
			//第二次是弹出右孩子节点后，这时候因为循环的原因（代码的原因），我们再次对根节点进行了右孩子判断，
			//所以这里就必须得判断该右孩子节点是否在之前的循环中已经判断过了，如果判断过了，则弹出根节点，否则压入右孩子节点。
			//总的来说，pre节点的作用是用来防止重复遍历右孩子节点的。
			if (node.rightNode != null && node != pre) {
				node = node.rightNode;
			}else{
				node = stack.pop();
				nodes.add(node);
				pre = node;
				node = null;
			}
		}
	}
}

