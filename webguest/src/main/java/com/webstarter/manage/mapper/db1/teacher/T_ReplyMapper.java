package com.webstarter.manage.mapper.db1.teacher;

import com.webstarter.manage.model.ReplyModel;

import java.util.HashMap;
import java.util.List;

public interface T_ReplyMapper {
    ReplyModel getLastReply (HashMap hashMap);
    int getCountReply(HashMap hashMap);
    List<ReplyModel> getReplyList(HashMap hashMap);
    int insertReply(ReplyModel replyModel);
    int updateReply(ReplyModel replyModel);
    int deleteReply(ReplyModel replyModel);
}
