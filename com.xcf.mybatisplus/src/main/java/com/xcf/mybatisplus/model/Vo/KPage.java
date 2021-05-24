package com.xcf.mybatisplus.model.Vo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.var;

/** 
* @author xcf 
* @Date 创建时间：2021年5月24日 下午4:07:56 
* 公共分页
*/
@Data
public class KPage<T> implements Serializable {
	private static final long serialVersionUID = -4375434118780684660L;
    /**
     * 数据列表
     */
    @ApiModelProperty("数据列表")
    private Collection<T> records;

    /**
     * 总数
     */
    @ApiModelProperty("总数")
    private Long total;

    /**
     * 每页显示条数，默认 10
     */
    @ApiModelProperty("每页显示条数，默认 10")
    private Long size;

    /**
     * 当前页
     */
    @ApiModelProperty("当前页")
    private Long current;


    public static <T> KPage<T> buildFromMBPPage(Page<T> mbpPage) {
        var page = new KPage<T>();
        page.setRecords(mbpPage.getRecords());
        page.setSize(mbpPage.getSize());
        page.setCurrent(mbpPage.getCurrent());
        page.setTotal(mbpPage.getTotal());
        return page;
    }
    public static <T> KPage<T> buildFromMBPIPage(IPage<T> mbpPage) {
        var page = new KPage<T>();
        page.setRecords(mbpPage.getRecords());
        page.setSize(mbpPage.getSize());
        page.setCurrent(mbpPage.getCurrent());
        page.setTotal(mbpPage.getTotal());
        return page;
    }
    public static <T, K> KPage<K> buildFromMBPPageAndMap(Page<T> mbpPage, Function<T, K> mapper) {
        var page = new KPage<K>();
        List<K> list = mbpPage.getRecords().stream().map(mapper).collect(Collectors.toList());
        page.setRecords(list);
        page.setSize(mbpPage.getSize());
        page.setCurrent(mbpPage.getCurrent());
        page.setTotal(mbpPage.getTotal());
        return page;
    }

    public static <T, K> KPage<K> buildFromMBPIPageAndMap(IPage<T> mbpPage, Function<T, K> mapper) {
        var page = new KPage<K>();
        List<K> list = mbpPage.getRecords().stream().map(mapper).collect(Collectors.toList());
        page.setRecords(list);
        page.setSize(mbpPage.getSize());
        page.setCurrent(mbpPage.getCurrent());
        page.setTotal(mbpPage.getTotal());
        return page;
    }

}
