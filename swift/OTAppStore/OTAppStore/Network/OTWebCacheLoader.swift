//
//  OTWebCacheLoader.swift
//  OTAppStore
//
//  Created by liuxj on 2022/3/18.
//

import Foundation


/**
 简单的网络缓存加载类
 实现了内存、磁盘、网络三级缓存加载
 */

actor OTWebCacheLoader {
    static let shared = OTWebCacheLoader()
    var inMomeryCache: [String: Data] = [:]
    
    //MARK: Api
    
    public func loadData(from urlString: String) async throws -> Data {
        guard let url = URL(string: urlString) else {
            throw OTNetworkError.invaildURL
        }
        
        //1. check momery
        if let inMomeryData = loadInMomeryImageData(from: urlString) {
            return inMomeryData
        }
        
        let request = URLRequest(url: url,
                                 cachePolicy: .returnCacheDataElseLoad,
                                 timeoutInterval: 30.0)
        
        //2. check cache
        if let cachedData = loadCachedImageData(from: request) {
            return cachedData
        }
        
        //3. check remote
        let remoteData = try await loadRemoteImageData(from: request)
        return remoteData
    }
    
    //MARK: load data
    
    private func loadInMomeryImageData(from urlString: String) -> Data? {
        let inMomeryData = inMomeryCache[urlString]
        return inMomeryData
    }
    
    private func loadCachedImageData(from request: URLRequest) -> Data? {
        if let cachedData = URLCache.shared.cachedResponse(for: request)?.data {
            inMomeryCache[request.url!.path] = cachedData
            return cachedData
        }
        return nil
    }
    
    private func loadRemoteImageData(from request: URLRequest) async throws -> Data {
        let (remoteData, response): (Data, URLResponse)
        do {
            (remoteData, response) = try await URLSession.shared.data(for: request)
        } catch {
            throw OTNetworkError.badResponse
        }
                
        inMomeryCache[request.url!.path] = remoteData
        let cachedDResponse = CachedURLResponse(response: response, data: remoteData)
        URLCache.shared.storeCachedResponse(cachedDResponse, for: request)
        
        return remoteData
    }
}
