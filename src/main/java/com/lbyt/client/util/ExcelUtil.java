package com.lbyt.client.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.record.CFRuleRecord.ComparisonOperator;
import org.apache.poi.hssf.usermodel.HSSFBorderFormatting;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFConditionalFormattingRule;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatternFormatting;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFSheetConditionalFormatting;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;

import com.lbyt.client.bean.ExcelConditionalFormattingBean;
import com.lbyt.client.bean.ExcelOutputBean;
import com.lbyt.client.constant.ExcelConstants;
import com.lbyt.client.error.ErrorBean;


/**
 * 
 * used for excel
 * 
 * @author zhenglianfu
 * 
 */
public class ExcelUtil {

	private static final int MAX_ROW_COUNT_PER_SHEET = 5000;
	
	public static final int HEAD_ROW_INDEX = 0;

	public static final int HEAD_COLUMN_INDEX = 0;

	public static List<Sheet> parseExcel(File file) throws IOException {
		FileInputStream in = new FileInputStream(file);
		HSSFWorkbook wb = new HSSFWorkbook(in);
		return bulidExcel(wb);
	}

	public static List<Sheet> parseExcel(InputStream in) throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook(in);
		return bulidExcel(wb);
	}

	public static boolean isEmptySheet(HSSFSheet sheet) {
		if (null == sheet) {
			return true;
		}
		if (sheet.getLastRowNum() == sheet.getFirstRowNum()) {
			return true;
		}
		// 标题为空， 依旧为空
		HSSFRow row = sheet.getRow(HEAD_ROW_INDEX);
		if (null == row) {
			return true;
		}
		HSSFCell cell = row.getCell(HEAD_COLUMN_INDEX);
		if (null == cell || CommUtil.isEmpty(cell.getStringCellValue())) {
			return true;
		}
		return false;
	}

	public static List<Sheet> bulidExcel(HSSFWorkbook wb) {
		List<Sheet> list = new ArrayList<Sheet>();
		int maxCount = 0, count = 0, lastRowNum = 0, i = 0;
		HSSFSheet sheet = null;
		Cell[] values = null;
		HSSFRow row = null;
		Sheet mySheet = null;
		if (wb != null) {
			maxCount = wb.getNumberOfSheets();
			while (count < maxCount) {
				sheet = wb.getSheetAt(count);
				count++;
				if (isEmptySheet(sheet)) {
					continue;
				}
				mySheet = new Sheet();
				lastRowNum = sheet.getLastRowNum();
				int titleLength = sheet.getRow(0).getLastCellNum();
				for (i = 0; i < lastRowNum; i++) {
					row = sheet.getRow(i);
					if (row == null) {
						continue;
					}
					int columnNum = row.getLastCellNum(), j = 0;
					values = new Cell[titleLength];
					columnNum = Math.min(columnNum, titleLength);
					for (; j < columnNum; j++) {
						HSSFCell cell = row.getCell(j);
						if (null == cell) {
							continue;
						}
						int cellType = cell.getCellType();
						switch (cellType) {
						case HSSFCell.CELL_TYPE_STRING:
							values[j] = new Cell(cell.getStringCellValue(),
									Cell.CELL_STRING);
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								Date date = cell.getDateCellValue();
								if (date != null) {
									values[j] = new Cell(DateUtil.date2String(date), Cell.CELL_STRING);
								} else {
									values[j] = new Cell("", Cell.CELL_NULL);
								}
							} else {
								values[j] = new Cell(""
										+ (long) cell.getNumericCellValue(),
										Cell.CELL_NUMERIC);
							}
							break;
						default:
							values[j] = new Cell(null, Cell.CELL_NULL);
							break;
						}
					}
					mySheet.addRow(values);
				}
				list.add(mySheet);
			}
		}
		return list;
	}

	public static class Sheet {
		private List<Cell[]> list = new ArrayList<Cell[]>();

		private int lastRowNum = 0;

		public void addRow(Cell[] row) {
			list.add(row);
			lastRowNum++;
		}

		public List<Cell[]> getRows() {
			return this.list;
		}

		public Cell[] getRow(int i) {
			i = i < 0 ? (i + this.lastRowNum) : i;
			if (i < this.lastRowNum) {
				return this.list.get(i);
			}
			return null;
		}

		public int getLastNumber() {
			return this.lastRowNum;
		}
	}

	public static class Cell {
		public static final int CELL_STRING = 1;

		public static final int CELL_NUMERIC = 2;

		public static final int CELL_NULL = -1;

		private String vlaue;

		private int type;

		public Cell(String value, int type) {
			this.vlaue = value;
			this.type = type;
		}

		public String getValue() {
			return this.vlaue;
		}

		public int getType() {
			return this.type;
		}
	}

	public static void buildExcelFile(ExcelOutputBean bean,
			List<ErrorBean> errors, ServletOutputStream outputStream) throws IOException {
		// 创建Excel工作簿
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFCellStyle spreadHeadStyle = getSpreadHeadStyle(wb);
		HSSFCellStyle dataStyle = getDataStyle(wb);
		Iterator<List<Object>> iterator = bean.getCells().iterator();
		int pageCount = ((bean.getCells().size() - 1) / MAX_ROW_COUNT_PER_SHEET) + 1;
		for (int i = 0; i < pageCount; i++) {
			 int row = 0;
             //新建工作表
             HSSFSheet sheet = wb.createSheet("第" + (i + 1) + "页");
             sheet.setDisplayGridlines(true);
             HSSFRow spreadHeadRow = sheet.createRow(row);
             int mergStart = 0;
             String lastSpreadHead = null;
             int col;
             for (col = 0; col < bean.getSpreadHeads().size(); col++) {
                 String value = bean.getSpreadHeads().get(col);
                 if (null != value) {
                     HSSFCell headCell = spreadHeadRow.createCell(col);
                     headCell.setCellStyle(spreadHeadStyle);
                     if (!value.equals(lastSpreadHead)) {
                         if (mergStart < col - 1) {
                             sheet.addMergedRegion(new CellRangeAddress(row, row, mergStart, col - 1));
                         }
                         mergStart = col;
                         headCell.setCellValue(value);
                         setupConditionalFormattingRules(sheet, ExcelConstants.READ_ONLY, row, col, value);
                     }
                 }
                 lastSpreadHead = value;
             }
             if (mergStart < col - 1) {
                 sheet.addMergedRegion(new CellRangeAddress(row, row, mergStart, col - 1));
             }
             //数据,
             for (int r = 0; r < MAX_ROW_COUNT_PER_SHEET; r++) {
                 row++;
                 if (iterator.hasNext()) {
                     HSSFRow dataRow = sheet.createRow(row);
                     List<Object> list = iterator.next();
                     for (int c = 0; c < bean.getHeads().size(); c++) {
                         Object obj = list.size() > c ? list.get(c) : null;
                         //条件格式
                         int condition = 0;
                         if (bean.getColumnConditions().size() > c) {
                             condition = bean.getColumnConditions().get(c);
                         }
                         if (null == obj) {
                             HSSFCell cell = dataRow.createCell(c);
                             cell.setCellStyle(dataStyle);
                             if (0 != condition) {
                                 setupConditionalFormattingRules(sheet, condition, row, c, null);
                             }
                             continue;
                         }
                         if (obj instanceof ExcelConditionalFormattingBean) {
                             ExcelConditionalFormattingBean conditionalFormattingBean = (ExcelConditionalFormattingBean) obj;
                             condition |= conditionalFormattingBean.getCondition();
                             obj = conditionalFormattingBean.getValue();
                         }
                         HSSFCell cell = dataRow.createCell(c);
                         cell.setCellStyle(dataStyle);
                         setupConditionalFormattingRules(sheet, condition, row, c, String.valueOf(obj));
                         if (obj instanceof String) {
                             cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
                             cell.setCellValue((String) obj);
                         } else if (obj instanceof Date) {
                             cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
                             cell.setCellValue((Date) obj);
                         } else if (obj instanceof Calendar) {
                             cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
                             cell.setCellValue((Calendar) obj);
                         } else if (obj instanceof Number) {
                             cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC);
                             cell.setCellValue(((Number) obj).doubleValue());
                         } else if (obj instanceof Boolean) {
                             cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN);
                             cell.setCellValue(((Boolean) obj).booleanValue());
                         } else {
                             cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
                             cell.setCellValue(String.valueOf(obj));
                         }
                     }
                 } else {
                     break;
                 }
             }
             //空行
             row++;
             //错误或警告
             for (ErrorBean error : errors) {
                 row++;
                 HSSFRow errorRow = sheet.createRow(row);
                 HSSFCell errorCell = errorRow.createCell(0);
                 errorCell.setCellValue(error.getMessage());
                 if (!bean.getHeads().isEmpty()) {
                     sheet.addMergedRegion(new CellRangeAddress(row, row, 0, bean.getHeads().size() - 1));
                 }
             }
             if (0 < bean.getFreezeCol() && 0 < bean.getFreezeRow()) {
                 sheet.createFreezePane(bean.getFreezeCol(), bean.getFreezeRow());
             }
             wb.write(outputStream);
		 }
	}
	
	private static void setupConditionalFormattingRules(HSSFSheet sheet, int condition, int row, int col, String value) {
        HSSFSheetConditionalFormatting formatting = sheet.getSheetConditionalFormatting();
        if ((ExcelConstants.READ_ONLY & condition) > 0) {
            List<HSSFConditionalFormattingRule> rules = new ArrayList<>();
            if (null != value && 0 < value.length() && !".".equals(value) && value.matches("^\\d*\\.?\\d*$")) {
                HSSFConditionalFormattingRule rule = formatting.createConditionalFormattingRule(ComparisonOperator.EQUAL, value, null);
                normalFormatting(rule);
                rules.add(rule);
            }
            HSSFConditionalFormattingRule rule = formatting.createConditionalFormattingRule(ComparisonOperator.NOT_EQUAL,
                    getReadonlyStringRule(value), null);
            invalidFormatting(rule);
            rules.add(rule);
            format(formatting, row, col, rules.toArray(new HSSFConditionalFormattingRule[rules.size()]));
        } else if ((ExcelConstants.NUMERIC_ONLY & condition) > 0) {
            HSSFConditionalFormattingRule numberRule = formatting.createConditionalFormattingRule(getNumberRule(col, row));
            normalFormatting(numberRule);
            HSSFConditionalFormattingRule notEmptyRule = formatting.createConditionalFormattingRule(getEmptyRule(col, row));
            invalidFormatting(notEmptyRule);
            format(formatting, row, col, numberRule, notEmptyRule);
        }
    }
	
	private static void format(HSSFSheetConditionalFormatting formatting, int row, int col, HSSFConditionalFormattingRule... rules) {
        CellRangeAddress[] regions = { new CellRangeAddress(row, row, col, col) };
        formatting.addConditionalFormatting(regions, rules);
    }

    private static void invalidFormatting(HSSFConditionalFormattingRule rule) {
        HSSFPatternFormatting pattern = rule.createPatternFormatting();
        pattern.setFillBackgroundColor(HSSFColor.RED.index);
        HSSFBorderFormatting border = rule.createBorderFormatting();
        border.setBorderLeft(CellStyle.BORDER_THIN);
        border.setBorderRight(CellStyle.BORDER_THIN);
        border.setBorderTop(CellStyle.BORDER_THIN);
        border.setBorderBottom(CellStyle.BORDER_THIN);
    }

    private static void normalFormatting(HSSFConditionalFormattingRule rule) {
        HSSFBorderFormatting border = rule.createBorderFormatting();
        border.setBorderLeft(CellStyle.BORDER_THIN);
        border.setBorderRight(CellStyle.BORDER_THIN);
        border.setBorderTop(CellStyle.BORDER_THIN);
        border.setBorderBottom(CellStyle.BORDER_THIN);
    }

    private static String getEmptyRule(int col, int row) {
        String address = CellReference.convertNumToColString(col) + (row + 1);
        return "NOT(" + address + "=\"\")";
    }

    private static String getNumberRule(int col, int row) {
        String address = CellReference.convertNumToColString(col) + (row + 1);
        return "ISNUMBER(" + address + ")";
    }

    private static String getReadonlyStringRule(String value) {
        if (null != value) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return "\"\"";
    }

	private static HSSFCellStyle getTitleStyle(HSSFWorkbook wb) {
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = wb.createFont();
		font.setFontName(style.getFont(wb).getFontName());
		font.setFontHeightInPoints((short) 16);
		font.setUnderline(Font.U_SINGLE);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.BLACK.index);
		style.setFont(font);
		style.setWrapText(true);
		return style;
	}

	private static HSSFCellStyle getSpreadHeadStyle(HSSFWorkbook wb) {
		HSSFCellStyle style = wb.createCellStyle();
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.ROYAL_BLUE.index);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = wb.createFont();
		font.setFontName(style.getFont(wb).getFontName());
		font.setFontHeightInPoints((short) 10);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.WHITE.index);
		style.setFont(font);
		style.setWrapText(true);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		return style;
	}

	private static HSSFCellStyle getHeadStyle(HSSFWorkbook wb) {
		HSSFCellStyle style = wb.createCellStyle();
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.ROYAL_BLUE.index);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = wb.createFont();
		font.setFontName(style.getFont(wb).getFontName());
		font.setFontHeightInPoints((short) 10);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.WHITE.index);
		style.setFont(font);
		style.setWrapText(true);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		return style;
	}

	private static HSSFCellStyle getDataStyle(HSSFWorkbook wb) {
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = wb.createFont();
		font.setFontName(style.getFont(wb).getFontName());
		font.setFontHeightInPoints((short) 10);
		font.setColor(HSSFColor.BLACK.index);
		style.setFont(font);
		style.setWrapText(true);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		return style;
	}

}
