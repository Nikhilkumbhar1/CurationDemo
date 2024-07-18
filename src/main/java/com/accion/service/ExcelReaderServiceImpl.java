package com.accion.service;

import com.accion.model.Entries;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelReaderServiceImpl implements ExcelReaderService {

    @Value("${excel.file.path}")
    private String excelFilePath;
    public List<Entries> readExcel()  {
        List<Entries> entriesList = new ArrayList<>();
        try {

            FileInputStream inputStream = new FileInputStream(excelFilePath);
            Workbook workbook = WorkbookFactory.create(inputStream);

            Sheet sheet = workbook.getSheetAt(0);

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");


            for (Row row : sheet) {
                if (row.getRowNum() == 0) { // Skip header row
                    continue;
                }

                Entries entry = new Entries();

                for (Cell cell : row) {
                    int columnIndex = cell.getColumnIndex();
                    switch (columnIndex) {
                        case 0:
                            if (cell.getCellType() == CellType.STRING) {
                                try {
                                    entry.setDateReported(dateFormat.parse(cell.getStringCellValue()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            } else if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                                entry.setDateReported(cell.getDateCellValue());
                            }
                            break;
                        case 1:
                            if (cell.getCellType() == CellType.STRING) {
                                entry.setReportedBy(cell.getStringCellValue());
                            }
                            break;
                        case 2:
                            if (cell.getCellType() == CellType.STRING) {
                                entry.setEntryNumber1(cell.getStringCellValue());
                            }
                            break;
                        case 3:
                            if (cell.getCellType() == CellType.STRING) {
                                entry.setArtifactID(cell.getStringCellValue());
                            }
                            break;
                        case 4:
                            if (cell.getCellType() == CellType.STRING) {
                                entry.setLibraryName(cell.getStringCellValue());
                            }
                            break;
                        case 5:
                            if (cell.getCellType() == CellType.STRING) {
                                entry.setAssignee(cell.getStringCellValue());
                            }
                            break;
                        case 6:
                            if (cell.getCellType() == CellType.STRING) {
                                entry.setVulnerabilityIdentifier(cell.getStringCellValue());
                            }
                            break;
                        case 7:
                            if (cell.getCellType() == CellType.STRING) {
                                entry.setAssignmentStatus(cell.getStringCellValue());
                            }
                            break;
                    }
                }

                entriesList.add(entry);
            }


            workbook.close();
            inputStream.close();



        }catch (Exception e){

        }
        return entriesList;

    }


}
