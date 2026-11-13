package Day05;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadToFile {
    public static void main(String[]args) throws IOException {
try(FileInputStream file = new FileInputStream("DataLogin.xlsx");
        Workbook workbook = new XSSFWorkbook(file)){
    Sheet sheetDataLogin = workbook.getSheetAt(0);
    DataFormatter dataFormate = new DataFormatter();
    for(Row row : sheetDataLogin){
        if (row.getRowNum()==0)continue;
        String user = dataFormate.formatCellValue(row.getCell(0)).trim();
        String pass = dataFormate.formatCellValue(row.getCell(1)).trim();
        if (!user.isEmpty()|| !pass.isEmpty()){
            System.out.println("User:" + user);
            System.out.println("Pass:" + pass);
        }
    }
        }
catch (IOException e){
    System.out.printf("Xay ra loi khi doc file :" +e.getMessage());
}
    }
}
