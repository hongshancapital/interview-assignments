//
//  AppDataProviderTests.swift
//  DemoTests
//
//  Created by milomiao on 2022/6/25.
//

import XCTest
@testable import Demo

class AppDataProviderTests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testObserverFrom() throws {
        let provider = FeedProvider()
        provider.from = 1
        XCTAssertTrue(provider.hasMore)
        XCTAssertEqual(provider.moreDescription, "Loading")
        
        provider.from = 50
        XCTAssertFalse(provider.hasMore)
        XCTAssertEqual(provider.moreDescription, "No more data")
    }
    
    func testUpdatePostion() {
        let provider = FeedProvider()
        XCTAssertTrue(provider.step == 10)
        provider.updatePostion()
        XCTAssertEqual(provider.from, 10)
    }
    
    func testResetPosition() {
        let provider = FeedProvider()
        provider.from = 100;
        provider.resetPosition()
        XCTAssertEqual(provider.from, 0)

    }
    
}

class FeedRequestTests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testLoadData() throws {
        
        let expectation = expectation(description: #function)
        Task {
            if let infos = await FeedRequest.loadData(from: 0, length: 10) {
                XCTAssertEqual(infos.count, 10)
            }
            expectation.fulfill()
        }
        waitForExpectations(timeout: 1)
    }
    
    func testLoadDataOverTotal() throws {
        let expectation = expectation(description: #function)
        Task {
            if let infos = await FeedRequest.loadData(from: 0, length: 60) {
                XCTAssertEqual(infos.count, 50)
            }
            expectation.fulfill()
        }
        waitForExpectations(timeout: 1)
    }
    
    func testLoadDataPart() throws {
        let expectation = expectation(description: #function)
        Task {
            if let infos = await FeedRequest.loadData(from: 45, length: 10) {
                XCTAssertEqual(infos.count, 5)
            }
            expectation.fulfill()
        }
        waitForExpectations(timeout: 1)
    }
}
