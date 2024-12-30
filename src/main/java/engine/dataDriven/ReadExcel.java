package engine.dataDriven;

import engine.logger.CustomLogger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ReadExcel {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private XSSFRow row;

    //TODO:Default Constructor
    public ReadExcel() {
    }

//    //TODO: Constructor
//    public ReadExcel(String filePath, String sheetName) {
//        readFile(filePath, sheetName);
//    }

    private void readFile(String filePath, String sheetName) {
        try (FileInputStream FileInputStream = new FileInputStream(filePath)) {
            workbook = new XSSFWorkbook(FileInputStream);
            sheet = workbook.getSheet(sheetName);
        } catch (IOException e) {
            CustomLogger.getLogger().fatal("Can't find the file at: {}", filePath);
        }
    }

    private String getCellData(XSSFCell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case FORMULA:
                return String.valueOf(cell.getCellFormula());
            default:
                return "";
        }
    }

    //TODO: Retrieve cell data using row & column indices
    public String getCellData(int rowIndex, int columnIndex) {
        return getCellDataByIndices(rowIndex, columnIndex);
    }

    //TODO: Retrieve cell data using row name & column index
    public String getCellData(String rowName, int columnIndex) {
        int rowNumber = getRowIndexByName(rowName);
        return getCellDataByIndices(rowNumber, columnIndex);
    }

    //TODO: Retrieve cell data using row index & column name
    public String getCellData(int rowIndex, String columnName) {
        int columnNumber = getColumnIndexByName(columnName);
        return getCellDataByIndices(rowIndex, columnNumber);
    }

    //TODO: Retrieve cell data using row & column names
    public String getCellData(String rowName, String columnName) {
        int rowNumber = getRowIndexByName(rowName);
        int columnNumber = getColumnIndexByName(columnName);
        return getCellDataByIndices(rowNumber, columnNumber);
    }


    //TODO: Get total number of columns
    private int getTotalColumnCount() {
        if (sheet == null) return 0;
        int maxColumnCount = 0;
        for (Row row : sheet) {
            int lastCellNum = row.getLastCellNum();
            if (lastCellNum > maxColumnCount) {
                maxColumnCount = lastCellNum;
            }
        }
        return maxColumnCount;
    }

    //TODO: Get total number of rows
    private int getTotalRowCount() {
        return sheet == null ? 0 : sheet.getPhysicalNumberOfRows();
    }

    //TODO: Helper Methods To Retrieve cell data by row and column indices
    private String getCellDataByIndices(int rowNum, int columnNumber) {
        if (sheet == null || rowNum < 0 || rowNum >= getTotalRowCount()) {
            return ""; // Return empty string if sheet is null or indices are out of bounds
        }

        row = sheet.getRow(rowNum);
        if (row == null) {
            return ""; // Return empty string if row is null
        }

        XSSFCell cell = row.getCell(columnNumber);
        return cell == null ? "" : cell.toString(); // Return cell value or empty string if cell is null
    }

    //TODO: Helper Methods To Retrieve
    private int getRowIndexByName(String rowName) {
        if (sheet == null || rowName == null) {
            return -1;
        }
        for (int i = 0; i < getTotalRowCount(); i++) {
            row = sheet.getRow(i);
            if (row != null && row.getCell(0) != null) {
                if (row.getCell(0).toString().equalsIgnoreCase(rowName)) {
                    return i;
                }
            }
        }
        return -1; // Return -1 if row name is not found
    }

    //TODO: Helper Methods To Retrieve column index by column name
    private int getColumnIndexByName(String columnName) {
        if (sheet == null || columnName == null) {
            return -1;
        }
        row = sheet.getRow(0); // Assume first row contains column names
        if (row == null) {
            return -1;
        }
        for (int i = 0; i < getTotalColumnCount(); i++) {
            XSSFCell cell = row.getCell(i);
            if (cell != null && cell.toString().equalsIgnoreCase(columnName)) {
                return i;
            }
        }
        return -1; // Return -1 if column name is not found
    }

    //TODO: Switch to a specific sheet by name
    public boolean switchToSheet(String sheetName) {
        try {
            sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                CustomLogger.getLogger().error("Sheet not found: " + sheetName);
                return false;
            }
        } catch (Exception e) {
            CustomLogger.getLogger().fatal("Couldn't switch to sheet: " + sheetName, e);
            return false;
        }
        return true;
    }

    public Object[][] readDataHashMapByRowCondition(String filePath, String sheetName, String conditionColumn, String conditionValue) {
        // Initialize variables
        List<LinkedHashMap<String, String>> filteredData = new ArrayList<>();

        // Read the file and sheet
        readFile(filePath, sheetName);

        // Ensure the sheet is not null
        if (sheet == null) {
            CustomLogger.getLogger().error("Sheet '{}' not found in file: {}", sheetName, filePath);
            throw new IllegalArgumentException("Sheet not found: " + sheetName);
        }

        // Get headers from the first row
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            CustomLogger.getLogger().error("Header row is missing in sheet: {}", sheetName);
            throw new IllegalStateException("Header row is missing in sheet: " + sheetName);
        }

        // Iterate through all rows to filter data based on the condition
        for (int rowIndex = 1; rowIndex < getTotalRowCount(); rowIndex++) {
            Row currentRow = sheet.getRow(rowIndex);
            if (currentRow == null) continue;

            // Check if the condition column matches the specified condition value
            String cellValue = getCellData(rowIndex, conditionColumn);
            if (conditionValue.equalsIgnoreCase(cellValue)) {
                LinkedHashMap<String, String> rowData = new LinkedHashMap<>();

                // Populate row data into a LinkedHashMap using headers as keys
                for (int colIndex = 0; colIndex < getTotalColumnCount(); colIndex++) {
                    String header = getCellData(0, colIndex).replaceAll(" ", "").toLowerCase();
                    String value = getCellData(rowIndex, colIndex);
                    rowData.put(header, value);
                }

                filteredData.add(rowData);
            }
        }

        // Check if any data matched the condition
        if (filteredData.isEmpty()) {
            CustomLogger.getLogger().error("No data found matching condition '{}' in column '{}' of file: {}", conditionValue, conditionColumn, filePath);
            throw new AssertionError("No data found matching condition: " + conditionValue);
        }

        CustomLogger.getLogger().info("Number of matching rows: {}", filteredData.size());

        // Convert the list of LinkedHashMap into a 2D Object array
        Object[][] resultData = new Object[filteredData.size()][1];
        for (int i = 0; i < filteredData.size(); i++) {
            resultData[i][0] = filteredData.get(i);
        }

        return resultData;
    }

    public String readCertainCell(String filePath, String sheetName, String columnName, String rowName) {
        readFile(filePath, sheetName);
        int colNum = getColumnIndexByName(columnName);
        int rowNum = getRowIndexByName(rowName);
        return getCellData(rowNum, colNum);
    }
}
