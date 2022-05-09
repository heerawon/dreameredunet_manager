package com.webstarter.manage.configure;

import com.webstarter.manage.model.PaymentOrderModel;
import com.webstarter.manage.model.StudentModel;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Log4j2
@Component("orderListXls")
public class OrderListXlsView extends AbstractXlsView {
    @Override protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
                                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "attachment; filename=\"dreamer_order_list.xls\"");
        CellStyle numberCellStyle = workbook.createCellStyle();
        DataFormat numberDataFormat = workbook.createDataFormat();
        numberCellStyle.setDataFormat(numberDataFormat.getFormat("#,##0"));
        Sheet sheet = workbook.createSheet("student_sheet");

        String[] firstRow = new String[]{"주문일시","주문번호","배송상태","주문상품","결제","주문액","결제상태","주문자명","계정","배송정보"};

        Row row = null;
        Cell cell = null;
        int rowNum = 0;

        // Header
        row = sheet.createRow(rowNum++);
        for(int i =0; i<firstRow.length; i++){
            cell = row.createCell(i);
            cell.setCellValue(firstRow[i]);
        }


        List<PaymentOrderModel> reqModel = (List<PaymentOrderModel>) model.get("resList");

        // Body
        for (int i=0; i<reqModel.size(); i++) {
            log.info("-----------------------------------------------------------");
            row = sheet.createRow(rowNum++);
            for( int j=0; j<firstRow.length; j++){

                cell = row.createCell(j);cell.setCellValue(reqModel.get(i).getRegDt()); j++;
                cell = row.createCell(j);cell.setCellValue(reqModel.get(i).getMerchantUid()); j++;
                if("1".equals(reqModel.get(i).getIsShipping())){
                    cell = row.createCell(j);cell.setCellValue("배송있음"); j++;
                }else if("0".equals(reqModel.get(i).getIsShipping())){
                    cell = row.createCell(j);cell.setCellValue("배송없음"); j++;
                }else{
                    cell = row.createCell(j);cell.setCellValue("미처리"); j++;
                }
                if(reqModel.get(i).getSubscribeName() != null){
                    cell = row.createCell(j);cell.setCellValue(reqModel.get(i).getSubscribeName()+" "+reqModel.get(i).getSubscribeTerm()+"개월"); j++;
                }else{
                    cell = row.createCell(j);cell.setCellValue(reqModel.get(i).getItemsName()); j++;
                }
                cell = row.createCell(j);cell.setCellValue("카드"); j++;
                cell = row.createCell(j);cell.setCellValue(reqModel.get(i).getTotalPrice()); j++;
                switch(reqModel.get(i).getOrderStatus()){
                    case "paid":
                        cell = row.createCell(j);cell.setCellValue("결제완료"); j++;
                        break;
                    case "failed":
                        cell = row.createCell(j);cell.setCellValue("결제실패"); j++;
                        break;
                    case "ready":
                        cell = row.createCell(j);cell.setCellValue("가상계좌 발급"); j++;
                        break;
                }
                cell = row.createCell(j);cell.setCellValue(reqModel.get(i).getOrderName()); j++;
                cell = row.createCell(j);cell.setCellValue("["+reqModel.get(i).getUserSnsType()+"]"+reqModel.get(i).getEmail()); j++;
                cell = row.createCell(j);cell.setCellValue(reqModel.get(i).getOrderMemo()); j++;

            }
        }

    }
}
