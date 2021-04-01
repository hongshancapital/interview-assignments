package com.sequoiacap.orm.transaction.registry.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import com.alibaba.dubbo.common.utils.NetUtils;
import com.sequoiacap.orm.transaction.registry.TransactionRegistry;
import com.sequoiacap.orm.transaction.registry.TransactionRegistryEntry;
import com.sequoiacap.orm.transaction.registry.TransactionRegistryEntry.Status;
import com.sequoiacap.orm.transaction.registry.TransactionRegistryListener;

@Service("redisTransactionRegistry")
public class RedisTransactionRegistry
	implements TransactionRegistry
{
	@Resource(name = "redisTemplate")
	private HashOperations<String, String, TransactionRegistryEntry.Status> hashOpt;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private RedisMessageListenerContainer redisListener;

	private String localHost = NetUtils.getLocalHost();
	
	private String toEntryName(TransactionStatus transaction)
	{
		return localHost + ":" + transaction.hashCode();
	}

	@Override
	public List<TransactionRegistryEntry> getTransactionRegistryEntry(String transactionId)
	{
		Set<String> names = hashOpt.keys(transactionId);

		ArrayList<TransactionRegistryEntry> entries = new
			ArrayList<TransactionRegistryEntry>(names.size());

		for(String name: names)
		{
			Status status = hashOpt.get(transactionId, name);

			TransactionRegistryEntry entry = new TransactionRegistryEntry();

			entry.setEntryName(name);
			entry.setStatus(status);

			entries.add(entry);
		}
		
		return entries;
	}

	@Override
	public void writeTransactionRegistryEntry(
		String transactionId, TransactionStatus transaction, Status status)
	{
		TransactionRegistryEntry entry = new TransactionRegistryEntry();

		entry.setEntryName(toEntryName(transaction));
		entry.setStatus(status);

		hashOpt.put(transactionId, entry.getEntryName(), status);

		redisTemplate.convertAndSend(transactionId + "-event", entry);
		
		if (status == TransactionRegistryEntry.Status.rollback ||
			status == TransactionRegistryEntry.Status.commit)
		{
			if (inAccordWith(transactionId, status))
			{//如果所有事务都为提交或回滚，则可以删除分布事务组
				redisTemplate.delete(transactionId);
			}
		}
	}

	@Override
	public void subscribe(final String transactionId,
		final TransactionRegistryListener listener)
	{
		final MessageListener msgListener = new MessageListener() {
			@Override
			public void onMessage(Message message, byte[] pattern)
			{
				boolean removeListener = false;
				
				TransactionRegistryEntry entry = null;
				
				RedisSerializer serializer =
					redisTemplate.getValueSerializer();
				
				if (serializer instanceof GenericJackson2JsonRedisSerializer)
				{
					entry =
						((GenericJackson2JsonRedisSerializer) serializer)
							.deserialize(message.getBody(), TransactionRegistryEntry.class);
				} else
				entry =(TransactionRegistryEntry) serializer.deserialize(message.getBody());

				if (entry.getStatus() == TransactionRegistryEntry.Status.rollback)
				{
					listener.onRollback(entry);

					removeListener = true;
				} else
				if (entry.getStatus() == TransactionRegistryEntry.Status.pending)
				{
					if (inAccordWith(transactionId, TransactionRegistryEntry.Status.pending))
					{
						listener.onCommit();

						removeListener = true;
					}
				}

				if (removeListener)
					redisListener.removeMessageListener(this);
			}
		};

		redisListener.addMessageListener(
			msgListener, new ChannelTopic(transactionId + "-event"));
	}

	private boolean inAccordWith(
		String transactionId, TransactionRegistryEntry.Status status)
	{
		Set<String> names = hashOpt.keys(transactionId);

		for(String name: names)
		{
			Status theStatus = hashOpt.get(transactionId, name);

			if (theStatus != status)
				return false;
		}

		return true;
	}
}
