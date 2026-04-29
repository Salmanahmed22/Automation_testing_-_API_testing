package utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {

    public static Object[][] getTestData(String sheetName) {
        FileInputStream fis = null;
        XSSFWorkbook workbook = null;
        try {
            fis = new FileInputStream("src/test/resources/testdata/TestData.xlsx");
            workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet(sheetName);

            int rowCount = sheet.getLastRowNum();
            int colCount = sheet.getRow(0).getLastCellNum();
            DataFormatter formatter = new DataFormatter();

            Object[][] data = new Object[rowCount][colCount];
            for (int i = 1; i <= rowCount; i++) {
                for (int j = 0; j < colCount; j++) {
                    data[i - 1][j] = formatter.formatCellValue(sheet.getRow(i).getCell(j));
                }
            }
            return data;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file: " + sheetName, e);
        } finally {
            try {
                if (workbook != null) workbook.close();
                if (fis != null) fis.close();
            } catch (IOException ignored) {}
        }
    }
}
