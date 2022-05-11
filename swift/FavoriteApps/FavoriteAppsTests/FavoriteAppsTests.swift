//
//  FavoriteAppsTests.swift
//  FavoriteAppsTests
//
//  Created by 刘明星 on 2022/5/6.
//

import XCTest
@testable import FavoriteApps

class FavoriteAppsTests: XCTestCase {
    
    var model: FetchModelAppList!

    override func setUpWithError() throws {
        model = FetchModelAppList()
    }

    override func tearDownWithError() throws {
        model = nil
    }

    func testExample() throws {
        model.fetch()
    }

}
