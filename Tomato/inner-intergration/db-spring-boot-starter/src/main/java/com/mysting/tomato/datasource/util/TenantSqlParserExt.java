package com.mysting.tomato.datasource.util;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import com.mysting.tomato.datasource.constant.Global;

import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.SelectBody;

/**
 * 多租户sql解析器扩展
 */
public class TenantSqlParserExt extends TenantSqlParser {

	/**
	 * 是否处理插入的sql
	 */
	private boolean isHandlerInsert = true;

	/**
	 * 是否处理查询的sql
	 */
	private boolean isHandlerSelect = true;

	@Override
	public void processInsert(Insert insert) {
		if (isHandlerInsert) {
			int index = findTenantIdColumnIndex(insert.getColumns());
			// 若不存在租户ID则进行添加租户ID信息
			if (-1 == index) {
				super.processInsert(insert);
			}
		}
	}

	/**
	 * 获取Column集合中租户ID的位置，若没有返回-1
	 * 
	 * @param columns
	 * @return
	 */
	public int findTenantIdColumnIndex(List<Column> columns) {
		Column column;
		for (int i = 0; i < columns.size(); i++) {
			column = columns.get(i);
			if (Global.TENANT_ID.equals(column.getColumnName())) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void processSelectBody(SelectBody selectBody) {
		if (isHandlerSelect) {
			super.processSelectBody(selectBody);
		}
	}

	public boolean isHandlerInsert() {
		return isHandlerInsert;
	}

	public void setHandlerInsert(boolean handlerInsert) {
		isHandlerInsert = handlerInsert;
	}

	public boolean isHandlerSelect() {
		return isHandlerSelect;
	}

	public void setHandlerSelect(boolean handlerSelect) {
		isHandlerSelect = handlerSelect;
	}
}
