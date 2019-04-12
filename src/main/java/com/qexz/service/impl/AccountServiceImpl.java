package com.qexz.service.impl;

import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qexz.common.QexzConst;
import com.qexz.dao.*;
import com.qexz.vo.ExamDetailVo;
import com.qexz.vo.AnswerVo;
import com.qexz.vo.ReasonVo;
import com.qexz.model.*;
import com.qexz.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private GradeMapper gradeMapper;

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    ContestContentMapper contestContentMapper;

    @Override
    public int addAccount(Account account) {
        account.setAvatarImgUrl(QexzConst.DEFAULT_AVATAR_IMG_URL);
        return accountMapper.insertAccount(account);
    }

    @Override
    public boolean updateAccount(Account account) {
        return accountMapper.updateAccountById(account) > 0;
    }

    @Override
    public boolean updateAvatarImgUrlById(String avatarImgUrl, int id) {
        return accountMapper.updateAvatarImgUrlById(avatarImgUrl, id) > 0;
    }

    @Override
    public Account getAccountByUsername(String username) {
        return accountMapper.getAccountByUsername(username);
    }

    @Override
    public List<Account> getAccountsByStudentIds(List<Integer> studentIds) {
        return accountMapper.getAccountsByIds(studentIds);
    }

    @Override
    public Map<String, Object> getAccounts(int pageNum, int pageSize) {
        Map<String, Object> data = new HashMap<>();
        int count = accountMapper.getCount();
        if (count == 0) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("accounts", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (pageNum > totalPageNum) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("accounts", new ArrayList<>());
            return data;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Account> accounts = accountMapper.getAccounts();
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("accounts", accounts);
        return data;
    }

    @Override
    public boolean deleteAccount(int id) {
        return accountMapper.deleteAccount(id) > 0;
    }

    @Override
    public boolean disabledAccount(int id) {
        return accountMapper.updateState(id, 1) > 0;
    }

    @Override
    public boolean abledAccount(int id) {
        return accountMapper.updateState(id, 0) > 0;
    }

    @Override
    public List<Account> getAccountsByIds(Set<Integer> ids) {
        return accountMapper.getAccountsByIdSets(ids);
    }

    @Override
    public Account getAccountById(int id) {
        return accountMapper.getAccountById(id);
    }

    @Override
    public List<ExamDetailVo> getContestDetailInfo(int contestId, int studentId) {
        List<ExamDetailVo> examDetailVos = new ArrayList<>();

        Gson gson = new Gson();
        Type type = new TypeToken<List<AnswerVo.AnswerContent>>() {
        }.getType();
        //得到某场考试的所有问题
        List<ContestContent> contestContents = contestContentMapper.getContentByContestId(contestId);
        ArrayList<Integer> questionIds = contestContents.stream().map(ContestContent::getQuestionId).collect(Collectors.toCollection(ArrayList::new));
        List<Question> questions = questionMapper.getQuestionByIds(questionIds);
        Answer answer = answerMapper.queryAnswer(contestId, studentId, 1);
        List<AnswerVo.AnswerContent> answerContents = gson.fromJson(answer.getAnswerJson(), type);
        Map<Integer, String> collect = answerContents.stream().collect(Collectors.toMap(AnswerVo.AnswerContent::getQuestionId, AnswerVo.AnswerContent::getAnswer));
        Grade grade = gradeMapper.getGradeByStudentIdAndContestId(contestId, studentId);

        List<ReasonVo> list = gson.fromJson(grade.getManulReason(), new TypeToken<List<ReasonVo>>(){}.getType());
        Map<Integer, String> reason = list.stream().collect(Collectors.toMap(ReasonVo::getQuestionId, ReasonVo::getManulReason));
        List<ContestContent> contestContests = contestContentMapper.getContentByContestId(contestId);
        Map<Integer, Integer> scores = contestContests.stream().collect(Collectors.toMap(ContestContent::getQuestionId, ContestContent::getScore));
        for (Question question : questions) {
            ExamDetailVo detailDto = new ExamDetailVo();
            detailDto.setQuestionAnswer(question.getAnswer());
            detailDto.setQuestionContent(question.getContent());
            detailDto.setQuestionParse(question.getParse());
            detailDto.setQuestionType(question.getQuestionType());
            detailDto.setAnswer(collect.get(question.getId()));
            if (question.getQuestionType() == 2) {
                detailDto.setMark(reason.get(question.getId()));
            }
            if (question.getQuestionType() == 0) {
                detailDto.setOptionA(question.getOptionA());
                detailDto.setOptionB(question.getOptionB());
                detailDto.setOptionC(question.getOptionC());
                detailDto.setOptionD(question.getOptionD());
            }
            detailDto.setQuestionScore(scores.get(question.getId()));
            examDetailVos.add(detailDto);
        }
        return examDetailVos;
    }
}
