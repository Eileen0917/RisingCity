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
        // check if it is full.
        if ((++lastIndex) == maxSize) {
            doubleHeapArray();
        }

        // add it into heapArray
        heapArray[lastIndex] = newBuilding;

        // swap if it is smaller than it's parent.
        int currentIndex = lastIndex;
        while (heapArray[parent(currentIndex)].getExecutedTime() > heapArray[currentIndex].getExecutedTime()) {
            if (currentIndex == 0) {
                break;
            }
            swap(currentIndex, parent(currentIndex));
            currentIndex = parent(currentIndex);
        }
    }

    public Building findConstructBuilding() {
        // return the first one in heapArray.
        if (lastIndex != -1) {
            return heapArray[0];
        } else {
            return null;
        }
    }

    public void increaseKey(Building b, int t) {
        int currentIndex = 0;
        int foundIndex = -1;

        // find the index of b
        for (Building building : heapArray) {
            if (building.getBuildingNum() == b.getBuildingNum()) {
                foundIndex = currentIndex;
                break;
            }
            currentIndex++;
        }

        // b is not in heapArray
        if (foundIndex == -1) {
            return;
        }

        heapArray[foundIndex].setExecutedTime(heapArray[foundIndex].getExecutedTime() + t);

        // need to rebuild the heap after increase key.
        minHeapify(foundIndex);
    }

    public void remove(Building b) {
        int currentIndex = 0;
        int foundIndex = -1;

        // find the index of b
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

        // delete last one don't need to do minHeapify again.
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
        return (i - 1) / 2;
    }

    private int leftChild(int i) {
        return (2 * i) + 1;
    }

    private int rightChild(int i) {
        return (2 * i) + 2;
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
                } else if (heapArray[leftChild(i)].getExecutedTime() > heapArray[rightChild(i)].getExecutedTime()) {
                    swap(i, rightChild(i));
                    minHeapify(rightChild(i));
                } else {
                    if (heapArray[leftChild(i)].getBuildingNum() < heapArray[rightChild(i)].getBuildingNum()) {
                        swap(i, leftChild(i));
                        minHeapify(leftChild(i));
                    } else {
                        swap(i, rightChild(i));
                        minHeapify(rightChild(i));
                    }
                }
            } else if (heapArray[i].getExecutedTime() == heapArray[leftChild(i)].getExecutedTime()
                    && heapArray[i].getExecutedTime() == heapArray[rightChild(i)].getExecutedTime()) {
                // because getExecutedTime is same, find the min child and swap it to be parent
                if (heapArray[i].getBuildingNum() > heapArray[leftChild(i)].getBuildingNum()
                        || heapArray[i].getBuildingNum() > heapArray[rightChild(i)].getBuildingNum()) {
                    if (heapArray[leftChild(i)].getBuildingNum() < heapArray[rightChild(i)].getBuildingNum()) {
                        swap(i, leftChild(i));
                        minHeapify(leftChild(i));
                    } else {
                        swap(i, rightChild(i));
                        minHeapify(rightChild(i));
                    }
                }
            } else if (heapArray[i].getExecutedTime() == heapArray[leftChild(i)].getExecutedTime()) {
                if (heapArray[i].getBuildingNum() > heapArray[leftChild(i)].getBuildingNum()) {
                    swap(i, leftChild(i));
                    minHeapify(leftChild(i));
                }
            } else if (heapArray[i].getExecutedTime() == heapArray[rightChild(i)].getExecutedTime()) {
                if (heapArray[i].getBuildingNum() > heapArray[rightChild(i)].getBuildingNum()) {
                    swap(i, rightChild(i));
                    minHeapify(rightChild(i));
                }
            }
        } else if (lastIndex == 1 && i == 0) {
            // only two nodes in the heap
            if (heapArray[i].getExecutedTime() == heapArray[lastIndex].getExecutedTime()) {
                if (heapArray[lastIndex].getBuildingNum() < heapArray[i].getBuildingNum()) {
                    swap(i, lastIndex);
                }
            } else if (heapArray[lastIndex].getExecutedTime() < heapArray[i].getExecutedTime()) {
                swap(i, lastIndex);
            }
        }
    }
}
