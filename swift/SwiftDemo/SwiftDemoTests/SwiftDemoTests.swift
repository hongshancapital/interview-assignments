//
//  SwiftDemoTests.swift
//  SwiftDemoTests
//
//  Created by liuyang on 2023/3/5.
//

import XCTest
@testable import SwiftDemo

class SwiftDemoTests: XCTestCase {

    var appDataManager: AppDataManager!
    
    override func setUpWithError() throws {
        appDataManager = AppDataManager()
    }

    override func tearDownWithError() throws {
        appDataManager = nil
    }

    func testLoadData() throws {
        XCTAssert(appDataManager.loadLocalJson().count > 0)
    }
}
