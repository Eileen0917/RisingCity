public class MinHeap {
    private int heap[];
    private int size;
    private int maxsize;

    private static final int FRONT = 1;

    public MinHeap(int maxsize) {
        this.maxsize = maxsize;
        this.size = 0;
        heap = new int[this.maxsize + 1];
        heap[0] = Integer.MIN_VALUE;
    }

    private int parent(int i) {
        return i / 2;
    }

    private int leftChild(int i) {
        return (2 * i);
    }

    private int rightChild(int i) {
        return (2 * i) + 1;
    }

    private boolean isLeaf(int i) {
        if (i >= (size / 2) && i <= size) {
            return true;
        }
        return false;
    }

    private void swap(int a, int b) {
        int tmp;
        tmp = heap[a];
        heap[a] = heap[b];
        heap[b] = tmp;
    }

    public int remove() {
        int pop_item = heap[FRONT];
        heap[FRONT] = heap[size--];
        minHeapify(FRONT);
        return pop_item;
    }

    public void insert(int element) {
        heap[++size] = element;
        int current = size;

        while (heap[current] < heap[parent(current)]) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    private void minHeapify(int i) {
        if (!isLeaf(i)) {
            if (heap[i] > heap[leftChild(i)] || heap[i] > heap[rightChild(i)]) {
                if (heap[leftChild(i)] < heap[rightChild(i)]) {
                    swap(i, leftChild(i));
                    minHeapify(leftChild(i));
                } else {
                    swap(i, rightChild(i));
                    minHeapify(rightChild(i));
                }
            }
        }
    }

    public void MinHeapImplementation() {
        for (int i = (size / 2); i >= 1; i--) {
            minHeapify(i);
        }
    }

}