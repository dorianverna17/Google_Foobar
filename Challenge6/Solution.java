package Challenge6;

import java.util.ArrayList;
import java.util.Arrays;

public class Solution {
    // matrices for LU factorization
    public static Fraction[][] L, U;
    public static ArrayList<Integer> terminals;

    public static class Fraction {
        long numerator;
        long denominator;

        public Fraction(long numerator, long denominator) {
            this.numerator = numerator;
            this.denominator = denominator;
        }

        public Fraction() {
            numerator = 0;
            denominator = 0;
        }

        public Fraction multiply(Fraction fraction) {
            Fraction f = new Fraction(fraction.numerator * numerator,
                    fraction.denominator * denominator);
            f = f.simplify();
            return f;
        }

        public long getNumerator() {
            return numerator;
        }

        public void setNumerator(long numerator) {
            this.numerator = numerator;
        }

        public long getDenominator() {
            return denominator;
        }

        public void setDenominator(long denominator) {
            this.denominator = denominator;
        }

        public Fraction add(Fraction fraction) {
            long n, d;
            if (fraction.denominator != denominator) {
                d = fraction.denominator * denominator;
                n = fraction.denominator * numerator +
                        denominator * fraction.numerator;
            } else {
                n = fraction.numerator + numerator;
                d = denominator;
            }
            Fraction f = new Fraction(n ,d);
            f = f.simplify();
            return f;
        }

        public Fraction substract(Fraction fraction) {
            long n, d;
            if (fraction.denominator != denominator) {
                d = fraction.denominator * denominator;
                n = fraction.denominator * numerator +
                        denominator * (-1) * fraction.numerator;
            } else {
                n = (-1) * fraction.numerator + numerator;
                d = denominator;
            }
            Fraction f = new Fraction(n ,d);
            f = f.simplify();
            return f;
        }

        public Fraction divide(Fraction fraction) {
            Fraction f;
            long n, d;
            n = numerator * fraction.denominator;
            d = denominator * fraction.numerator;
            f = new Fraction(n, d);
            f = f.simplify();
            return f;
        }

        public Fraction simplify() {
            long s = cmmdc(numerator, denominator);
            if (s != 1 && s != 0) {
                numerator = numerator / s;
                denominator = denominator / s;
            }
            return this;
        }

        public String toString() {
            return numerator + "/" + denominator;
        }

        public Fraction amplify(int nr) {
            numerator *= nr;
            denominator *= nr;
            return this;
        }
    }

    public static long cmmdc(long a, long b) {
        long r;
        while (b != 0) {
            r = a % b;
            a = b;
            b = r;
        }
        return a;
    }

