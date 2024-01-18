//
//  AppItemInfoTests.swift
//  DemoTests
//
//  Created by milomiao on 2022/6/25.
//

import XCTest
@testable import Demo

class AppItemInfoTests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testDecoder() throws {
        let dict = ["artworkUrl60" : "abc",
                    "trackCensoredName" : "123",
                    "description" : "hello word",
                    "bundleId" : "com.xx"]
        let encoder = JSONEncoder()
        if let data = try? encoder.encode(dict) {
            let decoder = JSONDecoder()
            if let item = try? decoder.decode(AppItemInfo.self, from: data) {
                XCTAssertEqual(item.title, "123")
                XCTAssertEqual(item.subtitle, "hello word")
                XCTAssertEqual(item.id, "com.xx")
                XCTAssertEqual(item.iconURL, "abc")
            }
        }
    }

    func testToggleLike() {
        let item = AppItemInfo()
        XCTAssertFalse(item.isLike)
        item.toggleLike()
        XCTAssertTrue(item.isLike)
    }
    
    func testLikeIcon() {
        let item = AppItemInfo()
        item.isLike = false
        XCTAssertEqual(item.likeIcon, "heart")
        item.isLike = true
        XCTAssertEqual(item.likeIcon, "heart.fill")
    }
}
