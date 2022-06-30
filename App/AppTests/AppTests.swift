//
//  AppTests.swift
//  AppTests
//
//  Created by xiongjin on 2022/6/29.
//

import XCTest
@testable import App

class AppTests: XCTestCase {
    
    var contentView: ContentView!

    override func setUpWithError() throws {
        contentView = ContentView()
    }

    override func tearDownWithError() throws {
        contentView = nil
    }

    func testExample() throws {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
    }
    
    func testFavorites() {

        let bundleId = "com.google.Dynamite"
        
        contentView.checkfavoritesEvent(bundleId: bundleId)
        let containsBool: Bool = contentView.selections.contains(bundleId)
        
        if containsBool {
            XCTAssertTrue(containsBool, "cancel favorites")
        } else {
            XCTAssertFalse(containsBool, "add favorites")
        }
    }
    
    func testInitResourceResult() {
        let resultCount: Int = 0
        let count = ResourceViewModel().resourceList.count
        XCTAssertEqual(resultCount, count)
    }
    
}
