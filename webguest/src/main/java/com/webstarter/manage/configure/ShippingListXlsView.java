package com.webstarter.manage.configure;

import com.webstarter.manage.model.PaymentOrderModel;
import com.webstarter.manage.model.ShippingModel;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Log4j2
@Component("ShippingListXls")
public class ShippingListXlsView extends AbstractXlsView {
    @Override protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
                                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "attachment; filename=\"dreamer_shipping_list.xls\"");
        CellStyle numberCellStyle = workbook.createCellStyle();
        DataFormat numberDataFormat = workbook.createDataFormat();
        numberCellStyle.setDataFormat(numberDataFormat.getFormat("#,##0"));
        Sheet sheet = workbook.createSheet("student_sheet");

        String[] firstRow = new String[]{"등록일시","학생명","주문상품","상태","수령인","배송정보"};

        Row row = null;
        Cell cell = null;
        int rowNum = 0;

        // Header
        row = sheet.createRow(rowNum++);
        for(int i =0; i<firstRow.length; i++){
            cell = row.createCell(i);
            cell.setCellValue(firstRow[i]);
        }

        List<ShippingModel> reqModel = (List<ShippingModel>) model.get("resList");

        // Body
        for (int i=0; i<reqModel.size(); i++) {
            row = sheet.createRow(rowNum++);
            for( int j=0; j<firstRow.length; j++){

                cell = row.createCell(j);cell.setCellValue(reqModel.get(i).getRegDt()); j++;
                cell = row.createCell(j);cell.setCellValue(reqModel.get(i).getUserName()); j++;
                if(reqModel.get(i).getSubscribeName() != null){
                    cell = row.createCell(j);cell.setCellValue(reqModel.get(i).getSubscribeName()+" "+reqModel.get(i).getSubscribeTerm()+"일"); j++;
                }else{
                    cell = row.createCell(j);cell.setCellValue(reqModel.get(i).getItemsName()); j++;
                }
                if("1".equals(reqModel.get(i).getShippingStatus())){
                    cell = row.createCell(j);cell.setCellValue("발송완료"+reqModel.get(i).getShippingServiceName()+reqModel.get(i).getShippingInvoice()); j++;
                }else{
                    cell = row.createCell(j);cell.setCellValue("발송전"); j++;
                }
                cell = row.createCell(j);cell.setCellValue(reqModel.get(i).getReceiverName()+reqModel.get(i).getReceiverCell()); j++;
                cell = row.createCell(j);cell.setCellValue(reqModel.get(i).getReceiverZipcode()+reqModel.get(i).getReceiverAddress()+reqModel.get(i).getMemo()); j++;

            }
        }

    }
}
