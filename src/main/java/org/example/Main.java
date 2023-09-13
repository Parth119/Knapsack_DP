package org.example;

import org.example.zero_one.ZeroOneKnapsack;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc  = new Scanner(System.in);
        System.out.print("Enter total number of weights :");
        Integer weights = sc.nextInt();
        System.out.print("Enter Maximum capacity :");
        Integer capacity = sc.nextInt();
        ZeroOneKnapsack zeroOneKnapsack = new ZeroOneKnapsack(weights,capacity);
        zeroOneKnapsack.run();
    }
}