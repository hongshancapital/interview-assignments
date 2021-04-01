package com.sequoiacap.orm.transaction.registry;

import java.util.List;

import org.springframework.transaction.TransactionStatus;

/**
 * 分布事务注册表
 */
public interface TransactionRegistry
{
	/**
	 * 获取一个分布事务组所有事务记录的状态
	 * 
	 * @param transactionId 分布事务id
	 * @return 事务记录列表
	 */
	public List<TransactionRegistryEntry>
		getTransactionRegistryEntry(String transactionId);

	/**
	 * 写入一条分布事务记录
	 * 
	 * @param transactionId 分布事务id
	 * @param transaction 事务
	 * @param status 事务状态
	 */
	public void writeTransactionRegistryEntry(
		String transactionId, TransactionStatus transaction,
		TransactionRegistryEntry.Status status);
	
	/**
	 * 注册分布事务事件
	 * 
	 * @param transactionId　分布事务
	 * @param listener 事务监听器
	 */
	public void subscribe(
		String transactionId, TransactionRegistryListener listener);
}
