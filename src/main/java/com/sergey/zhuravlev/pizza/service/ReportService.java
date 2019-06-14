package com.sergey.zhuravlev.pizza.service;

import com.sergey.zhuravlev.pizza.entity.Order;
import com.sergey.zhuravlev.pizza.entity.PizzaOrder;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final OrderService orderService;

    public byte[] generateOrderReport() {
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("Заказы");

        Row header = sheet.createRow(0);
        Cell numberHeader = header.createCell(0);
        numberHeader.setCellValue("Номер");
        Cell countHeader = header.createCell(1);
        countHeader.setCellValue("Количество");
        Cell customerHeader = header.createCell(2);
        customerHeader.setCellValue("Покупатель");
        Cell orderDateHeader = header.createCell(3);
        orderDateHeader.setCellValue("Время заказа");
        Cell totalPriceHeader = header.createCell(4);
        totalPriceHeader.setCellValue("Цена");

        List<Order> orders = orderService.list(Pageable.unpaged()).getContent();

        for (int i = 0; i < orders.size(); i++) {
            Row row = sheet.createRow(i + 1);
            Cell numberCell = row.createCell(0);
            numberCell.setCellValue(orders.get(i).getId());
            Cell countCell = row.createCell(1);
            countCell.setCellValue(orders.get(i).getOrders().stream().mapToLong(PizzaOrder::getCount).sum());
            Cell customerCell = row.createCell(2);
            customerCell.setCellValue(orders.get(i).getCustomer().getFirstName()
                    + " " +
                    orders.get(i).getCustomer().getLastName());
            Cell orderDateCell = row.createCell(3);
            DataFormat format = book.createDataFormat();
            CellStyle dateStyle = book.createCellStyle();
            dateStyle.setDataFormat(format.getFormat("dd.mm.yyyy"));
            orderDateCell.setCellStyle(dateStyle);
            orderDateCell.setCellValue(orders.get(i).getOrderDate());
            Cell totalPriceCell = row.createCell(4);
            totalPriceCell.setCellValue(orders.get(i).getTotalPrice().toString());
        }

        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            book.write(baos);
            book.close();
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
