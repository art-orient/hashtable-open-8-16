package com.epam.rd.autocode.hashtableopen816;

public class TableOpen8to16 implements HashtableOpen8to16 {
    private int[] array = new int[8];
    private boolean[] isUse = new boolean[array.length];

    @Override
    public void insert(int key, Object value) {
//        if (key == 0) {
//            System.out.println("debug");
//        }
        if (hasNumber(key)) {
            return;
        }
        int index = Math.abs(key) % array.length;
        if (isUse[index] == true) {
            index = findIndex(index, isUse);
            if (index != -1) {
                array[index] = key;
                isUse[index] = true;
                return;
            } else {
                array = grow(array);
                index = Math.abs(key) % array.length;
            }
        }
        if (isUse[index] == true) {
            index = findIndex(index, isUse);
        }
        array[index] = key;
        isUse[index] = true;
    }

    private boolean hasNumber(int key) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == key) {
                if (key == 0) {
                    if (isUse[i] == true) {
                        return true;
                    } else {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private int findIndex(int index, boolean[] isUse) {
        for (int i = index; i < isUse.length; i++) {
            if (isUse[i] == false) {
                return i;
            }
        }
        for (int i = 0; i < index; i++) {
            if (isUse[i] == false) {
                return i;
            }
        }
        return -1;
    }

    private int[] grow(int[] arr) {
        if (arr.length > 15) {
            throw new IllegalStateException();
        }
        int[] newArray = new int[arr.length * 2];
        isUse = new boolean[isUse.length * 2];
        for (int i = 0; i < arr.length; i++) {
            int index = Math.abs(arr[i]) % newArray.length;
            if (isUse[index] == true) {
                index = findIndex(index, isUse);
            }
            newArray[index] = arr[i];
            isUse[index] = true;
        }
        return newArray;
    }

    @Override
    public Object search(int key) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == key) {
                return key;
            }
        }
        return null;
    }

    @Override
    public void remove(int key) {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == key) {
                array[i] = 0;
                isUse[i] = false;
            }
            if (isUse[i] == true) {
                count++;
            }
        }
        if (count <= array.length / 4 && array.length > 2) {
            array = decrease(array);
        }
    }

    private int[] decrease(int[] arr) {
        int[] newArray = new int[arr.length / 2];
        boolean[] newIsUse = new boolean[newArray.length];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0 && isUse[i] == false) {
                continue;
            }
            int index = Math.abs(arr[i]) % newArray.length;
            if (newIsUse[index] == true) {
                index = findIndex(index, newIsUse);
            }
            newArray[index] = arr[i];
            newIsUse[index] = true;
        }
        isUse = newIsUse;
        return newArray;
    }

    @Override
    public int size() {
        return array.length;
    }

    @Override
    public int[] keys() {
        return array;
    }
}
