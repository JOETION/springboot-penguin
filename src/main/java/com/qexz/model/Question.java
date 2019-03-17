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
    private int contestId;
    private int score;
    private int difficulty;
    private Date createTime;
    private Date updateTime;
    private int state;

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
        this.contestId = contestId;
        this.score = score;
        this.difficulty = difficulty;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.state = state;
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

    public int getContestId() {
        return contestId;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", questionType=" + questionType +
                ", optionA='" + optionA + '\'' +
                ", optionB='" + optionB + '\'' +
                ", optionC='" + optionC + '\'' +
                ", optionD='" + optionD + '\'' +
                ", answer='" + answer + '\'' +
                ", parse='" + parse + '\'' +
                ", subjectId=" + subjectId +
                ", contestId=" + contestId +
                ", score=" + score +
                ", difficulty=" + difficulty +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", state=" + state +
                ", subjectName='" + subjectName + '\'' +
                '}';
    }

    public Question(Builder builder) {
        this.answer = builder.answer;
        this.content = builder.content;
        this.contestId = builder.contestId;
        this.difficulty = builder.difficulty;
        this.optionA = builder.optionA;
        this.optionB = builder.optionB;
        this.optionC = builder.optionC;
        this.optionD = builder.optionD;
        this.parse = builder.parse;
        this.questionType = builder.questionType;
        this.score = builder.score;
        this.title = builder.title;
        this.subjectId = builder.subjectId;
        this.state = builder.state;
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
        private int contestId;
        private int score;
        private int difficulty;
        private int state;


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


        public Builder setContestId(int contestId) {
            this.contestId = contestId;
            return this;
        }


        public Builder setScore(int score) {
            this.score = score;
            return this;
        }


        public Builder setDifficulty(int difficulty) {
            this.difficulty = difficulty;
            return this;
        }

        public Builder setState(int state) {
            this.state = state;
            return this;
        }

        public Question build() {
            return new Question(this);
        }
    }


}
