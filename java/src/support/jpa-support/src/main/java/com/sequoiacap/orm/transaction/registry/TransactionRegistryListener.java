package com.sequoiacap.orm.transaction.registry;

public interface TransactionRegistryListener
{
	/**
	 * 分布事务组回滚
	 * 
	 * @param entry 导致事务回滚的事务记录
	 */
	public void onRollback(TransactionRegistryEntry entry);

	/**
	 * 提交事务
	 */
	public void onCommit();
}
