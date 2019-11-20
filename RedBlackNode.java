public class RedBlackNode {
    Building building;
    // int key;
    RedBlackTree.COLOR color;
    RedBlackNode parent;
    RedBlackNode leftChild;
    RedBlackNode rightChild;

    public RedBlackNode(Building b, RedBlackTree.COLOR color) {
        this.building = b;
        // this.key = b.getBuildingNum();
        this.color = color;
        this.parent = RedBlackTree.nil;
        this.leftChild = RedBlackTree.nil;
        this.rightChild = RedBlackTree.nil;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building b) {
        this.building = b;
        // this.key = b.getBuildingNum();
    }

    public RedBlackTree.COLOR getColor() {
        return color;
    }

    public void setColor(RedBlackTree.COLOR color) {
        this.color = color;
    }

    public RedBlackNode getParent() {
        return parent;
    }

    public void setParent(RedBlackNode parent) {
        this.parent = parent;
    }

    public RedBlackNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(RedBlackNode leftChild) {
        this.leftChild = leftChild;
    }

    public RedBlackNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(RedBlackNode rightChild) {
        this.rightChild = rightChild;
    }
}