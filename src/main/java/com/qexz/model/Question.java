package com.qexz.model;

import java.util.Date;

public class Question {

    private int id;
    private String title;
    private String content;
    private int questionType;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String answer;
    private String parse;
    private int subjectId;
    private Date createTime;
    private Date updateTime;

    private String subjectName;

    public Question() {
    }

    public Question(int id, String title, String content, int questionType, String optionA, String optionB, String optionC, String optionD, String answer, String parse, int subjectId, int contestId, int score, int difficulty, Date createTime, Date updateTime, int state, String subjectName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.questionType = questionType;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.answer = answer;
        this.parse = parse;
        this.subjectId = subjectId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.subjectName = subjectName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getParse() {
        return parse;
    }

    public void setParse(String parse) {
        this.parse = parse;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }


    public Question(Builder builder) {
        this.answer = builder.answer;
        this.content = builder.content;
        this.optionA = builder.optionA;
        this.optionB = builder.optionB;
        this.optionC = builder.optionC;
        this.optionD = builder.optionD;
        this.parse = builder.parse;
        this.questionType = builder.questionType;
        this.title = builder.title;
        this.subjectId = builder.subjectId;
    }


    public static class Builder {
        private String title;
        private String content;
        private int questionType;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private String answer;
        private String parse;
        private int subjectId;


        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }


        public Builder setContent(String content) {
            this.content = content;
            return this;
        }


        public Builder setQuestionType(int questionType) {
            this.questionType = questionType;
            return this;
        }


        public Builder setOptionA(String optionA) {
            this.optionA = optionA;
            return this;
        }


        public Builder setOptionB(String optionB) {
            this.optionB = optionB;
            return this;
        }


        public Builder setOptionC(String optionC) {
            this.optionC = optionC;
            return this;
        }


        public Builder setOptionD(String optionD) {
            this.optionD = optionD;
            return this;
        }


        public Builder setAnswer(String answer) {
            this.answer = answer;
            return this;
        }


        public Builder setParse(String parse) {
            this.parse = parse;
            return this;
        }


        public Builder setSubjectId(int subjectId) {
            this.subjectId = subjectId;
            return this;
        }

        public Question build() {
            return new Question(this);
        }
    }


}
