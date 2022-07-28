package base;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
    PropReader propReader =new PropReader();
    File file=new File(propReader.getProp("ExcelFilePath"));
    String sheetName="register";


    public  Map<String,String> getData(String username) throws IOException {
        Map<String,String>data=new HashMap<>();
        FileInputStream fis=new FileInputStream(file);
        XSSFWorkbook workbook =new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet(sheetName);

        Row headerRow=sheet.getRow(sheet.getFirstRowNum());
        Iterator<Cell>headers=headerRow.cellIterator();
        while (headers.hasNext()){
            String headerValue= headers.next().getStringCellValue();
            data.put(headerValue,null);
        }
        boolean userFound= false;
        Iterator<Row> rows=sheet.rowIterator();
        while (rows.hasNext()){
           Row row=rows.next();
           String firstCellValue=row.getCell(row.getFirstCellNum()).getStringCellValue();
           if (firstCellValue.contains(username)){
               userFound=true;
               Iterator<Cell> cells=row.cellIterator();
                while (cells.hasNext()){
                    Cell cell= cells.next();
                    String  cellValue =null;
                    if (cell.getCellType()== CellType.STRING){
                        cellValue= cell.getStringCellValue();
                    }else if (cell.getCellType()== CellType.NUMERIC){
                        cellValue= NumberToTextConverter.toText(cell.getNumericCellValue());
                    }
                    else {
                        cellValue=null;
                    }
                    String relatedHeaderCellValue=headerRow.getCell(cell.getColumnIndex()).getStringCellValue();
                    data.replace(relatedHeaderCellValue,null,cellValue);
                }
           }
        }
        if (!userFound){
            System.out.println("Username "+username+" not found");
            return null;
        }else{
            return data;
        }

    }



}
