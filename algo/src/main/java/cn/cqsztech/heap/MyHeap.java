package cn.cqsztech.heap;

/**
 * ccmars
 * 2021/8/19
 **/
public class MyHeap {
   private int[] heaps ;
   private int   heapSize;
   private int   limit;

    public MyHeap(int limit) {
        heaps         = new int[limit];
        this.heapSize = 0;
        this.limit    = limit;
    }
    void push(int item){
        if(heapSize == limit){
            throw new RuntimeException(" heap is full");
        }
        heaps[heapSize] = item;
        heapInsert(heapSize++);
    }
    int  pop(){
        int item = heaps[0];
        swap(heaps,0,--heapSize);
        heapify(heaps,0,heapSize);
        return item;
    }
    void heapInsert(int index){
        while (heaps[(index-1)/2] < heaps[index]){
            swap(heaps,(index-1)/2,index);
            index =(index-1)/2;
        }
    }
    void heapify(int[] arr,int index,int heapSize){
        int left = index*2+1;
        while (left<heapSize){
            int largest = left+1<heapSize && arr[left+1]>arr[left]?left+1:left;
            largest     = arr[largest]>arr[index]?largest:index;
            if(index == largest){
                break;
            }
            swap(arr,index,largest);
            index = largest;
            left  = index*2+1;
        }
    }
    void swap(int[] arrs ,int x ,int y){
        int temp = arrs[x];
        arrs[x]  = arrs[y];
        arrs[y]  = temp;
    }
}
