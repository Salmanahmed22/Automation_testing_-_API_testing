package utils;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class GenerateTestData {

    public static void main(String[] args) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();

        createRegistrationSheet(workbook);
        createLoginSheet(workbook);
        createSearchSheet(workbook);
        createCurrencySheet(workbook);
        createSortBySheet(workbook);
        createCheckoutSheet(workbook);

        String path = "src/test/resources/testdata/TestData.xlsx";
        try (FileOutputStream fos = new FileOutputStream(path)) {
            workbook.write(fos);
        }
        workbook.close();
        System.out.println("TestData.xlsx created at: " + path);
    }

    private static void createRegistrationSheet(XSSFWorkbook wb) {
        XSSFSheet sheet = wb.createSheet("Registration");
        writeRow(sheet, 0, "firstName", "lastName", "email", "telephone", "password", "confirmPassword");
        writeRow(sheet, 1, "John", "Doe", "johndoe_auto1@test.com", "0501234567", "Test@1234", "Test@1234");
        writeRow(sheet, 2, "Jane", "Smith", "janesmith_auto2@test.com", "0507654321", "Pass@5678", "Pass@5678");
    }

    private static void createLoginSheet(XSSFWorkbook wb) {
        XSSFSheet sheet = wb.createSheet("Login");
        writeRow(sheet, 0, "email", "password", "expectedResult");
        writeRow(sheet, 1, "testuser_automation@gmail.com", "Test@1234", "success");
        writeRow(sheet, 2, "wrong@email.com", "wrongpass", "failure");
    }

    private static void createSearchSheet(XSSFWorkbook wb) {
        XSSFSheet sheet = wb.createSheet("Search");
        writeRow(sheet, 0, "keyword", "expectedProduct");
        writeRow(sheet, 1, "Mac", "Mac");
        writeRow(sheet, 2, "Apple", "Apple Cinema 30");
    }

    private static void createCurrencySheet(XSSFWorkbook wb) {
        XSSFSheet sheet = wb.createSheet("Currency");
        writeRow(sheet, 0, "email", "password", "currency");
        writeRow(sheet, 1, "testuser_automation@gmail.com", "Test@1234", "EUR");
    }

    private static void createSortBySheet(XSSFWorkbook wb) {
        XSSFSheet sheet = wb.createSheet("SortBy");
        writeRow(sheet, 0, "email", "password", "sortOrder");
        writeRow(sheet, 1, "testuser_automation@gmail.com", "Test@1234", "Name (A - Z)");
        writeRow(sheet, 2, "testuser_automation@gmail.com", "Test@1234", "Name (Z - A)");
    }

    private static void createCheckoutSheet(XSSFWorkbook wb) {
        XSSFSheet sheet = wb.createSheet("Checkout");
        writeRow(sheet, 0, "email", "password", "firstName", "lastName", "address", "city", "postcode", "country", "zone", "comment");
        writeRow(sheet, 1, "testuser_automation@gmail.com", "Test@1234", "John", "Doe", "123 Test Street", "Dubai", "00000", "United Arab Emirates", "Dubai", "Test comment");
    }

    private static void writeRow(XSSFSheet sheet, int rowIndex, String... values) {
        XSSFRow row = sheet.createRow(rowIndex);
        for (int i = 0; i < values.length; i++) {
            row.createCell(i).setCellValue(values[i]);
        }
    }
}
