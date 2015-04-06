package com.lbyt.client.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExcelOutputBean{

	private String                   title;

    private int                      freezeCol        = -1;

    private int                      freezeRow        = -1;

    private final List<String>       spreadHeads      = new ArrayList<>();

    private final List<String>       heads            = new ArrayList<>();

    private final List<List<Object>> cells            = new ArrayList<>();

    private final List<Integer>      columnConditions = new ArrayList<>();

    public List<String> getHeads() {
        return heads;
    }

    public void setHeads(String... strings) {
        heads.clear();
        heads.addAll(Arrays.asList(strings));
    }

    public List<List<Object>> getCells() {
        return cells;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getSpreadHeads() {
        return spreadHeads;
    }

    public void setSpreadHeads(String... strings) {
        spreadHeads.clear();
        spreadHeads.addAll(Arrays.asList(strings));
    }

    /**
     * @return 返回变量freezeCol的值
     */
    public int getFreezeCol() {
        return freezeCol;
    }

    /**
     * @param freezeCol
     *            设置freezeCol的值
     */
    public void setFreezeCol(int freezeCol) {
        this.freezeCol = freezeCol;
    }

    /**
     * @return 返回变量freezeRow的值
     */
    public int getFreezeRow() {
        return freezeRow;
    }

    /**
     * @param freezeRow
     *            设置freezeRow的值
     */
    public void setFreezeRow(int freezeRow) {
        this.freezeRow = freezeRow;
    }

    /**
     * @return 返回变量columnConditions的值
     */
    public List<Integer> getColumnConditions() {
        return columnConditions;
    }
	
}	
