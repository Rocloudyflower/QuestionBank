package com.example.question_bank.util;

import org.springframework.util.ResourceUtils;

import javax.xml.ws.handler.Handler;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Jieba {

    public static HashSet<String> set = new HashSet<>();

    static {
        try {
            set = getWords();
        }catch (IOException ignored){

        }
    }

    //    获得自定义分词
    private static HashSet<String> getWords() throws IOException{

        InputStream stream = Jieba.class
                .getClassLoader()
                .getResourceAsStream("static/dicts/hotwords.txt");
        BufferedReader bf = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));

        StringBuilder buffer = new StringBuilder();

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
