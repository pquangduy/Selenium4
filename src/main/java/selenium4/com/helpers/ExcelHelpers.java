package selenium4.com.helpers;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import selenium4.com.constants.FrameworkConstants;
import selenium4.com.exceptions.InvalidPathForExcelException;
import selenium4.com.utils.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class ExcelHelpers {

	private FileInputStream fileIn;
	private FileOutputStream fileOut;
	private Workbook workbook;
	private Sheet sheet;
	private Cell cell;
	private Row row;
	private String excelFilePath;
	private Map<String, Integer> columns = new HashMap<>();

	public ExcelHelpers() {
		excelFilePath = Helpers.getCurrentDir() + FrameworkConstants.EXCEL_DATA_FILE_PATH;
		LogUtils.info("Excel File: " + excelFilePath);
		try {
			workbook = getWorkbook(excelFilePath);
			sheet = getSheet();
			addColumnHeader();
		} catch (Exception e) {
			e.getMessage();
			LogUtils.error(e.getMessage());
		}
	}

	public ExcelHelpers(String sheetName) {
		excelFilePath = Helpers.getCurrentDir() + FrameworkConstants.EXCEL_DATA_FILE_PATH;
		LogUtils.info("Excel File: " + excelFilePath);
		try {
			workbook = getWorkbook(excelFilePath);
			sheet = getSheet(sheetName);
			addColumnHeader();
		} catch (Exception e) {
			e.getMessage();
			LogUtils.error(e.getMessage());
		}
	}

	// Set Excel File
	public void setExcelFile(String excelPath, String sheetName) {
		LogUtils.info("Set Excel File: " + excelPath);
		LogUtils.info("Sheet Name: " + sheetName);
		try {
			workbook = getWorkbook(excelPath);
			sheet = getSheet(sheetName);
			addColumnHeader();
		} catch (Exception e) {
			e.getMessage();
			LogUtils.error(e.getMessage());
		}
	}

	// Phương thức này nhận số hàng làm tham số và trả về dữ liệu của hàng đó.
	public Row getRowData(int rowNum) {
		row = sheet.getRow(rowNum);
		return row;
	}

	public Object[][] getExcelData(String excelPath, String sheetName) {
		Object[][] data = null;
		LogUtils.info("Set Excel file " + excelPath);
		LogUtils.info("Selected Sheet: " + sheetName);
		try {
			File f = new File(excelPath);
			if (!f.exists()) {
				try {
					LogUtils.info("File Excel path not found.");
					throw new InvalidPathForExcelException("File Excel path not found.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (sheetName.isEmpty()) {
				try {
					LogUtils.info("The Sheet Name is empty.");
					throw new InvalidPathForExcelException("The Sheet Name is empty.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// load the file
			fileIn = new FileInputStream(f);
			String fileExtensionName = excelPath.substring(excelPath.indexOf("."));
			if (fileExtensionName.equals(".xlsx")) {
				workbook = new XSSFWorkbook(fileIn);
			} else if (fileExtensionName.equals(".xls")) {
				workbook = new HSSFWorkbook(fileIn);
			} else {
				try {
					fileIn.close();
					LogUtils.info("The excel extension is not in xlsx or xls.");
					throw new InvalidPathForExcelException("The excel extension is not in xlsx or xls.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(0);
			int noOfRows = sheet.getLastRowNum();
			int noOfCols = row.getLastCellNum();
			System.out.println("Number of rows: " + noOfRows);
			System.out.println("Number of cols: " + noOfCols);
			data = new Object[noOfRows][noOfCols];
			// Vòng lặp FOR chạy từ 1 để bỏ dòng tiêu đề (dòng tiêu đề là 0)
			for (int i = 1; i <= noOfRows; i++) {
				for (int j = 0; j < noOfCols; j++) {
					row = sheet.getRow(i);
					cell = row.getCell(j);
					if (cell != null) {
						// Này dùng để xác định kiểu dữ liệu từ ô trong excel rồi chuển về String luôn
						// cho tiện đọc
						switch (cell.getCellType()) {
						case STRING:
							data[i - 1][j] = cell.getStringCellValue();
							break;
						case NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								data[i - 1][j] = String.valueOf(cell.getDateCellValue());
							} else {
								data[i - 1][j] = String.valueOf((long) cell.getNumericCellValue());
							}
							break;
						case BOOLEAN:
							data[i - 1][j] = String.valueOf(cell.getBooleanCellValue());
							break;
						case BLANK:
							data[i - 1][j] = "";
							break;
						default:
							data[i - 1][j] = null;
							break;
						}
					} else {
						System.out.println("Cell is null, at row [" + i + "],col[" + j + "]");
						data[i - 1][j] = null;
					}
				}
			}
		} catch (Exception e) {
			e.getMessage();
			throw new RuntimeException(e);
		}
		return data;
	}

	public Object[][] getDataHashTable(String excelPath, String sheetName, int startRow, int endRow) {
		LogUtils.info("Excel File: " + excelPath);
		LogUtils.info("Sheet Name: " + sheetName);
		Object[][] data = null;
		try {
			File f = new File(excelPath);
			if (!f.exists()) {
				try {
					LogUtils.info("File Excel path not found.");
					throw new InvalidPathForExcelException("File Excel path not found.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			fileIn = new FileInputStream(f);
			// load the workbook
			String fileExtensionName = excelPath.substring(excelPath.indexOf("."));
			if (fileExtensionName.equals(".xlsx")) {
				workbook = new XSSFWorkbook(fileIn);
			} else if (fileExtensionName.equals(".xls")) {
				workbook = new HSSFWorkbook(fileIn);
			} else {
				try {
					fileIn.close();
					LogUtils.info("The excel extension is not in xlsx or xls.");
					throw new InvalidPathForExcelException("The excel extension is not in xlsx or xls.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			sheet = workbook.getSheet(sheetName);
			int rows = getRows();
			int columns = getColumns();
			LogUtils.info("Number of Rows: " + rows + " - Number of Columns: " + columns);
			LogUtils.info("StartRow: " + startRow + " - EndRow: " + endRow);
			data = new Object[(endRow - startRow) + 1][1]; // 1 cột
			Hashtable<String, String> table = null;
			for (int rowNums = startRow; rowNums <= endRow; rowNums++) {
				table = new Hashtable<>();
				for (int colNum = 0; colNum < columns; colNum++) {
					table.put(getCellData(0, colNum), getCellData(rowNums, colNum));
				}
				data[rowNums - startRow][0] = table;
			}
		} catch (IOException e) {
			e.printStackTrace();
			LogUtils.error(e.getMessage());
		}

		return data;
	}

	public String getTestCaseName(String testCaseName) {
		String value = testCaseName;
		int position = value.indexOf("@");
		value = value.substring(0, position);
		position = value.lastIndexOf(".");

		value = value.substring(position + 1);
		return value;
	}

	public int getRowContains(String sTestCaseName, int colNum) {
		int i;
		int rowCount = getRows();
		for (i = 0; i < rowCount; i++) {
			if (getCellData(i, colNum).equalsIgnoreCase(sTestCaseName)) {
				break;
			}
		}
		return i;
	}

	public int getRows() {
		try {
			return sheet.getLastRowNum();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw (e);
		}
	}

	public int getColumns() {
		try {
			row = sheet.getRow(0);
			return row.getLastCellNum();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw (e);
		}
	}

	// Get cell data
	public String getCellData(int rowNum, int colNum) {
		try {
			cell = sheet.getRow(rowNum).getCell(colNum);
			String cellData = null;
			switch (cell.getCellType()) {
			case STRING:
				cellData = cell.getStringCellValue();
				break;
			case FORMULA:
				// cellData = cell.getCellFormula();
				cellData = String.valueOf((long) cell.getNumericCellValue());
				break;
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					cellData = String.valueOf(cell.getDateCellValue());
				} else {
					cellData = String.valueOf((long) cell.getNumericCellValue());
				}
				break;
			case BOOLEAN:
				cellData = Boolean.toString(cell.getBooleanCellValue());
				break;
			default:
				cellData = "";
				break;
			}
			return cellData;
		} catch (Exception e) {
			return "";
		}
	}

	public String getCellData(int rowNum, String columnName) {
		return getCellData(rowNum, columns.get(columnName));
	}

	public String getCellData(String columnName, int rowNum) {
		return getCellData(rowNum, columns.get(columnName));
	}

	// Write data to excel sheet
	public void setCellData(String text, int rowNumber, int colNumber) {
		try {
			row = sheet.getRow(rowNumber);
			if (row == null) {
				row = sheet.createRow(rowNumber);
			}
			cell = row.getCell(colNumber);
			if (cell == null) {
				cell = row.createCell(colNumber);
			}
			cell.setCellValue(text);
			CellStyle style = workbook.createCellStyle();
			text = text.trim().toLowerCase();
			if (text == "pass" || text == "passed" || text == "success") {
				style.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
			}
			if (text == "fail" || text == "failed" || text == "failure") {
				style.setFillForegroundColor(IndexedColors.RED.getIndex());
			}
			style.setFillPattern(FillPatternType.NO_FILL);
			style.setAlignment(HorizontalAlignment.CENTER);
			style.setVerticalAlignment(VerticalAlignment.CENTER);

			cell.setCellStyle(style);

			fileOut = new FileOutputStream(excelFilePath);
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (Exception e) {
			e.getMessage();
			LogUtils.error(e.getMessage());
		}
	}

	public void setCellData(Date d, String format, int rowNumber, int colNumber) {
		try {
			row = sheet.getRow(rowNumber);
			if (row == null) {
				row = sheet.createRow(rowNumber);
			}
			cell = row.getCell(colNumber);
			if (cell == null) {
				cell = row.createCell(colNumber);
			}
			CreationHelper creationHelper = workbook.getCreationHelper();
			CellStyle style = workbook.createCellStyle();
			style.setDataFormat(creationHelper.createDataFormat().getFormat(format));
			cell.setCellValue(d);
			cell.setCellStyle(style);

			fileOut = new FileOutputStream(excelFilePath);
			workbook.write(fileOut);
			fileOut.flush();
			workbook.close();
			fileOut.close();
		} catch (Exception e) {
			e.getMessage();
			LogUtils.error(e.getMessage());
		}
	}

	public void setCellData(String text, int rowNumber, String columnName) {
		try {
			row = sheet.getRow(rowNumber);
			if (row == null) {
				row = sheet.createRow(rowNumber);
			}
			cell = row.getCell(columns.get(columnName));

			if (cell == null) {
				cell = row.createCell(columns.get(columnName));
			}
			cell.setCellValue(text);

			XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
			text = text.trim().toLowerCase();
			if (text == "pass" || text == "passed" || text == "success") {
				style.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
			}
			if (text == "fail" || text == "failed" || text == "failure") {
				style.setFillForegroundColor(IndexedColors.RED.getIndex());
			}

			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style.setAlignment(HorizontalAlignment.CENTER);
			style.setVerticalAlignment(VerticalAlignment.CENTER);

			cell.setCellStyle(style);

			fileOut = new FileOutputStream(excelFilePath);
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (Exception e) {
			e.getMessage();
			LogUtils.error(e.getMessage());
		}
	}

	private Workbook getWorkbook(String excelPath) {
		Workbook wb = null;
		try {
			File f = new File(excelPath);
			if (!f.exists()) {
				try {
					LogUtils.info("File Excel path not found.");
					throw new InvalidPathForExcelException("File Excel path not found.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			fileIn = new FileInputStream(f);
			excelFilePath = excelPath;
			wb = WorkbookFactory.create(fileIn);
		} catch (Exception e) {
			e.getMessage();
			LogUtils.error(e.getMessage());
		}
		return wb;
	}

	private Sheet getSheet(String sheetName) {
		Sheet sh = null;
		try {
			if (sheetName.isEmpty()) {
				try {
					LogUtils.info("The Sheet Name is empty.");
					throw new InvalidPathForExcelException("The Sheet Name is empty.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (workbook.getSheetIndex(sheetName) == -1) // If sheet does not exist, create new Sheet
				workbook.createSheet(sheetName);
			sh = workbook.getSheet(sheetName);
			if (sh == null) {
				try {
					LogUtils.info("Sheet name not found.");
					throw new InvalidPathForExcelException("Sheet name not found.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.getMessage();
			LogUtils.error(e.getMessage());
		}
		return sh;
	}

	private Sheet getSheet() {
		Sheet sh = null;
		try {
			sh = workbook.getSheetAt(0);
			if (sh == null) {
				try {
					LogUtils.info("Sheet can not be created.");
					throw new InvalidPathForExcelException("Sheet can not be created.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.getMessage();
			LogUtils.error(e.getMessage());
		}
		return sh;
	}

	private void addColumnHeader() {
		try {
			Row r = sheet.getRow(0);
			System.out.println("adding all the column header names to the map 'columns'");
			if (r != null) {
				r.forEach(c -> {
					String val = "";
					if (c != null) {
						switch (c.getCellType()) {
						case STRING:
							val = c.getStringCellValue();
							break;
						case NUMERIC:
							val = String.valueOf((long) c.getNumericCellValue());
							break;
						default:
							val = "";
							break;
						}
					} else {
						System.out.println("Cell is null");
						LogUtils.info("Cell is null");
						throw new InvalidPathForExcelException("Cell is null");
					}
					Integer idx = c.getColumnIndex();
					System.out.println("Cell Value : " + val + "--- Column Index : " + idx);
					columns.put(val, idx);
				});
			}
		} catch (Exception e) {
			e.getMessage();
			LogUtils.error(e.getMessage());
		}
	}

	public String getExcelFilePath() {
		return excelFilePath;
	}
}
