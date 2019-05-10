package com.sse.easyexcel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-05-10 14:41
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReadModel extends BaseRowModel {

    @ExcelProperty(index = 0, format = "yyyy-MM-dd")
    private Date tradeDate;

    @ExcelProperty(index = 1)
    private String code;

    @ExcelProperty(index = 2)
    private String indexNameCn;

    @ExcelProperty(index = 4)
    private String indexName;

    @ExcelProperty(index = 9)
    private BigDecimal close;
}
