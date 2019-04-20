package com.qexz.dao;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/4/9          FXY        Created
 **********************************************
 */

import com.qexz.model.Complaint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface ComplaintMapper {

    int insertComplaint(@Param("complaint") Complaint complaint);

    Complaint queryWhichComplaintByUserId(@Param("which") int which, @Param("whichId") int whichId, @Param("userId") int userId);

    List<Complaint> queryComplaintByWhich(@Param("which") int which);

    int getCountByWhich(@Param("which") int which);

    int deleteComplaint(@Param("which") int which, @Param("whichId") int whichId, @Param("useId") int useId);

    int updateComplaint(@Param("which") int which, @Param("whichId") int whichId, @Param("userId") int userId, @Param("state") int state);

}
