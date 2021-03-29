package Challenge4;

import java.math.BigInteger;
import java.util.ArrayList;

public class Solution {
//    static int moves = Integer.MAX_VALUE;
//    static ArrayList<BigInteger> list = new ArrayList<BigInteger>() {
//        @Override
//        public boolean contains(Object o) {
//            if (o instanceof BigInteger) {
//                BigInteger b = (BigInteger) o;
//                for (BigInteger bigInteger : this) {
//                    if (bigInteger.compareTo(new BigInteger(b.toString())) == 0)
//                        return true;
//                }
//            }
//            return false;
//        }
//
//        @Override
//        public boolean remove(Object o) {
//            if (this.contains(o)) {
//                for (int i = 0; i < this.size(); i++) {
//                    if (this.get(i).compareTo(new BigInteger(((BigInteger) o).toString())) == 0) {
//                        this.remove(i);
//                        return true;
//                    }
//                }
//            }
//            return false;
//        }
//    };
//
    public static boolean checkParity(BigInteger number) {
        return number.remainder(new BigInteger("2")).compareTo(new BigInteger("0")) == 0;
    }
//
//    public static int divideNumber(BigInteger number, int moves_aux) {
////        System.out.println(number.toString());
//        if (number.compareTo(new BigInteger("0")) < 0)
//            return Integer.MAX_VALUE;
//        if (moves_aux > moves)
//            return Integer.MAX_VALUE;
//        number = number.divide(new BigInteger("2"));
//        if (number.compareTo(new BigInteger("1")) == 0)
//            return moves_aux;
//        else
//            list.add(number);
//        int moves_aux2 = Integer.MAX_VALUE;
//        if (checkParity(number))
//            moves_aux2 = divideNumber(number, moves_aux + 1);
//        if (moves_aux2 < moves)
//            moves = moves_aux2;
//        int mov = addSubNumber(number, moves_aux + 1);
//        if (mov < moves)
//            moves = mov;
//        for (BigInteger i = new BigInteger("1"); i.compareTo(number) < 0; i = i.add(new BigInteger("1")))
//            list.remove(i);
//        return moves;
//    }
//
//    public static int addSubNumber(BigInteger number, int moves_aux) {
//        BigInteger added, substracted, one = new BigInteger("1");
////        System.out.println(number.toString());
//        int min1, min2;
//        if (moves_aux > moves)
//            return Integer.MAX_VALUE;
//        list.add(number);
//        added = number.add(one);
//        substracted = number.subtract(one);
//        min1 = divideNumber(added, moves_aux + 1);
//        if (min1 < moves)
//            moves = min1;
//        min2 = divideNumber(substracted, moves_aux + 1);
//        if (min2 < moves)
//            moves = min2;
//        for (BigInteger i = new BigInteger("1"); i.compareTo(number) < 0; i = i.add(new BigInteger("1")))
//            list.remove(i);
//        return moves;
//    }
//
//    public static int solution(String x) {
//        if (x.equals("1"))
//            return 0;
//        BigInteger number = new BigInteger(x);
//        if (checkParity(number)) {
//            list.add(number);
//            moves = divideNumber(number, 1);
//        } else
//            moves = addSubNumber(number, 1);
//        return moves;
//    }

    public static int solution(String x) {
        int moves = 0;
        if (x.equals("1"))
            return 0;
        BigInteger number = new BigInteger(x);
        BigInteger one = new BigInteger("1");
        while (number.compareTo(one) != 0) {
            if (!number.testBit(0)) {
                number = number.divide(new BigInteger("2"));
            } else if ((number.testBit(0) && !number.testBit(1)) || (
                    number.compareTo(new BigInteger("3"))) == 0) {
                number = number.subtract(one);
            } else {
                number = number.add(one);
            }
            moves++;
        }
        return moves;
    }

    public static void main(String[] args) {
        System.out.println(solution("9932023023020302"));
        for (Integer i = 1; i < 100; i++) {
            System.out.println(i + " " + solution(i.toString()));
        }
    }
}
