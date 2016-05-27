package com.thinkcoo.mobile.model.entity.serverresponse;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	protected static final long serialVersionUID = 1829989122820583427L;
	private static final int RESPONSE_OK =1 ;
	/**
	 * 响应状态码
	 */
	private int status;
	/**
	 * 异常提示信息
	 */
	private String msg;
	/**
	 * 数据
	 */
	private T data;
	
	/**
	 * 页面对象
	 */
	private PageBean page;
	
	public PageBean getPage() {
		return page;
	}
	public void setPage(PageBean page) {
		this.page = page;
	}
	

	/**
	 * 
	 * Description: 数据
	 * @return
	 */
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public boolean isSuccess(){
		return  status == RESPONSE_OK ;
	}
}