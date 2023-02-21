//
//  SoftwareListRemoteDataSource.swift
//  DemoApp
//
//  Created by 黄瑞 on 2023/2/16.
//

import Foundation

protocol SoftwareListRemoteDataSourceProtocol {
    /// 获取软件列表
    /// 抛出 ServerException 异常
    func getSoftware(count: Int) async throws -> [Software]
}

class SoftwareListRemoteDataSource: SoftwareListRemoteDataSourceProtocol {
    let networkManager: NetworkManagerProtocol
    
    init(networkManager: NetworkManagerProtocol) {
        self.networkManager = networkManager
    }
    
    func getSoftware(count: Int) async throws -> [Software] {
        let url = SoftwareAPI.replacing("{limit}", with: "\(count)")
        let result = await self.networkManager.get(url: url)
        if result.1 != nil || result.0 == nil {
            throw ServerException()
        }
        let data = result.0!
        do {
            let object = try JSONSerialization.jsonObject(with: data) as! [String: Any]
            
            guard let resultCount = object["resultCount"] as? Int else {
                throw ServerException()
            }
            
            if resultCount != count {
                throw ServerException()
            }
            
            guard let results = object["results"] as? [[String: Any]] else {
                throw ServerException()
            }
                    
            let softwareList = try results.map { jsonObject in
                let data = try JSONSerialization.data(withJSONObject: jsonObject)
                let software = try Software.fromJson(data: data)
                return software
            }
            return softwareList
        } catch {
            throw ServerException()
        }
    }
}
