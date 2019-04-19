public class BinaryTree {

    private Node root;

    public static class Node {
        Node left;
        Node right;
        Integer value;

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }

    public boolean nodeExists(Node node) {
        return node != null && node.value != null;
    }

    public void createNode(Node node, int value) {
        node.left = new Node();
        node.right = new Node();
        node.value = value;
    }

    public void insert(Node node, int value) {
        if (!nodeExists(node)) createNode(node, value);
        else if (value < node.value) insert(node.left, value);
        else if (value >= node.value) insert(node.right, value);
    }

    public Node search(Node node, int value) {
        if (!nodeExists(node)) return null;
        if (node.value == value) return node;
        if (value < node.value) return search(node.left, value);
        return search(node.right, value);
    }

    Node getMin(Node node) {
        if (!nodeExists(node)) return null;
        if (!nodeExists(node.left)) return null;
        return getMin(node.left);
    }

    Node getMax(Node node) {
        if (!nodeExists(node)) return null;
        if (!nodeExists(node.right)) return null;
        return getMin(node.right);
    }

    /**
     * Симметричный обход дерева.
     * Обход: левый - корень - правый
     * <pre>
     *     2
     *    / \    =>  1 2 3
     *   1   3
     * <pre/>
     *
     * @param node корень дерева
     */
    public void inOrderTraversal(Node node) {
        if (!nodeExists(node)) return;
        inOrderTraversal(node.left);
        System.out.print(node.value);
        inOrderTraversal(node.right);
    }

    /**
     * Обратный обход дерева.
     * Обход: левый - правый - корень
     * <pre>
     *     2
     *    / \    =>  1 3 2
     *   1   3
     * <pre/>
     *
     * @param node корень дерева
     */
    public void postOrderTraversal(Node node) {
        if (!nodeExists(node)) return;
        postOrderTraversal(node.left);
        postOrderTraversal(node.right);
        System.out.print(node.value);
    }

    /**
     * Прямой обход дерева.
     * Обход: корень - левый - правый
     * <pre>
     *     2
     *    / \    =>  2 1 3
     *   1   3
     * <pre/>
     *
     * @param node корень дерева
     */
    public void preOrderTraversal(Node node) {
        if (!nodeExists(node)) return;
        System.out.print(node.value);
        preOrderTraversal(node.left);
        preOrderTraversal(node.right);
    }


    /**
     * Удаление узла с одним ребенком
     */
    private void removeNodeWithOneOrZeroChild(Node nodeToDelete) {
        Node childOrNull = getChildOrNull(nodeToDelete);
        transplantNode(nodeToDelete, childOrNull);
    }


    /**
     * Перенос узла fromNode в узел toNode
     *
     * @param fromNode переносимый узел
     * @param toNode   целевой для переноса узел
     */
    void transplantNode(Node toNode, Node fromNode) {
        toNode.value = fromNode.value;
        toNode.left = fromNode.left;
        toNode.right = fromNode.right;
    }

    /**
     * Получение количества детей у узла
     *
     * @param node узел, количество детей которого будет возвращено
     */
    int getChildrenCount(Node node) {
        int count = 0;
        if (nodeExists(node.left)) ++count;
        if (nodeExists(node.right)) ++count;
        return count;
    }

    /**
     * Получение ребенка узла или пустой ссылки
     *
     * @param node узел, ребенок которого будет возвращен
     */
    Node getChildOrNull(Node node) {
        return nodeExists(node.left)
                ? node.left
                : node.right;
    }

    /**
     * Удаление узла из дерева
     *
     * @param root  корень дерева
     * @param value значение удаляемого узла
     */
    public boolean remove(Node root, int value) {
        Node nodeToDelete = search(root, value);
        if (!nodeExists(nodeToDelete)) return false;

        int childrenCount = getChildrenCount(nodeToDelete);
        if (childrenCount < 2) {
            removeNodeWithOneOrZeroChild(nodeToDelete);
        } else {
            Node minNode = getMin(nodeToDelete.right);
            nodeToDelete.value = minNode.value;
            removeNodeWithOneOrZeroChild(minNode);
        }
        return true;
    }




}
