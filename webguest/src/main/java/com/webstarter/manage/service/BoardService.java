package com.webstarter.manage.service;

import com.webstarter.manage.mapper.db1.BoardMapper;
import com.webstarter.manage.model.BoardModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BoardService {
    @Resource
    private BoardMapper boardMapper;

    public List<BoardModel> getBoardList(){
        return boardMapper.selectBoardList();
    }

    public BoardModel getBoardDetail(Integer boardID){
        BoardModel board = boardMapper.selectBoardDetail(boardID);
        return board;
    }

    public int insertBoard(BoardModel boardModel) throws Exception{
        try{
            this.boardMapper.insertBoard(boardModel);
        }catch (Exception e){
            throw new Exception("insertBoard Err");
        }

        return boardModel.getBoardId();
    }

    public void updateBoard(BoardModel boardModel)throws  Exception{
        try{
            boardMapper.updateBoard(boardModel);
        }catch (Exception e){
            throw new Exception("updateBoard Err");
        }
    }

    public void deleteBoard(Integer boardID){
        boardMapper.deleteBoard(boardID);
    }
}
