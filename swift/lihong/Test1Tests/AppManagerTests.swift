//
//  AppManagerTests.swift
//  Test1Tests
//
//  Created by Stephen Li on 2022/10/19.
//

import Foundation

import XCTest
@testable import Test1

class AppManagerTests: XCTestCase {
    
    override func setUpWithError() throws {
        // Setup mock data with 20 items
        AppManager.shared.configBackend(MockServer("1", "txt", 0, 0, 20))
    }
    
    override func tearDownWithError() throws {
        // Reset backend server
        AppManager.shared.configBackend(nil)
    }

    func testGetAppsInvalidInput() async throws {
        let results = await AppManager.shared.getApps(-1, 100)
        XCTAssertTrue(0 == results.count, "Results should be empty, but it returns: \(results.count)")

        let results2 = await AppManager.shared.getApps(1, -10)
        XCTAssertTrue(0 == results2.count, "Results2 should be empty, but it returns: \(results2.count)")

        let results3 = await AppManager.shared.getApps(-5, -3)
        XCTAssertTrue(0 == results3.count, "Results3 should be empty, but it returns: \(results3.count)")
    }

    func testGetAppsCommon() async throws {
        let results = await AppManager.shared.getApps(1, 5)
        XCTAssertTrue(5 == results.count, "There should be 5 items, but it returns: \(results.count)")

        let results2 = await AppManager.shared.getApps(2, 10)
        XCTAssertTrue(10 == results2.count, "There should be 10 items, but it returns: \(results2.count)")
    }

    func testGetAppsBoundary() async throws {
        let results = await AppManager.shared.getApps(4, 6)
        XCTAssertTrue(2 == results.count, "There should be 2 items, but it returns: \(results.count)")

        let results2 = await AppManager.shared.getApps(5, 5)
        XCTAssertTrue(0 == results2.count, "Results2 should be empty, but it returns: \(results2.count)")
    }

    func testMarkFavorited() async throws {
        let results = await AppManager.shared.getApps(1, 2)
        XCTAssertTrue(2 == results.count, "There should be 2 items.")

        AppManager.shared.markFavorited(results[0].id, true)
        let results2 = await AppManager.shared.getApps(1, 2)
        XCTAssertTrue(2 == results2.count, "There should be 2 items.")
        XCTAssertTrue(results2[0].isFavorited, "This item's favorited flag should be true.")
        XCTAssertFalse(results2[1].isFavorited, "This item's favorited flag should be false.")
    }
}
