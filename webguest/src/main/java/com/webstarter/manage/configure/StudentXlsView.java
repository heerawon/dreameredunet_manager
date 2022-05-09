package com.webstarter.manage.configure;

import com.webstarter.manage.model.StudentModel;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("studentXls")
public class StudentXlsView extends AbstractXlsView {
    @Override protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
                                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "attachment; filename=\"dreamer_student.xls\"");
        CellStyle numberCellStyle = workbook.createCellStyle();
        DataFormat numberDataFormat = workbook.createDataFormat();
        numberCellStyle.setDataFormat(numberDataFormat.getFormat("#,##0"));
        Sheet sheet = workbook.createSheet("student_sheet");

        String[] firstRow = new String[]{"가입일시", "이름", "성별", "생년월일", "구독 횟수", "결제금액", "구독 만료", "수강중인 팀", "특별활동 팀"};

        Row row = null;
        Cell cell = null;
        int rowNum = 0;

        // Header
        row = sheet.createRow(rowNum++);
        for(int i =0; i<firstRow.length; i++){
            cell = row.createCell(i);
            cell.setCellValue(firstRow[i]);
        }

        List<StudentModel> reqModel = (List<StudentModel>) model.get("resList");

        // Body
        for (int i=0; i<reqModel.size(); i++) {
            row = sheet.createRow(rowNum++);
            for( int j=0; j<firstRow.length; j++){

                cell = row.createCell(j);cell.setCellValue(reqModel.get(i).getUserRegDt()); j++;
                cell = row.createCell(j);cell.setCellValue(reqModel.get(i).getUserName()); j++;
                cell = row.createCell(j);cell.setCellValue("F".equals(reqModel.get(i).getStudentGender())? "여성":"남성"); j++;
                cell = row.createCell(j);cell.setCellValue(reqModel.get(i).getStudentBirth()); j++;
                cell = row.createCell(j);cell.setCellValue(reqModel.get(i).getCntOrder()); j++;
                cell = row.createCell(j);cell.setCellValue(reqModel.get(i).getSumTotalPrice()); j++;
                cell = row.createCell(j);cell.setCellValue(reqModel.get(i).getCntExpiredDt()+"일 전"); j++;
                cell = row.createCell(j);cell.setCellValue(reqModel.get(i).getTeamName()); j++;
                cell = row.createCell(j);cell.setCellValue(reqModel.get(i).getTeamName2()); j++;

            }
        }



    }
}
