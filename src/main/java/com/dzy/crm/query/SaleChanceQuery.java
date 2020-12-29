package com.dzy.crm.query;

import com.dzy.crm.base.BaseQuery;
import lombok.Data;

@Data
public class SaleChanceQuery extends BaseQuery {
    // 客户名称
    private String customerName;
    // 创建人
    private String createMan;
    // 分配状态
    private String state;
}
