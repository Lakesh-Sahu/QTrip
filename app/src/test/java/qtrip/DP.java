package qtrip;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class DP {

    @DataProvider(name = "userOnboardData")
    public Object[][] dpMethod(String sheetName) throws IOException {
        int rowIndex = 0;
        int cellIndex = 0;
        List<List<String>> outputList = new ArrayList<>();

        FileInputStream excelFile = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/DatasetsForQTripTests.xlsx");
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet selectedSheet = workbook.getSheet(sheetName);
        for (Row nextRow : selectedSheet) {
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            List<String> innerList = new ArrayList<>();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (rowIndex > 0 && cellIndex > 0) {
                    if (cell.getCellType() == CellType.STRING) {
                        innerList.add(cell.getStringCellValue());
                    } else if (cell.getCellType() == CellType.NUMERIC) {
                        innerList.add(String.valueOf(cell.getNumericCellValue()));
                    }
                }
                cellIndex = cellIndex + 1;
            }
            rowIndex = rowIndex + 1;
            cellIndex = 0;
            if (!innerList.isEmpty())
                outputList.add(innerList);
        }
        workbook.close();

        excelFile.close();

        return outputList.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
    }
}