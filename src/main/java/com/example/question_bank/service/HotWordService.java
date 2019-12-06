package com.example.question_bank.service;

import com.example.question_bank.dao.HotWordDAO;
import com.example.question_bank.pojo.HotWord;
import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.nlp.tokenizers.ChineseWordTokenizer;
import com.kennycason.kumo.palette.LinearGradientColorPalette;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HotWordService {
    @Autowired
    HotWordDAO hotWordDAO;

    public List<HotWord> sortBySearchtimes(){
        return hotWordDAO.findAllByOrderBySearchtimesDesc();
    }

    public boolean exitByHotWord(String hotword){
        return hotWordDAO.existsById(hotword);
    }

    public void save(HotWord hotWord){
        hotWordDAO.save(hotWord);
    }

    public HotWord get(String hotWord){
        return hotWordDAO.getOne(hotWord);
    }

//    public void createWordCloud(){
//        List<HotWord> hotWordList = hotWordDAO.findAllByOrderBySearchtimesDesc();//按搜索次数大小排序返回结果
//        List<String> wordlist = new ArrayList<>();
//        int top = 60; //前60个热词
//        int i =0;
//        while (i < top){
//            HotWord t_hotword = hotWordList.get(i);
//            String t_word = t_hotword.getHotword();
//            int count = t_hotword.getSearchtimes();//热词搜索次数
//            //注：现在热词搜索次数都是0，所以测试的时候可以将下面的c < count改成c < (60 - i)之类的总之就是使c每次小于不同的数
//            for(int c = 0; c < count;c++){
//                wordlist.add(t_word);
//            }
//            i++;
//        }
//        FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
//        frequencyAnalyzer.setWordFrequenciesToReturn(600);
//        frequencyAnalyzer.setMinWordLength(2);
//        frequencyAnalyzer.setWordTokenizer(new ChineseWordTokenizer());
//
//        List<WordFrequency> wordFrequencyList = frequencyAnalyzer.load(wordlist);
//        Dimension dimension = new Dimension(600, 600);
//        WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
//        wordCloud.setPadding(2);
//        java.awt.Font font = new java.awt.Font("font.ttf",2,20);
//        wordCloud.setColorPalette(
//                new LinearGradientColorPalette(
//                        Color.GREEN,Color.RED,Color.BLUE,30,30));
//        wordCloud.setKumoFont(new KumoFont(font));
//        wordCloud.setBackgroundColor(new Color(255,255,255));
//        wordCloud.setBackground(new CircleBackground(255));
//        wordCloud.setFontScalar(new SqrtFontScalar(12,45));
//        wordCloud.build(wordFrequencyList);
//
////        String folder = "img/";
////        File imgFolder = new File(request.getServletContext().getRealPath(folder));
////        File file = new File(imgFolder,"wordcloud.png");
////        if(!file.getParentFile().exists())
////            file.getParentFile().mkdirs();
////        System.out.println(file.getPath());
//
////        wordCloud.writeToFile("D:\\QuestionBank\\src\\main\\webapp\\img\\wordcloud.png");
//
//        wordCloud.writeToFile("wordcloud.png");
//    }
}
