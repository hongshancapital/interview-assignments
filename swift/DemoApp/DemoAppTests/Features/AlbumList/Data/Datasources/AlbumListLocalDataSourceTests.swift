//
//  SoftwareListLocalDataSourceTests.swift
//  DemoAppTests
//
//  Created by 黄瑞 on 2023/2/19.
//

import XCTest
@testable import DemoApp

final class SoftwareListLocalDataSourceTests: XCTestCase {
    var dataSource: SoftwareListLocalDataSource!
    var mockCache: MockCacheManager!
    
    override func setUpWithError() throws {
        self.mockCache = MockCacheManager()
        self.dataSource = .init(cache: self.mockCache)
    }

    override func tearDownWithError() throws {
        self.dataSource = nil
        self.mockCache = nil
    }
    
    func test有缓存的时候返回缓存内容() {
        let expect = Array(repeating: Software.default, count: 15)
        
        do {
            // arrange
            self.mockCache.setStringKey = cacheKey
            let object = [cacheKey: Array(repeating: Software.default, count: 15)]
            let data = try JSONEncoder().encode(object)
            self.mockCache.setStringValue = String(data: data, encoding: .utf8)
            // act
            let result = try self.dataSource.getCachedSoftwareList()
            // expect
            XCTAssert(self.mockCache.isGetStringCalled)
            XCTAssertEqual(expect, result)
        } catch {
            // expect
            XCTAssert(false, error.localizedDescription)
        }
    }
    
    func test没有缓存的时候需要抛出异常() {
        // arrange
        self.mockCache.setStringKey = nil
        self.mockCache.setStringValue = nil
        // act and expect
        do {
            let _ = try self.dataSource.getCachedSoftwareList()
            XCTAssert(false, "需要报错")
        } catch {
            XCTAssert(error is CacheException)
        }
    }
    
    func test缓存数据() {
        // act
        self.dataSource.cacheSoftwareList(list: Array(repeating: Software.default, count: 15))
        // expect
        XCTAssertTrue(self.mockCache.isSetStringCalled)
    }
}

//MARK: - Mock Cahce util
class MockCacheManager: CacheManagerProtocol {
    var isSetStringCalled = false
    var setStringKey: String?
    var setStringValue: String?
    func setString(_ string: String, forKey key: String) {
        isSetStringCalled = true
        setStringKey = key
        setStringValue = string
    }
    
    var isGetStringCalled = false
    func getString(forKey key: String) -> String? {
        isGetStringCalled = true
        
        if key != setStringKey {
            return nil
        }
        
        return setStringValue!
    }
}
