package com.sergey.zhuravlev.pizza.controller;

import com.sergey.zhuravlev.pizza.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    //TODO @Secured("ROLE_ADMIN")
    @GetMapping("/order")
    public HttpEntity<byte[]> downloadOrderReport() throws Exception {
        byte[] documentBody = reportService.generateOrderReport();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf("application/vnd.ms-excel"));
        header.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=OrderReport.xls");
        header.setContentLength(documentBody.length);

        return new HttpEntity<>(documentBody, header);
    }

}
