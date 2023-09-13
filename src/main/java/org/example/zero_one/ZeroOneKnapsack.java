package org.example.zero_one;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ZeroOneKnapsack {
    Integer totalNumber;
    Integer maxCapacity;

    public ZeroOneKnapsack(Integer totalNumber, Integer maxCapacity) {
        this.totalNumber = totalNumber;
        this.maxCapacity = maxCapacity;
    }

    public void run() {
        if (totalNumber != null && maxCapacity != null) {
            Scanner sc = new Scanner(System.in);
            System.out.println("--------------- 0/1 KnapSack Problem ---------------");
            Integer[] profitArray = new Integer[totalNumber + 1];
            Integer[] weightArray = new Integer[totalNumber + 1];
            System.out.println("Please enter the value of weight and price ");
            for (int i = 0; i <= totalNumber; i++) {
                if (i == 0) {
                    weightArray[i] = 0;
                    profitArray[i] = 0;
                    continue;
                }
                System.out.print("weight [ " + (i) + " ]: ");
                weightArray[i] = sc.nextInt();
                while (maxCapacity < weightArray[i]) {
                    System.out.print("Weight must less than the max capacity [" + maxCapacity + "]\nweight [ " + (i + 1) + " ]: ");
                    weightArray[i] = sc.nextInt();
                }
                System.out.print("price [ " + (i + 1) + " ]: ");
                profitArray[i] = sc.nextInt();
                System.out.println();
            }
            Boolean swapped;
            for (int i = 0; i < totalNumber; i++) {
                swapped = Boolean.FALSE;
                for (int j = 0; j < totalNumber - i - 1; j++) {
                    if (weightArray[j] > weightArray[j + 1]) {
                        Integer tempWeight, tempPrice;

                        tempWeight = weightArray[j];
                        tempPrice = profitArray[j];
                        weightArray[j] = weightArray[j + 1];
                        profitArray[j] = profitArray[j + 1];
                        weightArray[j + 1] = tempWeight;
                        profitArray[j + 1] = tempPrice;

                        swapped = Boolean.TRUE;
                    }
                }
                if (swapped.equals(Boolean.FALSE)) {
                    break;
                }
            }
//            System.out.println("weight Array :" + Arrays.toString(weightArray));
//            System.out.println("price Array :" + Arrays.toString(profitArray));
            Integer[][] calculationArray = new Integer[totalNumber + 1][maxCapacity + 1];

//            Equation: V[i][w] = max{v[i-1,w],v[i-1,w-w[i]]+p[i]}
            System.out.println("Calculating values");

            for (int i = 0; i <= totalNumber; i++) {
                for (int j = 0; j <= maxCapacity; j++) {
                    if (i == 0 && j == 0) {
                        calculationArray[i][j] = 0;
                    } else if (i == 0 && j != 0) {
                        calculationArray[i][j] = 0;
                    } else if (i != 0 && j == 0) {
                        calculationArray[i][j] = 0;
                    } else {
                        //                       Equation: V[i][w] = max{v[i-1,w],v[i-1,w-w[i]]+p[i]}
                        Integer first = calculationArray[i - 1][j];
                        Integer second = (j - weightArray[i] < 0) ? 0 : ((calculationArray[i - 1][j - weightArray[i]]) + profitArray[i]);
                        calculationArray[i][j] = findMax(first, second);
                    }
                    System.out.print(calculationArray[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println("\nCalculating the considered weights :\n");
            System.out.println(getConsederedWeights(calculationArray, profitArray,weightArray).toString());

        } else {
            System.out.println("Init error assign max and total value");
        }
    }

    private HashMap<String,List<Integer>> getConsederedWeights(Integer[][] calculationArray, Integer[] profitArray, Integer[] weightArray) {
        HashMap<String,List<Integer>> result = new HashMap<>();
        List<Integer> desiredWeights = new ArrayList<>();
        List<Integer> desiredProfit = new ArrayList<>();
        Integer pointer = calculationArray[totalNumber][maxCapacity];

        Integer rowthLocation = totalNumber;
        while (pointer > 0) {
            rowthLocation = getRowLocation(rowthLocation,calculationArray, pointer);
            if (rowthLocation != null) {
                pointer -= profitArray[rowthLocation];
                desiredWeights.add(weightArray[rowthLocation]);
                desiredProfit.add(profitArray[rowthLocation]);
            } else {
                break;
            }
        }
        result.put("maximum weight :",desiredWeights);
        result.put("maximum profit :",desiredProfit);
        return result;
    }

    private Integer getRowLocation(Integer rowthLocation,Integer[][] calculationArray, Integer pointer) {
        Integer rowPosition = null;
        for (int i = rowthLocation; i >= 0; i--) {
            for (int j = maxCapacity; j >= 0; j--) {
                if (calculationArray[i][j].equals(pointer)) {
                    rowPosition = i;
                }
            }
        }
        return rowPosition;
    }

//    private Boolean doesRowContainsValue(Integer row, Integer[][] calculationArray, Integer pointer) {
//        for (int i = 0; i <= maxCapacity; i++) {
//            System.out.print(calculationArray[row][i] + " ");
//            if (calculationArray[row][i].equals(pointer)) {
//                return Boolean.TRUE;
//            }
//        }
//        return Boolean.FALSE;
//    }


    private Integer findMax(Integer number1, Integer number2) {
        if (number1 > number2) {
            return number1;
        } else if (number1 < number2) {
            return number2;
        } else {
            return number1;
        }
    }
}
