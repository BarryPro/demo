package com.barry.demo.tree;

/**
 * @Description: <p>红黑树
 * 性质1：每个节点要么是红色，要么是黑色
 * 性质2：根节点永远是黑色
 * 性质3：所有的叶子节点都是空节点（即null），并且是黑色的
 * 性质4：每个红色节点的两个子节点都是黑色。（从每个叶子到根的路径上不会有两个连续红色节点）
 * 性质5：从任意节点到其子树中每个叶子节点的路径都包含相同数量的黑色节点
 * （黑色节点的数被称为黑色高度black=height）
 * <p>
 * 性质4结论（黑色高度N）：
 * 1、从根节点到叶子节点的最短路径是N-1
 * 2、最长的路径是2*（N-1）
 * <p>
 * 注意：
 * 红黑树并不是真正的平衡二叉树，但实际应用中，红黑树的统计性能高于平衡二叉树，但极端性能略差
 * <p>
 * 提示：
 * 1、插入（红色）：如果设为黑色就会导致根节点的路径上多一个额外的黑节点，这样将会很难调整，但是插入红色，
 * 可能出现两个连续的红色节点，在通过颜色调换和树的旋转调整即可
 * <p>
 * 例子：
 * JDK TreeMap本身就是一个红黑树的实现</p>
 * @Author: belong.
 * @Date: 2017/7/6.
 */
public class RedBlackTree<T extends Comparable> {

    // 定义红黑树的颜色
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    // 定义红黑树的节点
    static class Node {
        Object data;
        Node parent;
        Node left;
        Node right;
        // 节点的默认颜色是黑色
        boolean color = BLACK;

        public Node(Object data, Node parent, Node left, Node right) {
            this.data = data;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "[data=" + data + ",color=" + color + "]";
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj.getClass() == Node.class) {
                Node target = (Node) obj;
                return data.equals(target.data) &&
                        color == target.color &&
                        left == target.left &&
                        right == target.right;
            }
            return false;
        }
    }

    // 定义根节点
    private Node root;

    // 默认构造器
    public RedBlackTree() {
        root = null;
    }

    // 带参数的构造器
    public RedBlackTree(T o) {
        root = new Node(o, null, null, null);
    }

    // 添加节点
    public void add(T ele) {
        // 如果根节点为空
        if (root == null) {
            root = new Node(ele, null, null, null);
        } else {
            // 从根节点开始遍历
            Node current = root;
            Node parent = null;
            int cmp = 0;
            // 搜索合适的叶子节点，以该叶子节点为父节点添加新节点
            do {
                parent = current;
                cmp = ele.compareTo(current.data);
                // 如果新节点的值大于当前节点的值
                if (cmp > 0) {
                    // 以右节点作为当前节点
                    current = current.right;
                    // 如果新节点小于或等于当前节点
                } else {
                    current = current.left;
                }
            } while (current != null);
            // 创建新节点
            Node newNode = new Node(ele, parent, null, null);
            // 再次判断新值和父节点的大小，看新节点作为parent的左节点还是右节点
            if (cmp > 0) {
                // 新节点作为右节点
                parent.right = newNode;
            } else {
                // 新节点作为左节点
                parent.left = newNode;
            }
            // 修复红黑树
            fixAfterInsertion(newNode);
        }
    }

    /**
     * 插入节点后修复红黑树（进行颜色调换和树旋转）
     * <p>
     * 情形1：新节点N是树的根节点，没有父节点
     * 这种情形下，直接将它设置为黑色以满足性质2
     * <p>
     * 情形2：新节点的父节点P是黑色
     * 这种情形下，新插入的节点是红色的，因此依然满足性质4.而且因为新节点N有两个黑色叶子节点，
     * 但是由于新节点N是红色，通过它的每个子节点的路径依然保持相同的黑色节点数，因此依然满足性质5
     * <p>
     * 情形3：如果父节点P和父节点的兄弟节点U都是红色
     * 这种情况下：程序应该将P节点、U节点都设置成黑色，并将P节点的父节点设为红色（用来保持性质5）
     * 现在，新节点N有了一个黑色的父节点P。由于从P节点、U节点到根节点的任何路径都必须通过G（P节点
     * 的父节点）节点，这些路径上的黑节点数目没有变化（原来有叶子和G节点两个黑色节点，现在有叶子
     * 和P两个黑色节点）
     * 经过情形3处理后，红色的G节点的父节点也可能是红色的、这就违反了性质4，因此还需要对G节点递归进行
     * 整个过程（把G当成新插入的节点进行处理）
     * <p>
     * 情形4：父节点P是红色，而其兄弟节点U是黑色或缺失；且新节点N是父节点P的右子节点，而父节点P又是其
     * 父节点G的左子节点
     * 这种情形下，对新节点和其父节点进行一次左旋转。接着，按情形5处理以前的父节点P（也就是把P当成新插
     * 入的节点）。这将导致某些路径通过它们以前不通过的新节点N或父节点P其中之一，但是这两个节点都是红色，
     * 因此不影响性质5
     * <p>
     * 情形5：父节点P是红色，而其兄弟节点U是黑色或缺少；且新节点N是其父节点的左子节点，而其父节点P又是其
     * 父节点G的左子节点
     * 这种情况下，需要对节点G进行一次右旋转。在旋转产生的树中，以前的父节点P现在是新节点N和节点G的父节点
     * 由于以前的节点G是黑色（否则父节点P就不能是红色），切换以前的父节点P和节点G的颜色，使之满足性质4。
     * 性质5也仍然保持满足，因为通过这3个节点中任何一个的所有路径以前都通过节点G，现在它们都通过以前的父节
     * 点P。在各自的情形下，这都是3个节点中唯一的黑色节点
     *
     * @param x 修复新插入的节点
     */
    private void fixAfterInsertion(Node x) {
        // 新插入的节点的颜色是红色
        x.color = RED;
        // 直到x节点的父节点不是空，且x的父节点不是红色
        while (x != null && x != root && x.parent.color == RED) {
            // 如果x的父节点是其父节的左子树（情形4）
            if (parentOf(x) == leftOf(parentOf(x))) {

            }
        }

    }

    /**
     * 获得指定节点的左子节点
     *
     * @param p
     * @return
     */
    private Node leftOf(Node p) {
        return (p == null ? null : p.left);
    }

    /**
     * 获得指定节点的父节点
     *
     * @param p
     * @return
     */
    private Node parentOf(Node p) {
        return (p == null ? null : p.parent);
    }

    /**
     * 删除操作
     * 红黑树的删除操作比插入操作要稍微复杂一些
     * 步骤：
     * 1、以排序二叉树的方法删除指定的节点
     * 2、进行颜色调节和树旋转，使之满足红黑树的特征
     *
     * @param ele 移除指定的ele元素
     */
    public void remove(T ele) {

    }


}
