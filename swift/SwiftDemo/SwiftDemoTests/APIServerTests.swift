//
//  APIServerTests.swift
//  SwiftDemoTests
//
//  Created by teenloong on 2022/8/27.
//

import Foundation

import XCTest
@testable import SwiftDemo

class APIServerTests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    func testAPIServer() async throws {
        let response = try await AS.debug(false).requestAsync(action: SearchAppAction(.init(entity: "software", term: "chat", limit: 10)))

        XCTAssertTrue(response.results?.count ?? 0 == 10, "SearchAppAction results error")
    }

}
