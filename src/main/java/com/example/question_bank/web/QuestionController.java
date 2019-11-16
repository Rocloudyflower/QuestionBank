package com.example.question_bank.web;

import com.example.question_bank.pojo.Question;
import com.example.question_bank.service.PropertyValueService;
import com.example.question_bank.service.QuestionService;
import com.example.question_bank.util.ImageUtil;
import com.example.question_bank.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@RestController
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    PropertyValueService propertyValueService;

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
        File apiFolder= new File(request.getServletContext().getRealPath("api/"));
        String apiPath = apiFolder.getAbsolutePath() + "\\baiduapi.py";
        System.out.println(apiPath);

        String[] arguments = new String[] {"python", apiPath , imagePath};

        String content = "";

        try {
            Process process = Runtime.getRuntime().exec(arguments);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
            String line = null;
            while ((line = in.readLine()) != null) {
                content += line + '\n';
                System.out.println(line);
            }
            in.close();
            //java代码中的process.waitFor()返回值为0表示我们调用python脚本成功，
            //返回值为1表示调用python脚本失败，这和我们通常意义上见到的0与1定义正好相反
            int re = process.waitFor();
            System.out.println(re);
            System.out.println(content);

            return questionService.search(content,0,20);



        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
