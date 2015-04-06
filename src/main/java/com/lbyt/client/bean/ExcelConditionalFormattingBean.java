package com.lbyt.client.bean;

public class ExcelConditionalFormattingBean {
	
    private Object value;

    private int    condition;

    /**
     * @return 返回变量value的值
     */
    public Object getValue() {
        return value;
    }

    /**
     * 设置属性value的值.
     * 
     * @param value
     *            value的值
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * @return 返回变量condition的值
     */
    public int getCondition() {
        return condition;
    }

    /**
     * 设置属性condition的值.
     * 
     * @param condition
     *            condition的值
     */
    public void setCondition(int condition) {
        this.condition = condition;
    }
}
