package com.scdt.shorturl.distributed;

import com.scdt.shorturl.distributed.id.ZookeeperService;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
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
	private final CuratorFramework client;
	public ProcessNode(LocalLRUCacheService localLRUCacheService, ZookeeperService zooKeeperService) throws IOException {
		this.localLRUCacheService = localLRUCacheService;
		this.client = zooKeeperService.getClient();
		this.client.watchers().add().usingWatcher(this);
	}

	/**
	 * 尝试成为leader
	 */
	private void attemptForLeader() {
		final List<String> childNodePaths = getChildren(LEADER_ELECTION_ROOT_NODE);
		Collections.sort(childNodePaths);
		int index = childNodePaths.indexOf(processNodePath.substring(processNodePath.lastIndexOf('/') + 1));
		// 第一个上线的就是leader
		if(index == 0) {
			log.info("[Process: " + nodeId + "] I am the new leader!");
			localLRUCacheService.isLeader = true;
		} else {
			final String watchedNodeShortPath = childNodePaths.get(index - 1);
			localLRUCacheService.isLeader = false;
			watchedNodePath = LEADER_ELECTION_ROOT_NODE + "/" + watchedNodeShortPath;
			log.info("[Process: " + nodeId + "] - Setting watch on node with path: " + watchedNodePath);
			watchNode(watchedNodePath);
		}
	}


	/**
	 * 服务启动时，注册节点，准备选举
	 */
	@Override
	public void run() {
		log.info("Process with id: " + nodeId + " has started!");
		//为所有服务器设置一个根Node
		final String rootNodePath = createNode(LEADER_ELECTION_ROOT_NODE , false);
		if(rootNodePath == null) {
			throw new IllegalStateException("Unable to create/access leader election root node with path: " + LEADER_ELECTION_ROOT_NODE);
		}
		// 给自身设置一个临时Node，代表上线了
		processNodePath = createNode(rootNodePath + PROCESS_NODE_PREFIX , true);
		if(processNodePath == null) {
			throw new IllegalStateException("Unable to create/access process node with path: " + LEADER_ELECTION_ROOT_NODE);
		}
		log.debug("[Process: " + nodeId + "] Process node created with path: " + processNodePath);
		attemptForLeader();
	}

	/**
	 * 节点删除事件处理：证明有服务下线，重新选举
	 * @param event
	 */
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

	/**
	 * 创建节点
	 * @param node
	 * @param ephimeral 是否是临时节点
	 * @return
	 */
	public String createNode(final String node, final boolean ephimeral) {
		String createdNodePath = null;
		try {
			final Stat nodeStat =  client.checkExists().forPath(node);

			if(nodeStat == null) {
				createdNodePath = client.create().withMode(ephimeral ?  CreateMode.EPHEMERAL_SEQUENTIAL : CreateMode.PERSISTENT).forPath(node,new byte[0]);
			} else {
				createdNodePath = node;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return createdNodePath;
	}

	/**
	 * 监听选举Node数据变化，有变化，证明服务存在下线，重新选举
	 * @param node
	 * @return
	 */
	public boolean watchNode(final String node) {
		boolean watched = false;
		try {
			final Stat nodeStat =  client.checkExists().forPath(node);;
			if(nodeStat != null) {
				watched = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return watched;
	}

	/**
	 * 获取所有服务器
	 * @param node
	 * @return
	 */
	public List<String> getChildren(final String node) {
		List<String> childNodes = null;
		try {
			childNodes = client.getChildren().forPath(node);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return childNodes;
	}
	
}
