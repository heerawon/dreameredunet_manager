package com.webstarter.manage.controller;

import com.google.gson.JsonObject;
import com.webstarter.manage.configure.ConstData;
import com.webstarter.manage.model.BoardModel;
import com.webstarter.manage.service.BoardService;
import com.webstarter.manage.service.SmartTracerService;
import com.webstarter.manage.service.SuremBisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Log4j2
@Controller
@RequestMapping("board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;


    @GetMapping("list")
    public String getBoardList(Model model) {
        List<BoardModel> boardList = boardService.getBoardList();
        model.addAttribute("boardList", boardList);
        return "ex_function/board/pub_notice_list";
    }

    @GetMapping("register")
    public String getBoardRegister(Model model,
                                   @RequestParam(value = "id", defaultValue = "-1") int id,
                                   @RequestParam(value = "type", defaultValue = "") String type,
                                   BoardModel boardModel) {
        if ("edit".equals(type)) {
            boardModel = boardService.getBoardDetail(id);

            log.info("getBoardRegister :::::::" + boardModel.toString());
            model.addAttribute("edit", 1);
            model.addAttribute("boardModel", boardModel);
        } else {
            model.addAttribute("edit", 0);
            model.addAttribute("boardModel", new BoardModel());

        }
        return "ex_function/board/pub_notice_register";
    }

    @PostMapping("insertUpdate")
    public String insertBoard(BoardModel boardModel, @RequestParam(value = "edit", defaultValue = "") Integer edit) {
        log.info("board :::::" + boardModel);
        log.info("edit :::::" + edit);
        int boardId = boardModel.getBoardId();

        if (edit == 1) {
            try {
                boardService.updateBoard(boardModel);
            } catch (Exception e) {
                return "redirect:detail?id=" + boardId + "&code=insertBoard_001";
            }
        } else {
            try {
                boardId = boardService.insertBoard(boardModel);
            } catch (Exception e) {
                return "redirect:detail?id=" + boardId + "&code=insertBoard_002";
            }
        }
        return "redirect:detail?id=" + boardId;
    }

    @GetMapping("detail")
    public String boardDetail(@RequestParam(value = "id", defaultValue = "-1") Integer boardId,
                              @RequestParam(value = "code", required = false, defaultValue = "") String code,
                              Model model) {


        log.info("detail? :::::");
        log.info("boardID :::::" + boardId);
        BoardModel resBoard = boardService.getBoardDetail(boardId);
        System.out.println("resBoard :::::" + resBoard);

        model.addAttribute("resModel", resBoard);
        model.addAttribute("msg", ConstData.getCode(code));
        return "ex_function/board/pub_notice_detail";
    }

    @GetMapping("delete")
    public String deleteBoard(@RequestParam(value = "id", defaultValue = "-1") int boardId) {
        System.out.println("boardId ::::::" + boardId);
        boardService.deleteBoard(boardId);
        return "redirect:list";
    }

}