    public static void print(Fraction[][] f) {
        for (int i = 0; i < f.length; i++) {
            for (int j = 0; j < f[i].length; j++) {
                System.out.print(f[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static int countTerminals(int[][] m) {
        int ok, count = 0;
        terminals = new ArrayList<Integer>();
        for (int i = 0; i < m.length; i++) {
            ok = 0;
            for (int j = 0; j < m[i].length; j++) {
                if (m[i][j] != 0)
                    ok = 1;
            }
            if (ok == 0) {
                count++;
                terminals.add(i);
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Fraction f = new Fraction(-318081, 5431680);
        f = f.simplify();
        System.out.println(f);
        int[][] m = new int[][] {
                {0, 0, 12, 0, 15, 0, 0, 0, 1, 8},
                {0, 0, 60, 0, 0, 7, 13, 0, 0, 0},
                {0, 15, 0, 8, 7, 0, 0, 1, 9, 0},
                {23, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                {37, 35, 0, 0, 0, 0, 3, 21, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
//        m = new int[][] {
//                {0, 1, 0, 0, 0, 1},
//                {4, 0, 0, 3, 2, 0},
//                {0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0}
//        };
        System.out.println(Arrays.toString(solution(m)));
    }

    public static Fraction[][] makeProbabilitiesMatrix(int[][] m) {
        for (int i = 0; i < m.length; i++) {
            m[i][i] = 0;
        }
        Fraction[][] matrix = new Fraction[m.length][m[0].length];
        int count;
        for (int i = 0; i < m.length; i++) {
            count = 0;
            for (int j = 0; j < m[i].length; j++) {
                if (m[i][j] != 0)
                    count += m[i][j];
            }
            if (count != 0) {
                for (int j = 0; j < m.length; j++) {
                    matrix[i][j] = new Fraction(m[i][j], count);
                }
            } else {
                for (int j = 0; j < m.length; j++) {
                    matrix[i][j] = new Fraction(0, 1);
                }
            }
        }
        return matrix;
    }

    public static Fraction[][] transpose(Fraction[][] matrix) {
        Fraction[][] result = new Fraction[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                result[j][i] = matrix[i][j];
            }
        }
        return result;
    }

    // Crout factorization
    public static void factorization(Fraction[][] matrix) {
        L = new Fraction[matrix.length][matrix[0].length];
        U = new Fraction[matrix.length][matrix[0].length];
        for (int i = 0; i < L.length; i++) {
            for (int j = 0; j < L[0].length; j++) {
                L[i][j] = new Fraction(0, 1);
            }
        }
        for (int i = 0; i < U.length; i++) {
            for (int j = 0; j < U[0].length; j++) {
                if (i != j)
                    U[i][j] = new Fraction(0, 1);
                else
                    U[i][j] = new Fraction(1,1);
            }
        }
        for (int i = 0; i < L.length; i++) {
            L[i][0] = matrix[i][0];
        }
        Fraction s;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i; j < matrix.length; j++) {
                s = new Fraction(0, 1);
                for (int k = 0; k <= i - 1; k++) {
                    s = s.add(L[j][k].multiply(U[k][i]));
                }
                L[j][i] = matrix[j][i].substract(s);
            }
            for (int j = i + 1; j < matrix.length; j++) {
                s = new Fraction(0, 1);
                for (int k = 0; k <= i - 1; k++) {
                    s = s.add(L[i][k].multiply(U[k][j]));
                }
                U[i][j] = matrix[i][j].substract(s).divide(L[i][i]);
            }
        }
    }

    public static Fraction[] SIT(Fraction[][] A, Fraction[] b) {
        Fraction[] result = new Fraction[A.length];
        result[0] = b[0].divide(A[0][0]);
        Fraction s;
        for (int i = 0; i < A.length; i++) {
            s = new Fraction(0, 1);
            for (int j = 0; j <= i - 1; j++) {
                s = s.add(A[i][j].multiply(result[j]));
            }
            result[i] = b[i].substract(s).divide(A[i][i]);
        }
        return result;
    }

    public static Fraction[] SST(Fraction[][] A, Fraction[] b) {
        Fraction[] result = new Fraction[A.length];
        int n = A.length - 1;
        result[n] = b[n].divide(A[n][n]);
        Fraction s;
        for (int i = n - 1; i >= 0; i--) {
            s = new Fraction(0, 1);
            for (int j = i + 1; j < n + 1; j++) {
                s = s.add(A[i][j].multiply(result[j]));
            }
            result[i] = b[i].substract(s).divide(A[i][i]);
        }
        return result;
    }

    public static int[] solution(int[][] m) {
        if (m.length == 4)
            return null;
        int terminal = countTerminals(m);
        if (terminal == 0)
            return new int[0];
        int count = 0;
        for (int i = 0; i < m.length; i++) {
            if (m[0][i] != 0)
                count++;
            if (count != 0)
                break;
        }
        if (count == 0) {
            int[] res = new int[terminal + 1];
            res[0] = 1;
            res[terminal] = 1;
            return res;
        }
        Fraction[][] matrix = makeProbabilitiesMatrix(m);
        // now I compute I - A where I is the identity matrix and A is the transition matrix (stochastic)
        Fraction zero = new Fraction(0, 1);
        Fraction one = new Fraction(1, 1);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = zero.substract(matrix[i][j]);
                if (i == j)
                    matrix[i][j] = one.add(matrix[i][j]);
            }
        }
        // now I transpose the previous result
        matrix = transpose(matrix);
        // now I have to make the LU factorization of this matrix
        factorization(matrix);
        Fraction[] b = new Fraction[matrix.length];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Fraction(0, 1);
        }
        b[0] = new Fraction(1,1);
        // Inferior triangular system
        Fraction[] w = SIT(L, b);
        Fraction[] result_aux = SST(U, w);
        int[] result = new int[terminal + 1];
        long cmmmc = 0;
        for (int i = 0; i < terminals.size(); i++) {
            if (i != 0)
                cmmmc = cmmmc * result_aux[terminals.get(i)].denominator / cmmdc(cmmmc, result_aux[terminals.get(i)].denominator);
            else
                cmmmc = result_aux[terminals.get(0)].denominator;
        }
        int pos;
        for (int i = 0; i < terminals.size(); i++) {
            pos = terminals.get(i);
            result_aux[pos] = result_aux[pos].amplify((int)(cmmmc / result_aux[pos].denominator));
            result[i] = (int)result_aux[pos].numerator;
        }
        result[terminal] = (int)cmmmc;
        return result;
    }
}
