//
//  DemoTests.swift
//  DemoTests
//
//  Created by csmac05 on 2023/2/7.
//

import XCTest
@testable import Demo

final class DemoTests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testExample() throws {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
        // Any test you write for XCTest can be annotated as throws and async.
        // Mark your test throws to produce an unexpected failure when your test encounters an uncaught error.
        // Mark your test async to allow awaiting for asynchronous code to complete. Check the results with assertions afterwards.
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }
    
    func testBackendMockDataRead() throws {
        //G
        let filename = "mockData"
        //W
        let mockData:MockData = try loadData(fileName: filename)
        //T
        XCTAssertTrue(mockData.results.count > 0)
    }
    
    func testBackendMockApi_list01() async throws  {
        //G
        let page = 1
        let pageSize = 10
        let params = ["page": page, "pageSize": pageSize]
        //W
        let result = try await MockApi.artistListRequest(params)
        //T
        XCTAssertTrue(result.count == 10)
    }
    
    func testBackendMockApi_list02() async throws  {
        //G
        let page = 5
        let pageSize = 10
        let params = ["page": page, "pageSize": pageSize]
        //W
        let result = try await MockApi.artistListRequest(params)
        //T
        XCTAssertTrue(result.isEmpty)
    }

    func testBackendMockApi_Like() async throws {
        //G
        let id = 1163852619
        let params: [String: Any] = ["isLike": true, "trackId": id]
        //W
        try await MockApi.likeRequest(params)
    }
    
    func testBackendMockApi_Like02() async throws {
        //G
        let id = 101
        let params: [String: Any] = ["isLike": true, "trackId": id]
        //W
        XCTAssertNoThrow {
            do {
                try await MockApi.likeRequest(params)
            } catch {
            }
        }
    }
}
