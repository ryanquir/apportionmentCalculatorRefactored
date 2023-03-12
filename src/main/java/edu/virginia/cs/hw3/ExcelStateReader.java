package edu.virginia.cs.hw3;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;



public class ExcelStateReader extends StateReader{
    private String filename;

    public ExcelStateReader(String filename) {
        this.filename = filename;
    }

    @Override
    public void readStates() {
        try {
            Sheet sheet;
            sheet = getSheet(filename);
            sheet.removeRow(sheet.getRow(0));
            createHash_xlsx(sheet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static Sheet getSheet(String fileName) throws IOException {
        FileInputStream input = new FileInputStream(fileName);
        Workbook workbook;
        if (fileName.endsWith(".xlsx")) {
            workbook = new XSSFWorkbook(input);
            return workbook.getSheetAt(0);
        } else if (fileName.endsWith(".xls")) {
            workbook = new HSSFWorkbook(input);
            return workbook.getSheetAt(0);
        } else {
            throw new RuntimeException("Invalid input file type");
        }
    }
    private void createHash_xlsx(Sheet sheet) {
        float tempVal;
        int tempInt;
        for (Row row : sheet) {
            if (row.getPhysicalNumberOfCells() >= 2) {
                tempVal = (float) row.getCell(1).getNumericCellValue();
                tempInt = (int) tempVal;
                if (tempVal - tempInt == 0 && tempVal >= 0) {
                    String stateName = row.getCell(0).getStringCellValue();
                    int statePopulation = (int) row.getCell(1).getNumericCellValue();
                    State newState = new State(stateName, statePopulation);
                    stateList.add(newState);
                }
            }
        }
    }
}

