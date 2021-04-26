package com.xcf.mybatisplus.model.Vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author xcf
 * @Date 创建时间：2021年4月26日 上午9:10:37
 */
public class PageAdapter implements Serializable {
	private static final long serialVersionUID = -1365136938206941207L;

	public static final int MAX_PAGE_SIZE = 10000;

	public static final int DEFAULT_PAGE_SIZE = 10;

	public static final int DEFAULT_PAGE_NUM = 1;

	public static final int MAX_LIMIT_SIZE = 2000;

	/**
	 * 页码
	 */
	// @ApiModelProperty(value = "页码[默认:1,起始页码:1]", example = "1")
	private Integer pageNum = 1;

	/**
	 * 每页条数
	 */
	// @ApiModelProperty(value = "每页条数[默认:10]", example = "10")
	private Integer pageSize = 10;

	/**
	 * 排序规则
	 */
	// @ApiModelProperty(value = "排序规则", notes = "见示例[名称倒序]",hidden = true, example
	// = "[{\"column\":true,\"asc\":false}]")
	private List<OrderItem> orderList;

	@JsonIgnore
	public <T> Page<T> buildMBPPage() {
		Page<T> page = new Page<>();
		page.setSize(Optional.ofNullable(this.pageSize).orElse(DEFAULT_PAGE_SIZE));
		page.setCurrent(Optional.ofNullable(this.pageNum).orElse(DEFAULT_PAGE_NUM));
		if (!orderList.isEmpty()) {
			page.setOrders(orderList);
		}
		return page;
	}

	/**
	 * SQL分页时可用这个参数从Service传回Controller
	 */
	@JsonIgnore
	private transient Long total;

	public void setPageNum(Integer pageNum) {
		if (Objects.isNull(pageNum) || pageNum < 1) {
			this.pageNum = 1;
		} else {
			this.pageNum = pageNum;
		}
	}

	public void setPageSize(Integer pageSize) throws Exception {

		if (Objects.isNull(pageSize) || pageSize < 1) {
			this.pageSize = 10;
		} else {
			this.pageSize = pageSize;
		}

		if (this.pageSize > MAX_PAGE_SIZE) {
			// throw new BusinessException(String.format("pageSize过大[1<%s<%s]", pageSize,
			// MAX_PAGE_SIZE));
			throw new Exception(String.format("pageSize过大[1<%s<%s]", pageSize, MAX_PAGE_SIZE));
		}
	}

	@JsonIgnore
	public Long getLimit() {

		if (Objects.isNull(pageNum) || Objects.isNull(pageSize)) {
			return null;
		}

		return (this.pageNum - 1L) * pageSize;
	}

	public PageAdapter() {

		this.orderList = new ArrayList<>();
	}
}
