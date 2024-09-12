//
//  NetworkTests.swift
//  DemoTests
//
//  Created by GNR on 11/1/22.
//

import XCTest
@testable import Demo

final class NetworkTests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    func testPageSize() async throws {
        do {
            try await NetWorkManager.shared.fetchApps(0, 0)
            XCTAssert(false, "badData")
        } catch {
            XCTAssert(true, "networkError")
        }
    }

    func testPageIndex() async throws {
        do {
            try await NetWorkManager.shared.fetchApps(10, -10)
            XCTAssert(false, "badData")
        } catch {
            XCTAssert(true, "networkError")
        }
    }
    
    func testResults() async throws {
        do {
            let apps = try await NetWorkManager.shared.fetchApps(20, 0)
            XCTAssert(apps.count == 20, "badData")
        } catch {
            XCTAssert(false, "networkError")
        }
    }
    
    func testResults1() async throws {
        do {
            let apps = try await NetWorkManager.shared.fetchApps(20, 2)
            XCTAssert(apps.count == 10, "badData")
        } catch {
            XCTAssert(false, "networkError")
        }
    }
    
    func testResults2() async throws {
        do {
            let apps = try await NetWorkManager.shared.fetchApps(11, 1)
            XCTAssert(apps.count == 11, "badData")
        } catch {
            XCTAssert(false, "networkError")
        }
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
