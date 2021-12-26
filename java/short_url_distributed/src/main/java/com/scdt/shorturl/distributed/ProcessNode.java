package com.scdt.shorturl.distributed;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class ProcessNode implements Watcher,Runnable{
	
	private static final String LEADER_ELECTION_ROOT_NODE = "/cache-election";
	private static final String PROCESS_NODE_PREFIX = "/p_";
	
	private String processNodePath;
	private String watchedNodePath;

	@Value("${spring.cloud.zookeeper.discovery.instance-id}")
	private int nodeId;
	
	private final LocalLRUCacheService localLRUCacheService;
	private final ZooKeeper zooKeeper;
	public ProcessNode(LocalLRUCacheService localLRUCacheService, @Value("${spring.cloud.zookeeper.connect-string}") String url) throws IOException {
		this.localLRUCacheService = localLRUCacheService;
		this.zooKeeper = new ZooKeeper(url, 3000, this);
	}

	/**
	 * 尝试成为leader
	 */
	private void attemptForLeader() {
		
		final List<String> childNodePaths = getChildren(LEADER_ELECTION_ROOT_NODE, false);
		
		Collections.sort(childNodePaths);
		
		int index = childNodePaths.indexOf(processNodePath.substring(processNodePath.lastIndexOf('/') + 1));
		if(index == 0) {
			log.info("[Process: " + nodeId + "] I am the new leader!");
			localLRUCacheService.isLeader = true;
		} else {
			final String watchedNodeShortPath = childNodePaths.get(index - 1);
			localLRUCacheService.isLeader = false;
			
			watchedNodePath = LEADER_ELECTION_ROOT_NODE + "/" + watchedNodeShortPath;
			
			log.info("[Process: " + nodeId + "] - Setting watch on node with path: " + watchedNodePath);
			watchNode(watchedNodePath, true);
		}
	}
	
	@Override
	public void run() {
		
		log.info("Process with id: " + nodeId + " has started!");
		
		final String rootNodePath = createNode(LEADER_ELECTION_ROOT_NODE, false, false);
		if(rootNodePath == null) {
			throw new IllegalStateException("Unable to create/access leader election root node with path: " + LEADER_ELECTION_ROOT_NODE);
		}
		
		processNodePath = createNode(rootNodePath + PROCESS_NODE_PREFIX, false, true);
		if(processNodePath == null) {
			throw new IllegalStateException("Unable to create/access process node with path: " + LEADER_ELECTION_ROOT_NODE);
		}
		
		log.debug("[Process: " + nodeId + "] Process node created with path: " + processNodePath);
		
		attemptForLeader();
	}

	@Override
	public void process(WatchedEvent event) {

		log.debug("[Process: " + nodeId + "] Event received: " + event);

		final Event.EventType eventType = event.getType();
		if(Watcher.Event.EventType.NodeDeleted.equals(eventType)) {
			if(event.getPath().equalsIgnoreCase(watchedNodePath)) {
				attemptForLeader();
			}
		}

	}


	public String createNode(final String node, final boolean watch, final boolean ephimeral) {
		String createdNodePath = null;
		try {

			final Stat nodeStat =  zooKeeper.exists(node, watch);

			if(nodeStat == null) {
				createdNodePath = zooKeeper.create(node, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, (ephimeral ?  CreateMode.EPHEMERAL_SEQUENTIAL : CreateMode.PERSISTENT));
			} else {
				createdNodePath = node;
			}

		} catch (KeeperException | InterruptedException e) {
			throw new IllegalStateException(e);
		}

		return createdNodePath;
	}

	public boolean watchNode(final String node, final boolean watch) {

		boolean watched = false;
		try {
			final Stat nodeStat =  zooKeeper.exists(node, watch);

			if(nodeStat != null) {
				watched = true;
			}

		} catch (KeeperException | InterruptedException e) {
			throw new IllegalStateException(e);
		}

		return watched;
	}

	public List<String> getChildren(final String node, final boolean watch) {

		List<String> childNodes = null;

		try {
			childNodes = zooKeeper.getChildren(node, watch);
		} catch (KeeperException | InterruptedException e) {
			throw new IllegalStateException(e);
		}

		return childNodes;
	}
	
}
