import java.io.*;
import java.text.DecimalFormat;
import java.util.Scanner;


import java.util.ArrayList;
import java.util.Comparator;

class HeapTest{
    public static void main(String[] args) {
        final int NUM_MUTUAL_FUNDS = 7;

		ArrayList<Order> orders = new ArrayList<>();

        // Same-Day Shipping - 1
    // Express One-Day Shipping - 2
    // Two-Day Shipping - 3
    // Standard Shipping - 4
    // No-Rush Shipping - 5
        String sameDay = "Same-Day Shipping"; // 5
        String express = "Express One-Day Shipping"; // 4
        String twoDay = "Two-Day Shipping"; // 3
        String standard = "Standard Shipping"; // 2
        String noRush = "No-Rush Shipping"; // 1

        orders.add(new Order(twoDay));
        orders.add(new Order(express));
        orders.add(new Order(standard));
        orders.add(new Order(noRush));
        orders.add(new Order(sameDay));
        orders.add(new Order(twoDay));
        orders.add(new Order(standard));
        orders.add(new Order(twoDay));
        orders.add(new Order(express));
        orders.add(new Order(twoDay));
        orders.add(new Order(noRush));
        orders.add(new Order(twoDay)); 
        orders.add(new Order(noRush));
        orders.add(new Order(sameDay));
        orders.add(new Order(express));

        

		Heap heap = new Heap<Order>(orders, new PriorityComparator()); 

     

        int size = heap.getHeapSize();
        System.out.println(size);
        for(int i = 0; i < size; i++) {
            System.out.println((i + 1) + ". " + heap.getMax());
            if(heap.getHeapSize() > 1) {
                heap.remove(1);
            }
            
            //System.out.println("size of heap" + heap.getHeapSize());
        }
        
        heap.insert(new Order(twoDay));
        heap.insert(new Order(express));
        heap.insert(new Order(standard));
        heap.insert(new Order(noRush));
        heap.insert(new Order(sameDay));
        heap.insert(new Order(twoDay));
        heap.insert(new Order(standard));
        heap.insert(new Order(twoDay));
        heap.insert(new Order(express));
        heap.insert(new Order(twoDay));
        heap.insert(new Order(noRush));
        heap.insert(new Order(twoDay)); 
        heap.insert(new Order(noRush));
        heap.insert(new Order(sameDay));
        heap.insert(new Order(express));

        

    }

   
}
