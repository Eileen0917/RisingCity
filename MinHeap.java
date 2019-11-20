public class MinHeap {
    private Building[] heapArray;
    private int lastIndex;
    private int maxSize;

    public MinHeap() {
        maxSize = 1;
        lastIndex = -1;
        heapArray = new Building[1];
    }

    public void insert(Building newBuilding) {
        if ((++lastIndex) == maxSize) {
            doubleHeapArray();
        }

        heapArray[lastIndex] = newBuilding;

        int currentIndex = lastIndex;
        while (heapArray[parent(currentIndex)].getExecutedTime() > heapArray[currentIndex].getExecutedTime()) {

            if (currentIndex == 0) {
                break;
            }

            swap(currentIndex, parent(currentIndex));
            currentIndex = (currentIndex - 1) / 2;
        }
    }

    public Building findConstructBuilding() {
        if (lastIndex != -1) {
            return heapArray[0];
        } else {
            return null;
        }
    }

    public void increaseKey(Building b, int t) {
        int currentIndex = 0;
        int foundIndex = -1;

        for (Building building : heapArray) {
            if (building.getBuildingNum() == b.getBuildingNum()) {
                foundIndex = currentIndex;
                break;
            }
            currentIndex++;
        }

        if (foundIndex == -1) {
            return;
        }
        heapArray[foundIndex].setExecutedTime(heapArray[foundIndex].getExecutedTime() + t);
        minHeapify(foundIndex);
    }

    public void remove(Building b) {
        System.out.println(b.getBuildingNum());
        int currentIndex = 0;
        int foundIndex = -1;

        for (Building building : heapArray) {
            if (building.getBuildingNum() == b.getBuildingNum()) {
                foundIndex = currentIndex;
                break;
            }
            currentIndex++;
        }

        heapArray[foundIndex] = heapArray[lastIndex];
        heapArray[lastIndex] = null;
        lastIndex -= 1;
        if (heapArray[foundIndex] != null) {
            minHeapify(foundIndex);
        }
    }

    private void doubleHeapArray() {
        Building[] arrayTemp = new Building[maxSize * 2];

        for (int i = 0; i < maxSize; i++) {
            arrayTemp[i] = heapArray[i];
        }

        heapArray = arrayTemp;
        maxSize *= 2;
    }

    private void swap(int a, int b) {
        Building temp;
        temp = heapArray[a];
        heapArray[a] = heapArray[b];
        heapArray[b] = temp;
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
        if (i >= (lastIndex / 2) && i <= lastIndex) {
            return true;
        }
        return false;
    }

    private void minHeapify(int i) {
        if (!isLeaf(i)) {
            if (heapArray[i].getExecutedTime() > heapArray[leftChild(i)].getExecutedTime()
                    || heapArray[i].getExecutedTime() > heapArray[rightChild(i)].getExecutedTime()) {
                if (heapArray[leftChild(i)].getExecutedTime() < heapArray[rightChild(i)].getExecutedTime()) {
                    swap(i, leftChild(i));
                    minHeapify(leftChild(i));
                } else {
                    swap(i, rightChild(i));
                    minHeapify(rightChild(i));
                }
            }
        }
    }

}
