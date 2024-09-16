package com.scdt.shorturl.distributed;

import com.scdt.shorturl.model.Record;
import com.scdt.shorturl.model.Res;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 分布式集群LRUCache 实现
 * 利用zookeeper的ZAB协议选举，同步LRUCache
 * @author tommy zhang
 * @email zdp118@gmail.com
 */
@Slf4j
@Service
public class DistributedLRUCacheService {

	public static final String METHOD_GET = "get";
	public static final String METHOD_SET = "set";
	private final RestTemplate restTemplate;
	private final LocalLRUCacheService localLRUCacheService;
	private final DiscoveryClient discoveryClient;
	@Value("${spring.application.name}")
	private String applicationName;
	@Value("${spring.cloud.zookeeper.discovery.instance-id}")
	private String nodeId;

	public DistributedLRUCacheService(RestTemplate restTemplate, LocalLRUCacheService localLRUCacheService, DiscoveryClient discoveryClient) {
		this.restTemplate = restTemplate;
		this.localLRUCacheService = localLRUCacheService;
		this.discoveryClient = discoveryClient;
	}

	//=====================================================set==================================
	/**
	 * set记录到集群，如果是leader直接同步，不是leader就先广播找leader
	 * @param urls 长域名或者短域名
	 * @return
	 */
	public Mono<Res<List<Record>>> setOrGet(Set<String> urls,String method){
		if (urls==null||urls.isEmpty()) return Mono.empty();
		try {
			if(localLRUCacheService.isLeader) {
				//是leader直接同步并返回值
				sync(urls,method);
				if (METHOD_SET.equals(method)) return localLRUCacheService.createShortUrlByLongUrl(urls);
				else return localLRUCacheService.getLongUrlByShortUrl(urls);
			} else {
				//不是leader先广播找leader
				return Mono.just(broadcast(urls,method));
			}
		}catch (Exception e){
			log.error(e.getMessage());
			return Mono.error(e);
		}
	}
	/**
	 * 当前如果是leader收到广播，开始同步记录，并给客户端返回值，不是leader直接跳过
	 * @param urls 长域名或者短域名
	 * @return
	 */
	public Mono<Res<List<Record>>> broadcastedAndSetOrGet(Set<String> urls,String method){
		if(localLRUCacheService.isLeader) {
			//同步到集群
			sync(urls,method);
			if (METHOD_SET.equals(method)) return localLRUCacheService.createShortUrlByLongUrl(urls);
			else return localLRUCacheService.getLongUrlByShortUrl(urls);
		}
		return Mono.empty();
	}

	//========================================================================================================

	/**
	 * 获取集群所有服务节点
	 * @return
	 */
	public List<ServiceInstance> getInstances() {
		return discoveryClient.getInstances(applicationName);
	}

	/**
	 * leader同步映射记录到集群
	 * @param urls
	 * @param method
	 */
	private void sync(Set<String> urls, String method) {
		var list = getInstances();
		if (list != null && !list.isEmpty() ) {
			for(ServiceInstance node : list) {
				//跳过自身
				if(node.getInstanceId().equals(nodeId))
					continue;
				try {
					restTemplate.put(node.getUri().toString() + "/short-urls/api/v1/inner/sync/"+method, urls);
				} catch(RestClientException restClientException) {
					log.warn("Error : ", restClientException);
				}
			}
		}
	}

	/**
	 * 广播找leader拿数据，leader再做去同步
	 * @param urls 长域名或者短域名
	 * @param method
	 */
	private Res<List<Record>> broadcast(Set<String> urls, String method) {
		Res<List<Record>> result = null;
		var list = getInstances();
		if (list != null && !list.isEmpty() ) {
			for(ServiceInstance node : list) {
				//跳过自身
				if(node.getInstanceId().equals(nodeId))
					continue;
				RequestEntity<Set<String>> requestEntity = new RequestEntity<>(urls,HttpMethod.POST, URI.create(node.getUri().toString() + "/short-urls/api/v1/inner/broadcast/"+method));
				ResponseEntity<Res> responseEntity = restTemplate.exchange(requestEntity, Res.class);
				if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.hasBody() && Objects.requireNonNull(responseEntity.getBody()).isSuccess()){
					//获得leader返回的数据
					result = responseEntity.getBody();
				}
			}
		}
		return result;
	}
	
}
