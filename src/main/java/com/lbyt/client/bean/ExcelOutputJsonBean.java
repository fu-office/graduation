package com.lbyt.client.bean;

import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 类名: ExcelOutputJsonBean
 * 描述: TODO
 * 
 * @author murphy
 */
public class ExcelOutputJsonBean extends JsonBean {

	private static final long serialVersionUID = -3159751185695028342L;
	
	private transient ExcelOutputBean bean     = new ExcelOutputBean();

    @JsonIgnore
    public List<String> getHeads() {
        return bean.getHeads();
    }

    @JsonIgnore
    public void setHeads(String... strings) {
        bean.getHeads().clear();
        bean.getHeads().addAll(Arrays.asList(strings));
    }

    @JsonIgnore
    public List<List<Object>> getCells() {
        return bean.getCells();
    }

    @JsonIgnore
    public String getTitle() {
        return bean.getTitle();
    }

    @JsonIgnore
    public void setTitle(String title) {
        bean.setTitle(title);
    }

    @JsonIgnore
    public List<String> getSpreadHeads() {
        return bean.getSpreadHeads();
    }

    @JsonIgnore
    public void setSpreadHeads(String... strings) {
        bean.getSpreadHeads().clear();
        bean.getSpreadHeads().addAll(Arrays.asList(strings));
    }

    /**
     * @return 返回变量freezeCol的值
     */
    @JsonIgnore
    public int getFreezeCol() {
        return bean.getFreezeCol();
    }

    /**
     * @param freezeCol
     *            设置freezeCol的值
     */
    @JsonIgnore
    public void setFreezeCol(int freezeCol) {
        bean.setFreezeCol(freezeCol);
    }

    /**
     * @return 返回变量freezeRow的值
     */
    @JsonIgnore
    public int getFreezeRow() {
        return bean.getFreezeRow();
    }

    /**
     * @param freezeRow
     *            设置freezeRow的值
     */
    @JsonIgnore
    public void setFreezeRow(int freezeRow) {
        bean.setFreezeRow(freezeRow);
    }

    /**
     * @return 返回变量columnConditions的值
     */
    @JsonIgnore
    public List<Integer> getColumnConditions() {
        return bean.getColumnConditions();
    }

    public ExcelOutputBean getBean() {
        return bean;
    }
}
