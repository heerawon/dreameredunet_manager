package com.webstarter.manage.mapper.db1;

import com.webstarter.manage.model.BoardModel;

import java.util.List;

public interface BoardMapper {
    List<BoardModel> selectBoardList ();
    int insertBoard (BoardModel boardModel);
    int updateBoard (BoardModel boardModel);
    int deleteBoard (Integer boardID);
    BoardModel selectBoardDetail(Integer boardID);
}
