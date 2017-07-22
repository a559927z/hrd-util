package net.chinahrd.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

/**
 * 解析excel相关的各个方法 Created by wqcai on 15/6/26.
 */
public class ExcelUtil {

	/**
	 * 文件操作 获取文件扩展名
	 *
	 * @param filename
	 *            文件名称包含扩展名
	 * @return
	 * @Author: sunny
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	/**
	 * 读取97-2003格式
	 *
	 * @param file
	 *            文件
	 * @throws java.io.IOException
	 */
	public static List<Map<String, Object>> readExcel2003(MultipartFile file, String rex) throws IOException {
		// 返回结果集
		List<Map<String, Object>> valueList = CollectionKit.newList();
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(file.getInputStream());
			POIFSFileSystem fs = new POIFSFileSystem(in);
			HSSFWorkbook wookbook = new HSSFWorkbook(fs); // 创建对Excel工作簿文件的引用
			for (int sheetIndex = 0; sheetIndex < wookbook.getNumberOfSheets(); sheetIndex++) {
				HSSFSheet sheet = wookbook.getSheetAt(sheetIndex);
				int rows = sheet.getPhysicalNumberOfRows(); // 获取到Excel文件中的所有行数
				Map<Integer, String> keys = CollectionKit.newMap();
				String titleCheck = "";
				int cells = 0;
				// 遍历行­（第1行 表头） 准备Map里的key
				HSSFRow firstRow = sheet.getRow(0);
				if (firstRow != null) {
					// 获取到Excel文件中的所有的列
					cells = firstRow.getPhysicalNumberOfCells();
					// 遍历列
					for (int j = 0; j < cells; j++) {
						// 获取到列的值­
						try {
							HSSFCell cell = firstRow.getCell(j);
							String cellValue = getCellValue(cell);
							// by jxzhang
							titleCheck += cellValue + ",";
							keys.put(j, cellValue);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if (!rex.isEmpty()) {
						if (!titleCheck.contains(rex)) {
							// if(!rex.equals(titleCheck)){
							break;
						}
					}

				}
				// 遍历行­（从第二行开始）
				for (int i = 1; i < rows; i++) {
					// 读取左上端单元格(从第二行开始)
					HSSFRow row = sheet.getRow(i);
					// 行不为空
					if (row != null) {
						// 准备当前行 所储存值的map
						Map<String, Object> val = CollectionKit.newMap();

						boolean isValidRow = false;

						// 遍历列
						for (int j = 0; j < cells; j++) {
							// 获取到列的值­
							try {
								HSSFCell cell = row.getCell(j);
								String cellValue = getCellValue(cell);
								val.put(keys.get(j), cellValue);
								if (!isValidRow && cellValue != null && cellValue.trim().length() > 0) {
									isValidRow = true;
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						// 第I行所有的列数据读取完毕，放入valuelist
						if (isValidRow) {
							valueList.add(val);
						}
					}
				}
			}
		} catch (IOException e) {
			// e.printStackTrace();
			return CollectionKit.newList();
		} finally {
			in.close();
		}
		return valueList;
	}

	/**
	 * 读取2007-2013格式
	 *
	 * @param file
	 *            文件
	 * @return
	 * @throws java.io.IOException
	 */
	public static List<Map<String, Object>> readExcel2007(MultipartFile file, String rex) throws IOException {
		// 返回结果集
		List<Map<String, Object>> valueList = CollectionKit.newList();
		try {
			XSSFWorkbook xwb = new XSSFWorkbook(file.getInputStream()); // 构造
																		// XSSFWorkbook
																		// 对象，strPath
																		// 传入文件路径
			for (int sheetIndex = 0; sheetIndex < xwb.getNumberOfSheets(); sheetIndex++) {
				XSSFSheet sheet = xwb.getSheetAt(sheetIndex);
				// 定义 row、cell
				XSSFRow row;
				// 循环输出表格中的第一行内容 表头
				Map<Integer, String> keys = CollectionKit.newMap();
				String titleCheck = "";
				row = sheet.getRow(0);
				if (row != null) {
					// System.out.println("j =
					// row.getFirstCellNum()::"+row.getFirstCellNum());
					// System.out.println("row.getPhysicalNumberOfCells()::"+row.getPhysicalNumberOfCells());
					for (int j = row.getFirstCellNum(); j <= row.getPhysicalNumberOfCells(); j++) {
						// 通过 row.getCell(j).toString() 获取单元格内容，
						if (row.getCell(j) != null) {
							if (!row.getCell(j).toString().isEmpty()) {

								// by jxzhang
								titleCheck += row.getCell(j).toString() + ",";

								keys.put(j, row.getCell(j).toString());
							}
						} else {
							keys.put(j, "K-R1C" + j + "E");
						}
					}

					if (!rex.isEmpty()) {
						if (!rex.equals(titleCheck)) {
							break;
						}
					}
				}
				// 循环输出表格中的从第二行开始内容
				for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getPhysicalNumberOfRows(); i++) {
					row = sheet.getRow(i);
					if (row != null) {
						boolean isValidRow = false;
						Map<String, Object> val = CollectionKit.newMap();
						for (int j = row.getFirstCellNum(); j <= row.getPhysicalNumberOfCells(); j++) {
							XSSFCell cell = row.getCell(j);
							if (cell != null) {
								String cellValue = null;
								if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
									if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
										cellValue = new DataFormatter().formatRawCellContents(
												cell.getNumericCellValue(), 0, "yyyy-MM-dd HH:mm:ss");
									} else {
										cellValue = String.valueOf(cell.getNumericCellValue());
									}
								} else {
									cellValue = cell.toString();
								}
								if (cellValue != null && cellValue.trim().length() <= 0) {
									cellValue = null;
								}
								val.put(keys.get(j), cellValue);
								if (!isValidRow && cellValue != null && cellValue.trim().length() > 0) {
									isValidRow = true;
								}
							}
						}
						// 第I行所有的列数据读取完毕，放入valuelist
						if (isValidRow) {
							valueList.add(val);
						}
					}
				}
			}

		} catch (IOException e) {
			// e.printStackTrace();
			return CollectionKit.newList();
		}
		return valueList;
	}

	private static String getCellValue(HSSFCell cell) {
		DecimalFormat df = new DecimalFormat("#");
		String cellValue = null;
		if (cell == null)
			return null;
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				cellValue = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
				break;
			}
			cellValue = df.format(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_STRING:
			cellValue = String.valueOf(cell.getStringCellValue());
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			cellValue = String.valueOf(cell.getCellFormula());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			cellValue = null;
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			cellValue = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			cellValue = String.valueOf(cell.getErrorCellValue());
			break;
		}
		if (cellValue != null && cellValue.trim().length() <= 0) {
			cellValue = null;
		}
		return cellValue;
	}

