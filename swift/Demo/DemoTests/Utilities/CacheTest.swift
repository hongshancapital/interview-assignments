//
//  CacheTest.swift
//  DemoTests
//
//  Created by 葬花桥 on 2023/3/16.
//

import XCTest
@testable import Demo

final class CacheTest: XCTestCase {
    let sut: DemoCache = .shared
    let testKey = "https://www.test.com"
    let testImage = UIImage(systemName: "suit.heart.fill")!
    func test_demo_cache() {
        sut.set(key: testKey, image: testImage)
        let image = sut.image(key: testKey)
        XCTAssertNotNil(image)
    }
}
