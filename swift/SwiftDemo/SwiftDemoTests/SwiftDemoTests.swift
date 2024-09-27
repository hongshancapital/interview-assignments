//
//  SwiftDemoTests.swift
//  SwiftDemoTests
//
//  Created by xz on 2023/2/3.
//

import XCTest
@testable import SwiftDemo

final class SwiftDemoTests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    func testFetchModels() throws {
        let expectation = self.expectation(description: "Fetch Models")
        NetworkManager.loadFeeds { t in
            if let feeds = t {
                XCTAssertEqual(feeds.resultCount, feeds.results.count)
                XCTAssertEqual(feeds.resultCount, 50)
            } else {
                XCTFail("Failed to fetch feeds: feeds is nil")
            }
            expectation.fulfill()
        }
        
        waitForExpectations(timeout: 2.0) { error in
            if let error = error {
                XCTFail("Failed to wait for expectation: \(error)")
                return
            }
        }
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