	/**
	 * 读取97-2003格式
	 * 
	 * @param filePath
	 *            文件路径
	 * @throws java.io.IOException
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map> readExcel2003(String filePath) throws IOException {
		// 返回结果集
		List<Map> valueList = new ArrayList<Map>();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
			HSSFWorkbook wookbook = new HSSFWorkbook(fis); // 创建对Excel工作簿文件的引用
			HSSFSheet sheet = wookbook.getSheetAt(0); // 在Excel文档中，第一张工作表的缺省索引是0
			int rows = sheet.getPhysicalNumberOfRows(); // 获取到Excel文件中的所有行数­
			Map<Integer, String> keys = new HashMap<Integer, String>();
			int cells = 0;
			// 遍历行­（第1行 表头） 准备Map里的key
			HSSFRow firstRow = sheet.getRow(0);
			if (firstRow != null) {
				// 获取到Excel文件中的所有的列
				cells = firstRow.getPhysicalNumberOfCells();
				// 遍历列
				for (int j = 0; j < cells; j++) {
					// 获取到列的值­
					try {
						HSSFCell cell = firstRow.getCell(j);
						String cellValue = getCellValue(cell);
						keys.put(j, cellValue);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			// 遍历行­（从第二行开始）
			for (int i = 1; i < rows; i++) {
				// 读取左上端单元格(从第二行开始)
				HSSFRow row = sheet.getRow(i);
				// 行不为空
				if (row != null) {
					// 准备当前行 所储存值的map
					Map<String, Object> val = new HashMap<String, Object>();

					boolean isValidRow = false;

					// 遍历列
					for (int j = 0; j < cells; j++) {
						// 获取到列的值­
						try {
							HSSFCell cell = row.getCell(j);
							String cellValue = getCellValue(cell);
							val.put(keys.get(j), cellValue);
							if (!isValidRow && cellValue != null && cellValue.trim().length() > 0) {
								isValidRow = true;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					// 第I行所有的列数据读取完毕，放入valuelist
					if (isValidRow) {
						valueList.add(val);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fis.close();
		}
		return valueList;
	}

	/**
	 * 读取2007-2013格式
	 * 
	 * @param filePath
	 *            文件路径
	 * @return
	 * @throws java.io.IOException
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map> readExcel2007(String filePath) throws IOException {
		List<Map> valueList = new ArrayList<Map>();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
			XSSFWorkbook xwb = new XSSFWorkbook(fis); // 构造 XSSFWorkbook
														// 对象，strPath 传入文件路径
			XSSFSheet sheet = xwb.getSheetAt(0); // 读取第一章表格内容
			// 定义 row、cell
			XSSFRow row;
			// 循环输出表格中的第一行内容 表头
			Map<Integer, String> keys = new HashMap<Integer, String>();
			row = sheet.getRow(0);
			if (row != null) {
				// System.out.println("j =
				// row.getFirstCellNum()::"+row.getFirstCellNum());
				// System.out.println("row.getPhysicalNumberOfCells()::"+row.getPhysicalNumberOfCells());
				for (int j = row.getFirstCellNum(); j <= row.getPhysicalNumberOfCells(); j++) {
					// 通过 row.getCell(j).toString() 获取单元格内容，
					if (row.getCell(j) != null) {
						if (!row.getCell(j).toString().isEmpty()) {
							keys.put(j, row.getCell(j).toString());
						}
					} else {
						keys.put(j, "K-R1C" + j + "E");
					}
				}
			}
			// 循环输出表格中的从第二行开始内容
			for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				if (row != null) {
					boolean isValidRow = false;
					Map<String, Object> val = new HashMap<String, Object>();
					for (int j = row.getFirstCellNum(); j <= row.getPhysicalNumberOfCells(); j++) {
						XSSFCell cell = row.getCell(j);
						if (cell != null) {
							String cellValue = null;
							if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
									cellValue = new DataFormatter().formatRawCellContents(cell.getNumericCellValue(), 0,
											"yyyy-MM-dd HH:mm:ss");
								} else {
									cellValue = String.valueOf(cell.getNumericCellValue());
								}
							} else {
								cellValue = cell.toString();
							}
							if (cellValue != null && cellValue.trim().length() <= 0) {
								cellValue = null;
							}
							val.put(keys.get(j), cellValue);
							if (!isValidRow && cellValue != null && cellValue.trim().length() > 0) {
								isValidRow = true;
							}
						}
					}

					// 第I行所有的列数据读取完毕，放入valuelist
					if (isValidRow) {
						valueList.add(val);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fis.close();
		}

		return valueList;
	}
}
