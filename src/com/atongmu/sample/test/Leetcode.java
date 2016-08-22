package com.atongmu.sample.test;

import java.util.Arrays;
import java.util.Random;

/**
 * [Description]
 * date: 2016/8/11
 *
 * @author maofagui
 * @version 1.0
 */
public class Leetcode {
    public static void main(String[] args) {
        Leetcode pro = new Leetcode();
        //System.out.println(pro.isPowerOfFour(0));
        //System.out.println(pro.canConstruct("aba", "baa"));
        //System.out.println(pro.reverseVowels("race car"));
        System.out.println(pro.guessNumber(2126753390));
    }

    private int rand = new Random().nextInt(Integer.MAX_VALUE);

    int guess(int num) {
        if (num > rand) return -1;
        else if (num < rand) return 1;
        return 0;

    }

    public int guessNumber(int n) {
        rand = 1702766719;//new Random().nextInt(n);
        System.out.println("rand:"+rand);

        int lower = 1;
        int upper = n;
        int middle= lower/2+upper/2;
        int res= guess(middle);
        while(0!=res){
            if(res==1){
                lower=middle;
                middle = lower/2+upper/2;
                if(lower==middle) middle++;
            }else{
                upper=middle;
                middle= lower/2+upper/2;
                if(upper==middle) middle--;
            }
            res = guess(middle);
        }
        return middle;
    }

    public boolean isPowerOfFour(int num) {
        while (num != 0 && (num & 1) == 0) {
            num = num >> 1;
            if ((num & 1) != 0) return false;
            num = num >> 1;
        }
        if (num == 1) return true;
        return false;
    }

    private boolean isVowel(char ch) {
        ch = Character.toLowerCase(ch);
        if ('a' == ch || 'e' == ch || 'i' == ch || 'o' == ch || 'u' == ch)
            return true;
        return false;
    }

    public String reverseVowels(String s) {
        if (null == s || s.length() < 2) return s;
        StringBuilder sb = new StringBuilder(s);
        int end = s.length() - 1;
        for (int i = 0; i < s.length(); i++) {
            if (isVowel(sb.charAt(i))) {
                while (end > i) {
                    if (isVowel(sb.charAt(end))) {
                        char tmp = sb.charAt(i);
                        sb.setCharAt(i, sb.charAt(end));
                        sb.setCharAt(end, tmp);
                        end--;
                        break;
                    }
                    end--;
                }
            }
        }
        return sb.toString();
    }


    public boolean canConstruct(String ransomNote, String magazine) {
        if (null == ransomNote || "".equals(ransomNote)) return true;
        if (null == magazine || ransomNote.length() > magazine.length()) return false;
        char[] arr1 = ransomNote.toCharArray();
        char[] arr2 = magazine.toCharArray();
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        int ptr0 = 0, ptr1 = 0;
        while (ptr0 < arr1.length && ptr1 < arr2.length) {
            if (arr1[ptr0] == arr2[ptr1]) {
                ptr0++;
                ptr1++;
            } else {
                ptr1++;
            }
        }
        if (ptr0 < arr1.length)
            return false;
        return true;
    }
}
