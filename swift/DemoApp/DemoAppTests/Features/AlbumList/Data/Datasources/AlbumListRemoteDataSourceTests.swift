//
//  SoftwareListRemoteDataSourceTests.swift
//  DemoAppTests
//
//  Created by 黄瑞 on 2023/2/19.
//

import XCTest
@testable import DemoApp

final class SoftwareListRemoteDataSourceTests: XCTestCase {

    var dataSource: SoftwareListRemoteDataSource!
    var mockNetworkManager: MockNetworkManager!
    
    override func setUpWithError() throws {
        self.mockNetworkManager = .init()
        self.dataSource = .init(networkManager: self.mockNetworkManager)
    }

    override func tearDownWithError() throws {
        self.mockNetworkManager = nil
        self.dataSource = nil
    }
    
    func test从后端获取数据() async {
        do {
            let limit = 15
            let expect = Array(repeating: Software.default, count: 15)
            let object: [String: Any] = [
                "resultCount": 15,
                "results": try expect.map({ software in
                    let data = try software.toJson()
                    let jsonObject = try JSONSerialization.jsonObject(with: data) as! [String: Any]
                    return jsonObject
                })
            ]
            let data = try JSONSerialization.data(withJSONObject: object)
            
            // arrange
            self.mockNetworkManager.isGetCalledResult = .success(data)
            // act
            let result = try await self.dataSource.getSoftware(count: limit)
            XCTAssertTrue(self.mockNetworkManager.isGetCalled)
            XCTAssertEqual(result, expect)
        } catch {
            XCTAssert(false)
        }
    }
    
    func test从后端获取数据异常() async {
        let failure = ServerException()
        let limit = 15
        
        // arrange
        self.mockNetworkManager.isGetCalledResult = .failure(failure)
        // act
        do {
            let _ = try await self.dataSource.getSoftware(count: limit)
            XCTAssert(false)
        } catch {
            XCTAssertTrue(error is ServerException)
        }
    }
}

//MARK: - Mock network manager
class MockNetworkManager: NetworkManagerProtocol {
    var isIsConnectedCalled = false
    var isConnectedResult: Bool = false
    var isConnected: Bool {
        isIsConnectedCalled = true
        return isConnectedResult
    }
    
    var isGetCalled = false
    var isGetCalledResult: Result<Data, ServerException> = .success(.init())
    func get(url: String) async -> (Data?, Error?) {
        isGetCalled = true
        switch isGetCalledResult {
        case .success(let success):
            return (success, nil)
        case .failure(let failure):
            return (nil, failure)
        }
    }
}

