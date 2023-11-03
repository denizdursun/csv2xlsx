package com.example.csv2xlsx.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.example.csv2xlsx.model.CsvRow;

@Component
public class CsvToExcelService {

    private Font titleFont;
    private XSSFCellStyle titleStyle = null;

    public void csvToExcel() throws Exception {
        // CSV dosyasını okuyun
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data.csv");
        List<CsvRow> csvRows = CsvRow.parse(inputStream);

        // Satırları "CityName" alanına göre gruplayın
        Map<String, List<CsvRow>> groupedCsvRows = csvRows.stream()
                .collect(Collectors.groupingBy(CsvRow::getCityName));

        ArrayList<String> headers = new ArrayList<>();
        headers.add("CompanyId");
        headers.add("TABELAADI");
        headers.add("VERGINO");
        headers.add("ENLEM");
        headers.add("BOYLAM");
        headers.add("SEHIR");
        headers.add("BOLGE");
        headers.add("MAHALLE");
        headers.add("SOKAK");
        headers.add("ISLETMEKODU");
        

        for (String cityName : groupedCsvRows.keySet()) {
            if(cityName == null || cityName=="")
                continue;

            // Her şehir için bir satır oluşturun
            int rowNum = 1;
            
            Sheet sheet = this.createWorkBook(cityName);
            // Başlıkları ekleyin ve stilini ayarlayın
            org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0);
            for (int j = 0; j < headers.size(); j++) {
                String header = headers.get(j);
                Cell headerCell = headerRow.createCell(j);
                headerCell.setCellValue(header);
                headerCell.setCellStyle(titleStyle);
                sheet.autoSizeColumn(j);
            }

            sheet.createFreezePane(0, 1);

            for (CsvRow csvRow : groupedCsvRows.get(cityName)) {
                // Yeni bir satır oluşturun
                org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum++);

                // Satırdaki değerleri ekleyin
                for (int i = 0; i < csvRow.size(); i++) {
                    row.createCell(i).setCellValue(csvRow.get(i));
                }
            }

            sheet.setAutoFilter(CellRangeAddress.valueOf("A1:J1"));
            //sheet.setAutoFilter(new CellRangeAddress(firstCell.getRow(), lastCell.getRow(), firstCell.getCol(), lastCell.getCol()));
            // Excel dosyasını kaydedin
            FileOutputStream outputStream = new FileOutputStream(new File("eski_" + cityName.replace("\"", "-_-") + ".xlsx"));
            sheet.getWorkbook().write(outputStream);
            outputStream.close();
        }
    }

    private Sheet createWorkBook(String cityName) {
        Workbook workbook = new XSSFWorkbook();

        
        // set title font
        titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 11);

        // set title style
        XSSFColor myColor = new XSSFColor(java.awt.Color.BLACK);
        titleStyle = (XSSFCellStyle) workbook.createCellStyle();
        titleStyle.setWrapText(false);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setFont(titleFont);
        titleStyle.setBorderBottom(BorderStyle.THIN);
        titleStyle.setBorderTop(BorderStyle.THIN);
        titleStyle.setBorderLeft(BorderStyle.THIN);
        titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setBottomBorderColor(myColor);
        titleStyle.setTopBorderColor(myColor);
        titleStyle.setLeftBorderColor(myColor);
        titleStyle.setRightBorderColor(myColor);
        titleStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        return workbook.createSheet(cityName);
    }
}