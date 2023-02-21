//
//  SoftwareListRepositoryTests.swift
//  DemoAppTests
//
//  Created by 黄瑞 on 2023/2/16.
//

import XCTest
@testable import DemoApp

final class SoftwareListRepositoryTests: XCTestCase {
    
    var mockNetworkManager: MockNetworkManager!
    var mockRemoteDataSource: MockRemoteDataSource!
    var mockLocalDataSource: MockLocalDataSource!
    var repository: SoftwareListRepository!
    
    override func setUpWithError() throws {
        self.mockNetworkManager = MockNetworkManager()
        self.mockRemoteDataSource = MockRemoteDataSource()
        self.mockLocalDataSource = MockLocalDataSource()
        self.repository = SoftwareListRepository(
            remoteDataSource: self.mockRemoteDataSource,
            localDataSource: self.mockLocalDataSource,
            networkManager: self.mockNetworkManager
        )
    }
    
    override func tearDownWithError() throws {
        self.repository = nil
        self.mockNetworkManager = nil
        self.mockLocalDataSource = nil
        self.mockRemoteDataSource = nil
    }
    
    func test请求数据时需要判断是否有网络() async {
        let limit = 10

        // arrange
        self.mockNetworkManager.isConnectedResult = true
        self.mockNetworkManager.isIsConnectedCalled = false
        // act
        let _ = await self.repository.getSoftware(count: limit)
        // assert
        XCTAssertTrue(self.mockNetworkManager.isIsConnectedCalled)
    }
    
    func test有网时请求数据需要从后端获取数据() async {
        let limit = 10

        // arrange
        self.mockNetworkManager.isConnectedResult = true
        self.mockRemoteDataSource.getSoftwareResult = .success([Software.default])
        // act
        let result = await self.repository.getSoftware(count: limit)
        // assert
        XCTAssertTrue(self.mockRemoteDataSource.isGetSoftwareCalled)
        XCTAssertEqual(try! result.get(), try! self.mockRemoteDataSource.getSoftwareResult!.get())
    }
    
    func test有网需要从后端请求数据且将数据缓存() async {
        let limit = 10
        
        // arrange
        self.mockNetworkManager.isConnectedResult = true
        self.mockRemoteDataSource.getSoftwareResult = .success([Software.default])
        // act
        let _ = await self.repository.getSoftware(count: limit)
        // assert
        XCTAssertTrue(self.mockLocalDataSource.isCacheSoftwareListCalled)
        XCTAssertTrue(self.mockRemoteDataSource.isGetSoftwareCalled)
    }
    
    func test后端数据源抛出异常() async {
        let limit = 10
        
        // arrange
        self.mockNetworkManager.isConnectedResult = true
        self.mockRemoteDataSource.getSoftwareResult = .failure(ServerFailure())
        // act
        let result = await self.repository.getSoftware(count: limit)
        // assert
        XCTAssertTrue(self.mockRemoteDataSource.isGetSoftwareCalled)
        XCTAssertTrue(self.mockLocalDataSource.isAnyInvocation == false)
        
        do {
            let _ = try result.get()
            XCTAssert(false)
        } catch {
            XCTAssertTrue(error is ServerFailure)
        }
    }
    
    func test从本地缓存读取数据正常() async {
        let limit = 10
        
        // arrage
        self.mockNetworkManager.isConnectedResult = false
        // act
        let _ = await self.repository.getSoftware(count: limit)
        // assert
        XCTAssertTrue(self.mockLocalDataSource.isGetSoftwareCalled)
    }
    
    func test从本地缓存读取数据异常() async {
        let limit = 10
        
        // arrage
        self.mockLocalDataSource.getSoftwareResult = .failure(CacheFailure())
        // act
        let result = await self.repository.getSoftware(count: limit)
        // assert
        XCTAssertTrue(self.mockRemoteDataSource.isAnyInvocation == false)
        XCTAssertTrue(self.mockLocalDataSource.isGetSoftwareCalled)
        do {
            let _ = try result.get()
            XCTAssert(false)
        } catch {
            XCTAssertTrue(error is CacheFailure)
        }
    }
}

// Remote Datasource
class MockRemoteDataSource: SoftwareListRemoteDataSourceProtocol {
    var isAnyInvocation: Bool {
        isGetSoftwareCalled
    }
    func reset() {
        isGetSoftwareCalled = false
    }
    
    var isGetSoftwareCalled = false
    var getSoftwareResult: Result<[DemoApp.Software], Failure>?
    func getSoftware(count: Int) async throws -> [Software] {
        isGetSoftwareCalled = true
        if let getSoftwareResult = getSoftwareResult {
            switch getSoftwareResult {
            case .success(let list):
                return list
            case .failure(let failure):
                throw failure
            }
        } else {
            return []
        }
    }
}

// Local Datasource
class MockLocalDataSource: SoftwareListLocalDataSourceProtocol {
    var isAnyInvocation: Bool {
        isCacheSoftwareListCalled || isGetSoftwareCalled
    }
    func reset() {
        isGetSoftwareCalled = false
        isCacheSoftwareListCalled = false
    }
    
    var isGetSoftwareCalled = false
    var getSoftwareResult: Result<[DemoApp.Software], Failure>?
    func getCachedSoftwareList() throws -> [DemoApp.Software] {
        isGetSoftwareCalled = true
        if let getSoftwareResult = getSoftwareResult {
            switch getSoftwareResult {
            case .success(let list):
                return list
            case .failure(let failure):
                throw failure
            }
        } else {
            return []
        }
    }
    
    var isCacheSoftwareListCalled = false
    func cacheSoftwareList(list: [DemoApp.Software]) {
        isCacheSoftwareListCalled = true
    }
}
