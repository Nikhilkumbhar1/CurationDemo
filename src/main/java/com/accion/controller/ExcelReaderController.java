package com.accion.controller;

import com.accion.model.Entries;
import com.accion.service.ExcelReaderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/excel")
public class ExcelReaderController {

    @Autowired
    private ExcelReaderServiceImpl excelReaderService;

    @GetMapping("/read")
    public List<Entries> readExcel() throws IOException {
        return excelReaderService.readExcel();
    }


}
