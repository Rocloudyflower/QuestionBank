package com.example.question_bank.web;

import com.baidu.aip.ocr.AipOcr;
import com.example.question_bank.pojo.Question;
import com.example.question_bank.pojo.SearchRecord;
import com.example.question_bank.pojo.User;
import com.example.question_bank.service.HotWordService;
import com.example.question_bank.service.PropertyValueService;
import com.example.question_bank.service.QuestionService;
import com.example.question_bank.service.SearchRecordService;
import com.example.question_bank.util.ImageUtil;
import com.example.question_bank.util.Page4Navigator;
import com.example.question_bank.util.Result;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    SearchRecordService searchRecordService;
    @Autowired
    HotWordService hotWordService;

    //设置APPID/AK/SK
    private static final String APP_ID = "17712908";
    private static final String API_KEY = "3TvcTfSPey2VkwaGt3bOeW72";
    private static final String SECRET_KEY = "TjhPUD6ffZK5PIGxTZZVHuFjBVbeHtqo";

    @PostMapping("/questions")
    public Object add(@RequestBody Question bean) throws Exception {
        questionService.add(bean);
        return bean;
    }

    @Transactional
    @DeleteMapping("/questions/{id}")
    public String delete(@PathVariable("id") int id)  throws Exception {
        Question question = questionService.get(id);
        propertyValueService.delete(question);
        questionService.delete(id);
        return null;
    }

    @PutMapping("/questions")
    public Object update(@RequestBody Question bean) throws Exception {
        questionService.update(bean);
        return bean;
    }

    @GetMapping("/units/{uid}/questions")
    public Page4Navigator<Question> list(@PathVariable("uid") int uid, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "10") int size) throws Exception {
        start = start < 0 ? 0 : start;
        Page4Navigator<Question> page = questionService.list(uid, start, size,10 );

        return page;
    }

    @GetMapping("/questions/{qid}")
    public Question get(@PathVariable("qid") int qid){
        return questionService.get(qid);
    }

    @GetMapping("excercises/{uid}/questionsinfo")
    public List<Question> get4exercise(@PathVariable(name = "uid") int uid) throws Exception{
        return questionService.listexcersice(uid);
    }

//    搜索量 +1  && 加入到用户足迹
    @PostMapping("questions/searched/{qid}")
    public Object updateSearched(@PathVariable(name = "qid") int qid, HttpSession session) throws Exception{
        if(questionService.get(qid) != null){

            Question question = questionService.get(qid);
            int searched = question.getSearched() + 1;
            question.setSearched(searched);
            questionService.update(question);

            User user = (User) session.getAttribute("user");
            if (user != null){

                SearchRecord searchRecord = new SearchRecord();
                searchRecord.setUser(user);
                searchRecord.setQuestion(question);

                //获取系统时间
                Date dt = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentTime = sdf.format(dt);
                searchRecord.setTime(currentTime);
//                searchRecord.setTime(dt);

                searchRecordService.add(searchRecord);
            }

            return Result.success();
        }
        else return Result.fail("该题目不存在");
    }

//    图片文字识别API + 找题
    @PostMapping("/search/picture")
    public Object searchByPic(MultipartFile image, HttpServletRequest request){
        String folder = "img/";
        File imageFolder= new File(request.getServletContext().getRealPath(folder));
        File file = new File(imageFolder,image.getName()+".jpg");

        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        try {
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String imagePath = file.getAbsolutePath();
        System.out.println(imagePath);

        // 初始化一个AipOcr
        AipOcr client;
        client = new AipOcr(APP_ID, API_KEY, SECRET_KEY );
        String word = "";
        // 调用接口
        String path = request.getServletContext().getRealPath("/img/image.jpg");
        JSONObject res = client.basicAccurateGeneral(path, new HashMap<String, String>());
        JSONArray result = res.getJSONArray("words_result");
        for(int i = 0 ; i < result.length() ; i++){
            JSONObject job = result.getJSONObject(i);
            word += job.get("words").toString();
            System.out.println(word);
            if ( i > 30 ) break;
        }

        word = word.replaceAll(" ", "");
        String regEx="[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？-]";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(word.toString());
        System.out.println(matcher.replaceAll("").trim());
        return matcher.replaceAll("").trim();

    }

//    用户评分
    @PostMapping("questions/evaluate/{qid}")
    public Object evaluate(@PathVariable(name = "qid") int qid, HttpServletRequest request) throws Exception {
        String score = request.getParameter("score");
        questionService.questionsave(qid,Double.parseDouble(score));

        String finalScore = String.valueOf(questionService.get(qid).getScore());
        return Result.success(finalScore);
    }

//    搜索扩展
    @GetMapping("questions/expand")
    public List<Question> expand(String keyString){
//        搜索扩展
        List<Question> questions = questionService.searchQuestionDetail(keyString);
        int num = Math.min(questions.size(), 10);
        List<Question> result = new ArrayList<>();
        for(int i = 0; i < num; i++){
            result.add(questions.get(i));
        }
        return result;
    }


}
