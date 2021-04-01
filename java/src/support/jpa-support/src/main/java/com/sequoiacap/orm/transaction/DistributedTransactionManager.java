package com.sequoiacap.orm.transaction;

import java.util.Stack;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

import com.sequoiacap.orm.transaction.registry.TransactionRegistry;
import com.sequoiacap.orm.transaction.registry.TransactionRegistryEntry;
import com.sequoiacap.orm.transaction.registry.TransactionRegistryListener;

public class DistributedTransactionManager
	implements PlatformTransactionManager
{
	private static Log log =
		LogFactory.getLog(DistributedTransactionManager.class);

	@Autowired(required = false)
	private TransactionRegistry transactionRegistry;

	private PlatformTransactionManager internalTranscationManager;

	public DistributedTransactionManager()
	{ }

	public DistributedTransactionManager(
		PlatformTransactionManager internalTranscationManager)
	{
		setTranscationManager(internalTranscationManager);
	}

	@Override
	public TransactionStatus getTransaction(TransactionDefinition definition)
		throws TransactionException
	{
		TransactionStatus transactionStatus =
			internalTranscationManager.getTransaction(definition);

		if (transactionRegistry != null &&
			(definition.getPropagationBehavior() ==
				TransactionDefinition.PROPAGATION_REQUIRES_NEW ||
			 transactionId() != null) &&
			transactionStatus.isNewTransaction())
		{
			String transactionId = transactionId();
			
			if (StringUtils.isBlank(transactionId))
				transactionId = newTransactionId();

			log.info(String.format(
				"start distributed transaction within %s", transactionId));

			transactionsStack().push(transactionStatus);

			transactionRegistry.writeTransactionRegistryEntry(
				transactionId, transactionStatus,
				TransactionRegistryEntry.Status.normal);
		}

		return transactionStatus;
	}

	@Override
	public void commit(final TransactionStatus status)
		throws TransactionException
	{
		if (status.equals(currentTransactionStatus()))
		{
			if (status.isRollbackOnly())
			{
				rollback(status);
				return;
			}

			final String transactionId = transactionId();

			log.info(String.format(
				"commit pending distributed transaction within %s", transactionId));

			transactionRegistry.subscribe(
				transactionId, new TransactionRegistryListener() {
					private boolean pending = true;

					@Override
					public synchronized void onRollback(TransactionRegistryEntry entry)
					{
						if (!pending)
							return;

						log.info(String.format(
							"rollback distributed transaction within %s because xtransaction %s",
								transactionId, entry.getEntryName()));

						internalTranscationManager.rollback(status);

						transactionRegistry.writeTransactionRegistryEntry(
							transactionId, status,
							TransactionRegistryEntry.Status.rollback);

						pending = false;
					}

					@Override
					public synchronized void onCommit()
					{
						if (!pending)
							return;
						
						log.info(String.format(
							"commit confirm distributed transaction within %s", transactionId));

						internalTranscationManager.commit(status);
						
						transactionRegistry.writeTransactionRegistryEntry(
							transactionId, status,
							TransactionRegistryEntry.Status.commit);
						
						pending = false;
					}					
				});

			transactionsStack().pop();
			closeTransactionId();
			
			transactionRegistry.writeTransactionRegistryEntry(
				transactionId, status,
				TransactionRegistryEntry.Status.pending);
		} else
		internalTranscationManager.commit(status);
	}

	@Override
	public void rollback(TransactionStatus status)
		throws TransactionException
	{
		if (status.equals(currentTransactionStatus()))
		{
			String transactionId = transactionId();
			
			log.info(String.format(
				"rollback distributed transaction within %s", transactionId));

			transactionRegistry.writeTransactionRegistryEntry(
				transactionId, status,
				TransactionRegistryEntry.Status.rollback);

			transactionsStack().pop();
			closeTransactionId();
		}
		
		internalTranscationManager.rollback(status);	
	}

	public PlatformTransactionManager getTranscationManager() {
		return internalTranscationManager;
	}

	public void setTranscationManager(PlatformTransactionManager transcationManager)
	{
		this.internalTranscationManager = transcationManager;
	}



	private static final ThreadLocal<Stack<String>> transactionIds =
		new ThreadLocal<Stack<String>>();

	private static final ThreadLocal<Stack<TransactionStatus>> transactions =
			new ThreadLocal<Stack<TransactionStatus>>();

	private static Stack<TransactionStatus> transactionsStack()
	{
		if (transactions.get() == null)
			transactions.set(new Stack<TransactionStatus>());

		return transactions.get();
	}

	private static TransactionStatus currentTransactionStatus()
	{
		if (transactionsStack().empty())
			return null;

		return transactionsStack().peek();
	}
	
	private static Stack<String> idStack()
	{
		if (transactionIds.get() == null)
			transactionIds.set(new Stack<String>());

		return transactionIds.get();
	}

	public static String transactionId()
	{
		if (idStack().empty())
			return null;
		
		return idStack().peek();
	}

	public static String newTransactionId()
	{
		UUID uuid = UUID.randomUUID();

		return setTransactionId("XT-" + uuid.toString());
	}

	public static String setTransactionId(String transactionId)
	{
		return idStack().push(transactionId);
	}
	
	public static void closeTransactionId()
	{
		if (idStack().empty())
			return;
		
		idStack().pop();
	}
}
