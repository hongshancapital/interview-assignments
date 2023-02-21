//
//  SoftwareListRepository.swift
//  DemoApp
//
//  Created by 黄瑞 on 2023/2/16.
//

/// https://itunes.apple.com/search?terms=music&limit=50

import Foundation

class SoftwareListRepository: SoftwareListRepositoryProtocol {
    let remoteDataSource: SoftwareListRemoteDataSourceProtocol
    let localDataSource: SoftwareListLocalDataSourceProtocol
    let networkManager: NetworkManagerProtocol
    
    init(remoteDataSource: SoftwareListRemoteDataSourceProtocol, localDataSource: SoftwareListLocalDataSourceProtocol, networkManager: NetworkManagerProtocol) {
        self.remoteDataSource = remoteDataSource
        self.localDataSource = localDataSource
        self.networkManager = networkManager
    }
    
    func getSoftware(count: Int) async -> Result<[Software], Failure> {
        if networkManager.isConnected {
            do {
                let result = try await self.remoteDataSource.getSoftware(count: count)
                self.localDataSource.cacheSoftwareList(list: result)
                return .success(result)
            } catch let error as Failure {
                return .failure(error)
            } catch {
                return .failure(Failure())
            }
        } else {
            do {
                let res = try self.localDataSource.getCachedSoftwareList()
                return .success(res)
            } catch let error as Failure {
                return .failure(error)
            } catch {
                return .failure(Failure())
            }
        }
    }
}
