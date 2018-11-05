package com.interview.program2;

public class Seating {

    public String seatThem(int n){
        if( n%2 !=0 && n%3 != 0){
            throw new RuntimeException("Invalid input "+n);
        }

        int patternCountToRepeat =  (n/ (2*3));

        String firstRowPattern = "M P C";
        String secondRowPattern = "C M P";
        StringBuilder sb = new StringBuilder();

        for( int i=0;i<patternCountToRepeat;i++){
            sb.append(firstRowPattern).append(" ");
        }
        sb.append("\n");
        for( int i=0;i<patternCountToRepeat;i++){
            sb.append(secondRowPattern).append(" ");
        }
        return sb.toString();
    }

    public static void main(String[] args){
        String seating = new Seating().seatThem(24);
        System.out.println(seating);
  }
}
