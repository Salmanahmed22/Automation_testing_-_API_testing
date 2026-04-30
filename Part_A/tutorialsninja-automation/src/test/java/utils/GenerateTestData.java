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
        createCheckoutSheet(workbook);
        createCartSheet(workbook);
        createSubcategorySheet(workbook);
        createBreadcrumbSheet(workbook);

        String path = "src/test/resources/testdata/TestData.xlsx";
        try (FileOutputStream fos = new FileOutputStream(path)) {
            workbook.write(fos);
        }
        workbook.close();
        System.out.println("TestData.xlsx created at: " + path);
    }

    // TC1 — one unique user per run (timestamp in email avoids duplicate account error)
    private static void createRegistrationSheet(XSSFWorkbook wb) {
        XSSFSheet sheet = wb.createSheet("Registration");
        writeRow(sheet, 0, "firstName", "lastName", "email", "telephone", "password", "confirmPassword");
        long ts = System.currentTimeMillis();
        writeRow(sheet, 1, "John", "Doe", "john_" + ts + "@test.com", "0501234567", "Test@1234", "Test@1234");
    }

    // TC3 (row 0 = valid) + TC4 (row 1 = invalid) — each login test reads its row by index
    private static void createLoginSheet(XSSFWorkbook wb) {
        XSSFSheet sheet = wb.createSheet("Login");
        writeRow(sheet, 0, "email", "password", "expectedResult");
        writeRow(sheet, 1, "testuser_automation@gmail.com", "Test@1234", "success");
        writeRow(sheet, 2, "wrong@email.com", "wrongpass", "failure");
    }

    // TC8 — Mac search only; Apple/subcategory search is TC9 (SubcategorySearchTest)
    private static void createSearchSheet(XSSFWorkbook wb) {
        XSSFSheet sheet = wb.createSheet("Search");
        writeRow(sheet, 0, "keyword", "expectedProduct");
        writeRow(sheet, 1, "Mac", "Mac");
    }

    // TC5
    private static void createCurrencySheet(XSSFWorkbook wb) {
        XSSFSheet sheet = wb.createSheet("Currency");
        writeRow(sheet, 0, "email", "password", "currency");
        writeRow(sheet, 1, "testuser_automation@gmail.com", "Test@1234", "EUR");
    }

    // TC11 — added category and product columns so navigation/product are not hardcoded
    private static void createCheckoutSheet(XSSFWorkbook wb) {
        XSSFSheet sheet = wb.createSheet("Checkout");
        writeRow(sheet, 0,
                "email", "password", "firstName", "lastName",
                "address", "city", "postcode", "country", "zone",
                "comment", "category", "product");
        writeRow(sheet, 1,
                "testuser_automation@gmail.com", "Test@1234", "John", "Doe",
                "123 Test Street", "Dubai", "00000", "United Arab Emirates", "Dubai",
                "Test comment", "MP3 Players", "iPod Shuffle");
    }

    // TC10 — two categories and products so CartTest has no hardcoded values
    private static void createCartSheet(XSSFWorkbook wb) {
        XSSFSheet sheet = wb.createSheet("Cart");
        writeRow(sheet, 0, "email", "password", "category1", "product1", "category2", "product2");
        writeRow(sheet, 1,
                "testuser_automation@gmail.com", "Test@1234",
                "Tablets", "Samsung Galaxy Tab 10.1",
                "Laptops", "MacBook");
    }

    // TC9 — keyword, category filter, and expected product are all in Excel
    private static void createSubcategorySheet(XSSFWorkbook wb) {
        XSSFSheet sheet = wb.createSheet("Subcategory");
        writeRow(sheet, 0, "email", "password", "keyword", "category", "expectedProduct");
        writeRow(sheet, 1,
                "testuser_automation@gmail.com", "Test@1234",
                "Apple", "Components", "Apple Cinema 30");
    }

    // TC6 — category to navigate and expected breadcrumb text are in Excel
    private static void createBreadcrumbSheet(XSSFWorkbook wb) {
        XSSFSheet sheet = wb.createSheet("Breadcrumb");
        writeRow(sheet, 0, "email", "password", "category", "expectedBreadcrumb");
        writeRow(sheet, 1,
                "testuser_automation@gmail.com", "Test@1234",
                "Tablets", "Tablets");
    }

    private static void writeRow(XSSFSheet sheet, int rowIndex, String... values) {
        XSSFRow row = sheet.createRow(rowIndex);
        for (int i = 0; i < values.length; i++) {
            row.createCell(i).setCellValue(values[i]);
        }
    }
}
