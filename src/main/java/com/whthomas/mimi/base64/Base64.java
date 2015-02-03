package com.whthomas.mimi.base64;
/**
 * Created by whthomas on 15-2-3.
 */
public class Base64 {
    public static String base64Table = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    public static String encodeWithASCII(String str){
        //切分字符串
        char arg[] = str.toCharArray();
        StringBuilder sumBin = new StringBuilder("");
        //标记空格数量
        int count = 0;
        for (char c : arg){
            //将AScii码转换为int值
            int i = c;
            //将十进制的数字转换成二进制
            String bin = Integer.toBinaryString(i);
            String temp = repeatAppend(bin,"0",8-bin.length(),0);

            //将每组数值都补齐成为8位
            sumBin.append(temp);
            //每24位数值加一个空格
            if((sumBin.length()-count)%24==0){
                sumBin.append(" ");
                count ++;
            }
        }
        //通过空格切分字符串
        String args[] = sumBin.toString().split(" ");
        StringBuilder result = new StringBuilder();
        for (String bingo : args){
            String temp[] = divideGroup24To4_6(bingo);
            for (int i = 0; i < temp.length; i++) {
                int base = Integer.valueOf(temp[i], 2);
                result.append(base64Table.charAt(base));
            }
        }
        int temp = 4 - result.toString().length()%4;
        if(temp != 0 && temp != 4){
            for (int i=0;i<temp;i++){
                result.append("=");
            }
        }
        return result.toString();
    }
    public static String[] divideGroup24To4_6(String arg){
        int num = arg.length() / 6;
        if (arg.length()%6 != 0){
            num++;
        }
        String str[] = new String[num];
        for ( int i = 0; i < num; i++ ) {
            int start= 6*i;
            if (i==num-1){
                str[i] = arg.substring(start);
                str[i] = repeatAppend(str[i],"0",6-str[i].length(),1);
            }else {
                str[i] = "00" + arg.substring(start,start+6);
            }
        }
        return str;
    }
    //type 0 表示将str加在srcStr前面，1表示将str加在srcStr后面
    public static String repeatAppend(String srcStr,String str,int loop,int type){
        StringBuilder temp ;
        if (type==0){
            temp = new StringBuilder();
        }else{
            temp = new StringBuilder(srcStr);
        }
        for (int i=0;i<loop;i++){
            temp.append(str);
        }
        if (type==0){temp.append(srcStr);}
        return temp.toString();
    }
    public static void main(String args[]){
        String temp = encodeWithASCII("duzuzhou");
        System.out.println(temp);
    }
}