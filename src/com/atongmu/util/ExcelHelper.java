package com.atongmu.util;


import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用于对excel文件进行简单读取和写入的工具类
 * date: 2016年2月25日 16:09:10
 */
public class ExcelHelper {

    /**
     * 读取excel中的数据,以数组形式返回
     *
     * @param fileName
     * @param columnNumber 为-1的时候，获取所有的列
     * @param sheetName
     * @return
     * @throws Exception
     */

    public static String[][] getCSVData(String fileName, int columnNumber, String sheetName, int colCount)
            throws Exception {
        List<List> resList = new ArrayList();
        String[][] arr = null;
        BufferedReader reader = null;
        String string = "";
        try {
            reader = new BufferedReader(new FileReader(fileName));//换成你的文件名
            reader.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
            String line = null;
            int lineCount = 0;
            while ((line = reader.readLine()) != null) {
                lineCount++;

                if (StringUtils.isBlank(line)) continue;

                line = line.substring(1, line.length() - 1);
                String item[] = line.split("\",\"");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                if (colCount != item.length) {
                    System.out.println("item length:" + item.length + " lineCount:" + lineCount + " line:" + line);
                    continue;
                }

                List tmp = Arrays.asList(item);
                resList.add(tmp);
            }
            System.out.println("Finish reading.");
            //将list转为数组
            arr = new String[resList.size()][];
            for (int i = 0; i < resList.size(); i++) {
                arr[i] = (String[]) resList.get(i).toArray(new String[resList.get(i).size()]);
            }

        } catch (Exception ex) {
            LogHelper.error("getExcelData [fileName:{0},sheetName:{1},ex:{2}]", fileName, sheetName, ex);
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (Exception ex) {
                LogHelper.error("getExcelData [inputStream.close,ex:{0}]", ex);
            }
        }
        return arr;
    }

    /**
     * 读取excel中的数据,以数组形式返回
     *
     * @param fileName
     * @param columnNumber 为-1的时候，获取所有的列
     * @param sheetName
     * @return
     * @throws Exception
     */

    public static String[][] getExcelData(String fileName, int columnNumber, String sheetName)
            throws Exception {
        String[][] arr = null;
        HSSFWorkbook workBook = null;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(fileName));
            workBook = new HSSFWorkbook(inputStream);
            HSSFSheet childSheet = workBook.getSheet(sheetName);

            int rowNum = childSheet.getPhysicalNumberOfRows();

            for (int j = 0; j < rowNum; j++) {
                HSSFRow row = childSheet.getRow(j);
                if (row != null) {
                    if (columnNumber == -1) {
                        columnNumber = row.getPhysicalNumberOfCells() + 1;

                    }
                    if (j == 0)
                        arr = new String[rowNum][columnNumber];
                    for (short k = 0; k < columnNumber; k++) {
                        HSSFCell cell = row.getCell(k);
                        arr[j][k] = parseExcel(cell);
                    }
                }
            }

        } catch (Exception ex) {
            LogHelper.error("getExcelData [fileName:{0},sheetName:{1},ex:{2}]", fileName, sheetName, ex);
        } finally {
            try {
                if (inputStream != null)

                    inputStream.close();
            } catch (Exception ex) {
                LogHelper.error("getExcelData [inputStream.close,ex:{0}]", ex);
            }
        }
        return arr;
    }


    /**
     * 读取excel中的数据,以map形式返回
     *
     * @param fileName
     * @param columnNumber 为-1的时候，获取所有的列
     * @return
     * @throws Exception
     */
    public static Map<String, String[][]> getExcelMapData(

            String fileName, int columnNumber) throws Exception {
        Map<String, String[][]> mapData = new HashMap<String, String[][]>();

        String[][] arr = null;
        HSSFWorkbook workBook = null;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(fileName));

            workBook = new HSSFWorkbook(inputStream);

            int sheetNum = workBook.getNumberOfSheets();
            for (int i = 0; i < sheetNum; i++) {
                HSSFSheet childSheet = workBook.getSheetAt(i);

                String sheetName = workBook.getSheetName(i);

                int rowNum = childSheet.getPhysicalNumberOfRows();
                for (int j = 0; j < rowNum; j++) {
                    HSSFRow row = childSheet.getRow(j);

                    if (row != null) {
                        if (columnNumber == -1) {
                            columnNumber = row.getPhysicalNumberOfCells() + 1;
                        }
                        if (j == 0)
                            arr = new String[rowNum][columnNumber];
                        for (short k = 0; k < columnNumber; k++) {
                            HSSFCell cell = row.getCell(k);
                            arr[j][k] = parseExcel(cell);
                        }
                    }
                }
                mapData.put(sheetName, arr);
            }
        } catch (Exception ex) {
            LogHelper.error("getExcelMapData [fileName:{0},ex:{1}]", fileName, ex);
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (Exception ex) {
                LogHelper.error("getExcelMapData [inputStream.close,ex:{0}]", ex);
            }
        }

        return mapData;
    }

    /**
     * 浮点数的精确位数
     */
    private static DecimalFormat DECIMALFORMAT = new DecimalFormat("#.######");

    /**
     * 解析单元格的数据格式
     *
     * @param cell
     * @return
     */
    private static String parseExcel(HSSFCell cell) {
        if (cell == null)
            return "";
        String result = new String();
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC:// 数字类型
                // 处理日期格式、 时间格式
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = null;
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = cell.getDateCellValue();
                    result = sdf.format(date);
                } else {
                    double va = cell.getNumericCellValue();
                    // 去掉数值类型后面的".0"
                    if (va == (int) va)
                        result = String.valueOf((int) va);
                    else
                        result = DECIMALFORMAT.format(va);
                }
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                try {
                    result = String.valueOf(cell.getNumericCellValue());
                } catch (IllegalStateException e) {
                    result = cell.getStringCellValue();
                }
                break;
            case HSSFCell.CELL_TYPE_STRING:// String类型
                result = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                result = "";
            default:
                result = "";
                break;
        }

        return result;
    }

    /**
     * 将数据写入到excel文件中
     *
     * @param fileName
     * @param data
     * @throws IOException
     */
    public static void exportExcels(String fileName, String[][] data) throws IOException {
        OutputStream outputStream = null;
        try {
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("new sheet");
            int rows = 0;
            for (String[] cells : data) {
                HSSFRow row = sheet.createRow(rows++);
                int cols = 0;
                for (String cellValue : cells) {
                    HSSFCell cell = row.createCell(cols++);
                    cell.setCellValue(cellValue);
                }
            }
            outputStream = new FileOutputStream(fileName);
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            LogHelper.error("exportExcels [fileName:{0},ex:{1}]", fileName, ex);
        } finally {
            try {
                if (outputStream != null)
                    outputStream.close();
            } catch (Exception ex) {
                LogHelper.error("exportExcels [outputStream.close(),ex:{0}]", ex);
            }
        }
    }

}
