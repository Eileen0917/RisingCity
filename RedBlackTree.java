public class RedBlackTree {

    public enum COLOR {
        RED, BLACK
    }

    RedBlackNode nil = new RedBlackNode(null, COLOR.BLACK);
    RedBlackNode root = nil;

    public void insert(Building b) {
        RedBlackNode node = new RedBlackNode(b, COLOR.RED);
        RedBlackNode temp = root;
        if (root == nil) {
            root = node;
            node.setColor(COLOR.BLACK);
            node.setParent(nil);
        } else {
            node.setColor(COLOR.RED);
            while (true) {
                if (node.getKey() < temp.getKey()) {
                    if (temp.getLeftChild() == nil) {
                        temp.setLeftChild(node);
                        node.setParent(temp);
                        break;
                    } else {
                        temp = temp.getLeftChild();
                    }
                } else if (node.getKey() >= temp.getKey()) {
                    if (temp.getRightChild() == nil) {
                        temp.setRightChild(node);
                        node.setParent(temp);
                        break;
                    } else {
                        temp = temp.getRightChild();
                    }
                }
            }
            fixTree(node);
        }
    }

    private void fixTree(RedBlackNode node) {
        while (node.getParent().getColor() == COLOR.RED) {
            RedBlackNode uncle = nil;
            if (node.getParent() == node.getParent().getParent().getLeftChild()) {
                uncle = node.getParent().getParent().getRightChild();

                if (uncle != nil && uncle.getColor() == COLOR.RED) {
                    node.getParent().setColor(COLOR.BLACK);
                    uncle.setColor(COLOR.BLACK);
                    node.getParent().getParent().setColor(COLOR.RED);
                    node = node.getParent().getParent();
                    continue;
                }
                if (node == node.getParent().getRightChild()) {
                    node = node.getParent();
                    rotateLeft(node);
                }
                node.getParent().setColor(COLOR.BLACK);
                node.getParent().getParent().setColor(COLOR.RED);

                rotateRight(node.getParent().getParent());
            } else {
                uncle = node.getParent().getParent().getLeftChild();
                if (uncle != nil && uncle.getColor() == COLOR.RED) {
                    node.getParent().setColor(COLOR.BLACK);
                    uncle.setColor(COLOR.BLACK);
                    node.getParent().getParent().setColor(COLOR.RED);
                    node = node.getParent().getParent();
                    continue;
                }
                if (node == node.getParent().getLeftChild()) {
                    node = node.getParent();
                    rotateRight(node);
                }
                node.getParent().setColor(COLOR.BLACK);
                node.getParent().getParent().setColor(COLOR.RED);

                rotateLeft(node.getParent().getParent());
            }
        }
        root.setColor(COLOR.BLACK);
    }
    // TODO: search with 1 param and 2 params
    // TODO: remove

    private void rotateLeft(RedBlackNode node) {
        if (node.getParent() != nil) {
            if (node == node.getParent().getLeftChild()) {
                node.getParent().setLeftChild(node.getRightChild());
            } else {
                node.getParent().setRightChild(node.getRightChild());
            }
            node.getRightChild().setParent(node.getParent());
            node.setParent(node.getRightChild());
            if (node.getRightChild().getLeftChild() != nil) {
                node.getRightChild().getLeftChild().setParent(node);
            }
            node.setRightChild(node.getRightChild().getLeftChild());
            node.getParent().setLeftChild(node);
        } else {// Need to rotate root
            RedBlackNode right = root.getRightChild();
            root.setRightChild(right.getLeftChild());
            right.getLeftChild().setParent(root);
            root.setParent(right);
            right.setLeftChild(root);
            right.setParent(nil);
            root = right;
        }
    }

    private void rotateRight(RedBlackNode node) {
        if (node.getParent() != nil) {
            if (node == node.getParent().getLeftChild()) {
                node.getParent().setLeftChild(node.getLeftChild());
            } else {
                node.getParent().setRightChild(node.getLeftChild());
            }

            node.getLeftChild().setParent(node.getParent());
            node.setParent(node.getLeftChild());
            if (node.getLeftChild().getRightChild() != nil) {
                node.getLeftChild().getRightChild().setParent(node);
            }
            node.setLeftChild(node.getLeftChild().getRightChild());
            node.getParent().setRightChild(node);
        } else {
            RedBlackNode left = root.getLeftChild();
            root.setLeftChild(root.getLeftChild().getRightChild());
            left.getRightChild().setParent(root);
            root.setParent(left);
            left.setRightChild(root);
            left.setParent(nil);
            root = left;
        }
    }
}