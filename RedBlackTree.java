import java.util.ArrayList;

public class RedBlackTree {

    public enum COLOR {
        RED, BLACK
    }

    static RedBlackNode nil = new RedBlackNode(null, COLOR.BLACK);
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
                if (node.getBuilding().getBuildingNum() < temp.getBuilding().getBuildingNum()) {
                    if (temp.getLeftChild() == nil) {
                        temp.setLeftChild(node);
                        node.setParent(temp);
                        break;
                    } else {
                        temp = temp.getLeftChild();
                    }
                } else if (node.getBuilding().getBuildingNum() >= temp.getBuilding().getBuildingNum()) {
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

    public void remove(Building b) {
        RedBlackNode z = find(b.getBuildingNum());
        RedBlackNode x;
        RedBlackNode y = z;
        COLOR y_original_color = y.getColor();

        if (z.getLeftChild() == nil) {
            x = z.getRightChild();
            swap(z, z.getRightChild());
        } else if (z.getRightChild() == nil) {
            x = z.getLeftChild();
            swap(z, z.getLeftChild());
        } else {
            y = findMin(z.getRightChild());
            y_original_color = y.getColor();
            x = y.getRightChild();
            if (y.getParent() == z)
                x.setParent(y);
            else {
                swap(y, y.getRightChild());
                y.setRightChild(z.getRightChild());
                y.getRightChild().setParent(y);
            }
            swap(z, y);
            y.setLeftChild(z.getLeftChild());
            y.getLeftChild().setParent(y);
            y.setColor(z.getColor());
        }
        if (y_original_color == COLOR.BLACK)
            removeFixup(x);
    }

    public Building search(int bNum) {
        RedBlackNode node = find(bNum);
        return node.getBuilding();
    }

    public ArrayList<Building> searchInRange(ArrayList<Building> list, int bNum1, int bNum2) {
        searchInRange(list, root, bNum1, bNum2);
        return list;
    }

    private void searchInRange(ArrayList<Building> list, RedBlackNode node, int bNum1, int bNum2) {
        if (node.getBuilding().getBuildingNum() > bNum1 && node.getBuilding().getBuildingNum() < bNum2) {
            list.add(node.getBuilding());
            if (node.getLeftChild() != nil) {
                searchInRange(list, node.getLeftChild(), bNum1, bNum2);
            }
            if (node.getRightChild() != nil) {
                searchInRange(list, node.getRightChild(), bNum1, bNum2);
            }
        } else if (node.getBuilding().getBuildingNum() < bNum1) {
            list.add(node.getBuilding());
            if (node.getLeftChild() != nil) {
                searchInRange(list, node.getLeftChild(), bNum1, bNum2);
            }
        } else if (node.getBuilding().getBuildingNum() > bNum2) {
            list.add(node.getBuilding());
            if (node.getRightChild() != nil) {
                searchInRange(list, node.getRightChild(), bNum1, bNum2);
            }
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

    private void swap(RedBlackNode a, RedBlackNode b) {
        if (a.getParent() == nil) {
            root = b;
        } else if (a == a.getParent().getLeftChild()) {
            a.getParent().setLeftChild(b);
        } else
            a.getParent().setRightChild(b);
        b.setParent(a.getParent());
    }

    private void removeFixup(RedBlackNode x) {
        while (x != root && x.getColor() == COLOR.BLACK) {
            if (x == x.getParent().getLeftChild()) {
                RedBlackNode w = x.getParent().getRightChild();
                if (w.getColor() == COLOR.RED) {
                    w.setColor(COLOR.BLACK);
                    x.getParent().setColor(COLOR.RED);
                    rotateLeft(x.getParent());
                    w = x.getParent().getRightChild();
                }
                if (w.getLeftChild().getColor() == COLOR.BLACK && w.getRightChild().getColor() == COLOR.BLACK) {
                    w.setColor(COLOR.RED);
                    x = x.getParent();
                    continue;
                } else if (w.getRightChild().getColor() == COLOR.BLACK) {
                    w.getLeftChild().setColor(COLOR.BLACK);
                    w.setColor(COLOR.RED);
                    rotateRight(w);
                    w = x.getParent().getRightChild();
                }
                if (w.getRightChild().getColor() == COLOR.RED) {
                    w.setColor(x.getParent().getColor());
                    x.getParent().setColor(COLOR.BLACK);
                    w.getRightChild().setColor(COLOR.BLACK);
                    rotateLeft(x.getParent());
                    x = root;
                }
            } else {
                RedBlackNode w = x.getParent().getLeftChild();
                if (w.getColor() == COLOR.RED) {
                    w.setColor(COLOR.BLACK);
                    x.getParent().setColor(COLOR.RED);
                    rotateRight(x.getParent());
                    w = x.getParent().getLeftChild();
                }
                if (w.getRightChild().getColor() == COLOR.BLACK && w.getLeftChild().getColor() == COLOR.BLACK) {
                    w.setColor(COLOR.RED);
                    x = x.getParent();
                    continue;
                } else if (w.getLeftChild().getColor() == COLOR.BLACK) {
                    w.getRightChild().setColor(COLOR.BLACK);
                    w.setColor(COLOR.RED);
                    rotateLeft(w);
                    w = x.getParent().getLeftChild();
                }
                if (w.getLeftChild().getColor() == COLOR.RED) {
                    w.setColor(x.getParent().getColor());
                    x.getParent().setColor(COLOR.BLACK);
                    w.getLeftChild().setColor(COLOR.BLACK);
                    rotateRight(x.getParent());
                    x = root;
                }
            }
        }
        x.setColor(COLOR.BLACK);
    }

    private RedBlackNode findMin(RedBlackNode n) {
        while (n.getLeftChild() != nil) {
            n = n.getLeftChild();
        }
        return n;
    }

    private RedBlackNode find(int bNum) {
        if (root.getBuilding().getBuildingNum() < bNum) {
            return findNode(root.getRightChild(), bNum);
        } else if (root.getBuilding().getBuildingNum() > bNum) {
            return findNode(root.getLeftChild(), bNum);
        } else {
            return root;
        }
    }

    private RedBlackNode findNode(RedBlackNode node, int key) {
        if (node == nil) {
            return nil;
        }

        if (key == node.getBuilding().getBuildingNum()) {
            return node;
        }

        if (key > node.getBuilding().getBuildingNum()) {
            return findNode(node.getRightChild(), key);
        } else {
            return findNode(node.getLeftChild(), key);
        }
    }

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