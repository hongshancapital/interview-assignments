//
//  SoftwareListLocalDataSource.swift
//  DemoApp
//
//  Created by 黄瑞 on 2023/2/16.
//

import Foundation

let cacheKey = "cacheKey"

protocol SoftwareListLocalDataSourceProtocol {
    /// 获取缓存的软件列表
    func getCachedSoftwareList() throws -> [Software]
    /// 缓存软件列表
    func cacheSoftwareList(list: [Software])
}

class SoftwareListLocalDataSource: SoftwareListLocalDataSourceProtocol {
    
    private let cache: CacheManagerProtocol
    
    init(cache: CacheManagerProtocol) {
        self.cache = cache
    }
    
    func getCachedSoftwareList() throws -> [Software] {
        do {
            guard let string = cache.getString(forKey: cacheKey) else {
                throw CacheException()
            }
            let data = string.data(using: .utf8)!
            let object = try JSONDecoder().decode([String: [Software]].self, from: data)
            let objectList = object[cacheKey]!
            return objectList
        } catch {
            throw CacheException()
        }
    }
    
    /// 一次只缓存15个数据
    func cacheSoftwareList(list: [Software]) {
        if list.count != 15 {
            return
        }
        let jsonString = "{\"cache\": \(list.map({ String(data: try! $0.toJson(), encoding: .utf8) }))}"
        self.cache.setString(jsonString, forKey: cacheKey)
    }
}
