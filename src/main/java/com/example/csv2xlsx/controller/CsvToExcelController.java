package com.example.csv2xlsx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.csv2xlsx.service.CsvToExcelService;

@RestController
@RequestMapping("/csv")
public class CsvToExcelController {

    @Autowired
    private CsvToExcelService csvToExcelService;

    @GetMapping
    public void csvToExcel() throws Exception {
        csvToExcelService.csvToExcel();
    }

}
