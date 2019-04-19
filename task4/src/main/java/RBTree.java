import java.util.function.Function;

/**
 * <strong>Красно-чёрное дерево</strong> - бинарное поисковое дерево, у которого каждому узлу сопоставлен
 * дополнительный атрибут — цвет и для которого выполняются следующие свойства:
 * <ol>
 * <li> Каждый узел промаркирован красным или чёрным цветом</li>
 * <li> Корень и конечные узлы (листья) дерева — чёрные</li>
 * <li> У красного узла родительский узел — чёрный</li>
 * <li> Все простые пути из любого узла x до листьев содержат одинаковое количество чёрных узлов</li>
 * <li> Чёрный узел может иметь чёрного родителя</li>
 * </ol>
 *
 * @see "http://www.mkurnosov.net/teaching/uploads/DSA/dsa-fall-lecture4.pdf"
 */
public class RBTree<Key extends Comparable<Key>, Value> {

    private int size;
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    private Node<Key, Value> root;

    public class Node<Key, Value> implements TreePrinter.PrintableNode {
        private Key key;
        private Value value;
        private Node<Key, Value> left;
        private Node<Key, Value> right;
        private Node<Key, Value> parent;
        private boolean color = BLACK;

        public Node(Key key, Value value, Node<Key, Value> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        public Key getKey() {
            return key;
        }

        public void setKey(Key key) {
            this.key = key;
        }

        public Value getValue() {
            return value;
        }

        public void setValue(Value value) {
            this.value = value;
        }

        public Node<Key, Value> getLeft() {
            return left;
        }


        public void setLeft(Node<Key, Value> left) {
            this.left = left;
        }

        public Node<Key, Value> getRight() {
            return right;
        }


        public void setRight(Node<Key, Value> right) {
            this.right = right;
        }

        public boolean isColor() {
            return color;
        }

        public String getColor() {
            return color ? "b" : "r";
        }

        public void setColor(boolean color) {
            this.color = color;
        }

        public String toString() {
            return key + "=" + value + " (" + getColor() + ")";
        }

        @SuppressWarnings("unchecked")
        private Node<Key, Value>[] getChildren() {
            return new Node[]{left, right};
        }

        @Override
        public String getText() {
            return key + ":" + getColor();
        }
    }

    public Node<Key, Value> getRoot() {
        return this.root;
    }

    private Node<Key, Value> getFirstNode() {
        Node<Key, Value> node = root;
        if (node != null) {
            while (node.left != null)
                node = node.left;
        }
        return node;
    }

    private Node<Key, Value> getLastNode() {
        Node<Key, Value> node = root;
        if (node != null)
            while (node.right != null)
                node = node.right;
        return node;
    }

    private boolean colorOf(Node<Key, Value> node) {
        return (node == null ? BLACK : node.color);
    }

    private Node<Key, Value> parentOf(Node<Key, Value> node) {
        return (node == null ? null : node.parent);
    }

    private void setColor(Node<Key, Value> node, boolean c) {
        if (node != null)
            node.color = c;
    }

    private Node<Key, Value> leftOf(Node<Key, Value> node) {
        return (node == null) ? null : node.left;
    }

    private Node<Key, Value> rightOf(Node<Key, Value> node) {
        return (node == null) ? null : node.right;
    }

    private void rotateLeft(Node<Key, Value> p) {
        if (p != null) {
            Node<Key, Value> r = p.right;
            p.right = r.left;
            if (r.left != null)
                r.left.parent = p;
            r.parent = p.parent;
            if (p.parent == null)
                root = r;
            else if (p.parent.left == p)
                p.parent.left = r;
            else
                p.parent.right = r;
            r.left = p;
            p.parent = r;
        }
    }

    private void rotateRight(Node<Key, Value> p) {
        if (p != null) {
            Node<Key, Value> l = p.left;
            p.left = l.right;
            if (l.right != null) l.right.parent = p;
            l.parent = p.parent;
            if (p.parent == null)
                root = l;
            else if (p.parent.right == p)
                p.parent.right = l;
            else p.parent.left = l;
            l.right = p;
            p.parent = l;
        }
    }

    public void put(Key key, Value value) {
        if (key == null)
            throw new NullPointerException();

        Node<Key, Value> t = this.root;
        if (t == null) {
            this.root = new Node<>(key, value, null);
            size = 1;
            return;
        }

        int cmp;
        Node<Key, Value> parent;
        do {
            parent = t;
            cmp = key.compareTo(t.key);
            if (cmp < 0)
                t = t.left;
            else if (cmp > 0)
                t = t.right;
            else {
                t.setValue(value);
                return;
            }
        } while (t != null);

        Node<Key, Value> e = new Node<>(key, value, parent);
        if (cmp < 0)
            parent.left = e;
        else
            parent.right = e;
        fixAfterInsertion(e);
        size++;
    }

