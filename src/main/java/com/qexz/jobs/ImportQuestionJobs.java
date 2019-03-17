package com.qexz.jobs;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/3/17          FXY        Created
 **********************************************
 */

import com.qexz.model.Question;
import com.qexz.service.QuestionService;
import com.qexz.util.POIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class ImportQuestionJobs {

    @Autowired
    QuestionService questionService;


    public final static long ONE_MINUTE = 60 * 1000;

    private static ConcurrentLinkedQueue<File> questionQueue = new ConcurrentLinkedQueue<>();

    //重试次数，5次则，移除试题，打印日志
    private int num = 0;

    //调度任务只能是public修饰吗？
    @Scheduled(fixedDelay = ONE_MINUTE)
    public void fixedDelayJob() {

        num = 0;
        while (!questionQueue.isEmpty()) {
            File file = null;
            List<Question> questions = null;
            try {
                num = 0;//次数重置为0
                file = removeQuestionJob();
                questions = convertToQuestion(file);
                questionService.addQuestions(questions);
            } catch (Exception e) {
                num++;
                while (num > 0 && num < 5) {
                    try {
                        System.out.println("导入试题出错，原因：" + e.toString());
                        questionService.addQuestions(questions);
                        num = 0;
                    } catch (Exception e1) {
                        num++;
                        if (num > 5) {
                            System.out.println("以导入5次，均失败，文件： " + file.getName());
                        }
                    }

                }
            }
        }
    }


    public static void addQuestionJob(File file) {
        questionQueue.add(file);
    }

    private File removeQuestionJob() {
        return questionQueue.poll();
    }

    private List<Question> convertToQuestion(File file) {
        List<Question> questions = new ArrayList<>();
        try {
            List<String[]> strings = POIUtil.readExcel(file);
            for (String[] s : strings) {
                Question question = new Question.Builder()
                        .setTitle(s[0]).setContent(s[1]).
                                setQuestionType(Integer.parseInt(s[2])).setOptionA(s[3]).
                                setOptionB(s[4]).setOptionC(s[5]).
                                setOptionD(s[6]).setAnswer(s[7]).
                                setParse(s[8]).setSubjectId(Integer.parseInt(s[9])).
                                setContestId(Integer.parseInt(s[10])).
                                setScore(Integer.parseInt(s[11])).setDifficulty(Integer.parseInt(s[12])).
                                setState(Integer.parseInt(s[13])).build();
                questions.add(question);
            }
            return questions;
        } catch (IOException e) {
            System.out.println("定时导入试题出错，原因：" + e.toString());
        }
        return null;
    }

}
