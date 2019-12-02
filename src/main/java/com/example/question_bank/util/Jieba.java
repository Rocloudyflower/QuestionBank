package com.example.question_bank.util;

import org.springframework.util.ResourceUtils;

import javax.xml.ws.handler.Handler;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Jieba {

    public static HashSet<String> set = new HashSet<>();

    static {
        try {
            set = getWords();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    获得自定义分词
    private static HashSet<String> getWords() throws IOException{
        StringBuilder buffer = new StringBuilder();
        BufferedReader bf = new BufferedReader(new FileReader(ResourceUtils.getFile("classpath:dicts/hotwords.txt")));

        String s = null;
        while((s = bf.readLine()) != null){
            //使用readLine方法，一次读一行
            buffer.append(s.trim());
        }

        String xml = buffer.toString().toLowerCase();
        System.out.println("xml+" + xml);
        bf.close();
        String [] str = xml.split("\\s");		//以空格分割
        return new HashSet<>(Arrays.asList(str));
    }

//    分词功能
    public static List<String> splitWords(String text) throws IOException{

        List<String> words = new ArrayList<>();

        int i = text.length();
        while(0 != text.length()){
            String a = text.substring(0, i);
            if(set.contains(a)){
                words.add(a);
                System.out.println(a);
                text = text.substring(i);
                i = text.length();
            }
            else{
                i--;
            }

            if(a.length() == 1){
                System.out.println(a);
                text = text.substring(1);
                i = text.length();
            }
        }
        return words;
    }
}