    private void fixAfterInsertion(Node<Key, Value> x) {
        x.color = RED;

        while (x != null && x != root && x.parent.color == RED) {
            if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
            /*x в левом поддереве*/
                Node<Key, Value> y = rightOf(parentOf(parentOf(x))); // y - дядя x
                if (colorOf(y) == RED) {  // если дядя x красный
                    /* случай 1 */
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == rightOf(parentOf(x))) {
                        /* случай 2 --> 3*/
                        x = parentOf(x);
                        rotateLeft(x);
                    }
                    /* случай 3 */
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    rotateRight(parentOf(parentOf(x)));
                }
            } else {
                /* x в правом поддереве - все тоже, только зеркально */
                Node<Key, Value> y = leftOf(parentOf(parentOf(x))); // y - дядя x = 15
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == leftOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateRight(x);
                    }
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    rotateLeft(parentOf(parentOf(x)));
                }
            }
        }
        root.color = BLACK;
    }


    /**
     * Удаление узла и последующая балансировка
     */
    public void delete(Key key) {
        Node<Key, Value> p = getNode(key);
        size--;

        Node<Key, Value> replacement = (p.left != null ? p.left : p.right);

        if (replacement != null) {
            // Link replacement to parent
            replacement.parent = p.parent;
            if (p.parent == null)
                root = replacement;
            else if (p == p.parent.left)
                p.parent.left = replacement;
            else
                p.parent.right = replacement;

            p.left = p.right = p.parent = null;

            if (p.color == BLACK)
                fixAfterDeletion(replacement);
        } else if (p.parent == null) { // если это корень
            root = null;
        } else { //  если у p нет детей
            if (p.color == BLACK)
                fixAfterDeletion(p);

            if (p.parent != null) {
                if (p == p.parent.left)
                    p.parent.left = null;
                else if (p == p.parent.right)
                    p.parent.right = null;
                p.parent = null;
            }
        }
    }

    private Node<Key, Value> getNode(Key key) {
        Node<Key, Value> p = root;
        while (p != null) {
            int cmp = key.compareTo(p.key);
            if (cmp < 0)
                p = p.left;
            else if (cmp > 0)
                p = p.right;
            else
                return p;
        }
        return null;
    }


    private void fixAfterDeletion(Node<Key, Value> x) {
        while (x != root && colorOf(x) == BLACK) {
            if (x == leftOf(parentOf(x))) { // если удаляемый node - левый ребенок
                Node<Key, Value> sib = rightOf(parentOf(x)); // получить его брата(правого ребенка)

                if (colorOf(sib) == RED) {
                    setColor(sib, BLACK);
                    setColor(parentOf(x), RED);
                    rotateLeft(parentOf(x));
                    sib = rightOf(parentOf(x));
                }

                // если у брата оба потомка черные
                if (colorOf(leftOf(sib)) == BLACK &&
                        colorOf(rightOf(sib)) == BLACK) {
                    setColor(sib, RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(rightOf(sib)) == BLACK) {
                        setColor(leftOf(sib), BLACK);
                        setColor(sib, RED);
                        rotateRight(sib);
                        sib = rightOf(parentOf(x));
                    }
                    setColor(sib, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(rightOf(sib), BLACK);
                    rotateLeft(parentOf(x));
                    x = root;
                }
            } else { //  если удаляемый node - правый ребенок (симметрично)
                Node<Key, Value> sib = leftOf(parentOf(x));

                if (colorOf(sib) == RED) {
                    setColor(sib, BLACK);
                    setColor(parentOf(x), RED);
                    rotateRight(parentOf(x));
                    sib = leftOf(parentOf(x));
                }

                if (colorOf(rightOf(sib)) == BLACK &&
                        colorOf(leftOf(sib)) == BLACK) {
                    setColor(sib, RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(leftOf(sib)) == BLACK) {
                        setColor(rightOf(sib), BLACK);
                        setColor(sib, RED);
                        rotateLeft(sib);
                        sib = leftOf(parentOf(x));
                    }
                    setColor(sib, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(leftOf(sib), BLACK);
                    rotateRight(parentOf(x));
                    x = root;
                }
            }
        }
        setColor(x, BLACK);
    }

    public boolean containsKey(Key key) {
        return getNode(key) != null;
    }


    // *************************************************
    // Set operations
    // *************************************************

    /**
     * @param node is root of tree or root of subtree
     */
    public void unite(Node<Key, Value> node) {

        if (node == null) return;
        unite(node.getLeft());
        unite(node.getRight());
        put(node.key, node.value);
    }

    /**
     * @param node is root of tree or root of subtree
     */
    public void differ(Node<Key, Value> node) {
        if (node == null) return;
        differ(node.getLeft());
        differ(node.getRight());
        if (containsKey(node.getKey())) {
            delete(node.getKey());
        }
    }

    /**
     * @param node is root of tree or root of subtree
     */
    public void intersect(Node<Key, Value> node) {
        if (node == null) return;
        intersect(node.getLeft());
        intersect(node.getRight());
        if (containsKey(node.key)) {
            put(node.key, node.value);
        }
    }

    /**
     * @param node is root of tree or root of subtree
     */
    public void symDiffer(Node<Key, Value> node) {
        if (node == null) return;
        if (containsKey(node.getKey()))
            delete(node.getKey());
        else
            put(node.getKey(), node.getValue());

        symDiffer(node.getLeft());
        symDiffer(node.getRight());
    }

    public void postOrderTraversal(Node<Key, Value> node,
                                   Function<Node<Key, Value>, Object> func) {
        if (node == null) return;
        postOrderTraversal(node.getLeft(), func);
        postOrderTraversal(node.getRight(), func);
        func.apply(node);

    }

}
