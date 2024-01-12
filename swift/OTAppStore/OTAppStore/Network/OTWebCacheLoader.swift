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

    private enum OTWebCacheEntry {
        case inProgress(Task<OTNetworkResult, Error>)
        case ready(Data)
    }

    static let shared = OTWebCacheLoader()
    private var inMomeryCache: [String: OTWebCacheEntry] = [:]

    //MARK: Api

    public func loadData(from urlString: String) async throws -> Data {
        guard let url = URL(string: urlString) else {
            throw OTNetworkError.invaildURL
        }

        //1. check momery
        if let inMomeryData = try await loadInMomeryImageData(from: urlString) {
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

    private func loadInMomeryImageData(from urlString: String) async throws -> Data? {
        if let inMomeryCache = inMomeryCache[urlString] {
            switch inMomeryCache {
            case .ready(let data):
                return data
            case .inProgress(let task):
                return try await task.value.data
            }
        }
        return nil
    }

    private func loadCachedImageData(from request: URLRequest) -> Data? {
        if let cachedData = URLCache.shared.cachedResponse(for: request)?.data {
            inMomeryCache[request.url!.absoluteString] = .ready(cachedData)
            return cachedData
        }
        return nil
    }

    private func loadRemoteImageData(from request: URLRequest) async throws -> Data {
        let task = Task<OTNetworkResult, Error> {
            try await URLSession.shared.data(for: request)
        }
        
        let urlString = request.url!.absoluteString
        inMomeryCache[urlString] = .inProgress(task)
        
        do {
            let result: OTNetworkResult = try await task.value
            inMomeryCache[urlString] = .ready(result.data)
            let cachedResponse = CachedURLResponse(response: result.response, data: result.data)
            URLCache.shared.storeCachedResponse(cachedResponse, for: request)
            return result.data
        } catch {
            throw OTNetworkError.badResponse
        }
    }
}
