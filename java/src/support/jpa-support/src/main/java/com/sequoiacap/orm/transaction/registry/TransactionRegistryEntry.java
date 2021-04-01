package com.sequoiacap.orm.transaction.registry;

public class TransactionRegistryEntry
{
	public static enum Status
	{
		normal,
		pending,
		rollback,
		commit
	}

	private String entryName;
	private Status status;

	public String getEntryName() {
		return entryName;
	}
	public void setEntryName(String entryName) {
		this.entryName = entryName;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
}
