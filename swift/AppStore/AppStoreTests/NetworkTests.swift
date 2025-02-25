//
//  NetworkTests.swift
//  AppStoreTests
//
//  Created by liuxing on 2022/4/12.
//

import XCTest

@testable import AppStore

class NetworkTests: XCTestCase {

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

    func testNetworkUrlError() async throws {
        let urlPath = "xxx"
        do {
            let _: AppListModel = try await Network.shared.requestData(urlPath: urlPath)
        } catch let error {
            XCTAssertNotNil(error)
        }
    }
    
    func testNetworkUnSupportMethod() async throws {
        let urlPath = "https://itunes.apple.com/search"
        do {
            let _: AppListModel = try await Network.shared.requestData(method: "DELETE", urlPath: urlPath)
        } catch let error {
            XCTAssertNotNil(error)
        }
    }
    
    func testNetworkRequestFailed() async throws {
        let urlPath = "https://itunes.apple.com/search"
        let params: [String : Any] = [
            "entity": "xxx",
            "limit": 50,
            "term": "chat"
        ]
        do {
            let _: AppListModel = try await Network.shared.requestData(urlPath: urlPath, params: params)
        } catch let error {
            XCTAssertNotNil(error)
        }
    }
    
    func testNetworkRequestSuccess() async throws {
        let urlPath = "https://itunes.apple.com/search"
        let params: [String : Any] = [
            "entity": "software",
            "limit": 50,
            "term": "chat"
        ]
        let model: AppListModel = try await Network.shared.requestData(urlPath: urlPath, params: params)
        XCTAssertTrue(model.resultCount >= 0)
        XCTAssertTrue(model.results.count >= 0)
    }

}
