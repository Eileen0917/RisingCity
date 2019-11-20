public class RedBlackNode {
    private Building building;
    private int key;
    private RedBlackTree.Color color;
    private RedBlackNode parent;
    private RedBlackNode leftChild;
    private RedBlackNode rightChild;

    public RedBlackNode(Building b, RedBlackTree.Color color) {
        this.building = b;
        this.key = b.getBuildingNum();
        this.color = color;
    }
}