//
//  AppListTests.swift
//  AppListTests
//
//  Created by Xiaojian Duan on 2022/11/13.
//

import XCTest
@testable import AppList

final class AppListTests: XCTestCase {
    
    var model = AppListModel()
    var viewModel = AppListViewModel()
    
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
        model.infoArray = [AppInfo(icon: "", name: "a", description: "a desc", id: 1),
                           AppInfo(icon: "", name: "b", description: "b desc", id: 2),
                           AppInfo(icon: "", name: "c", description: "c desc", id: 3)
                          ]
        model.likedItems = []
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testExample() throws {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
        // Any test you write for XCTest can be annotated as throws and async.
        // Mark your test throws to produce an unexpected failure when your test encounters an uncaught error.
        // Mark your test async to allow awaiting for asynchronous code to complete. Check the results with assertions afterwards.
    }

    func testModelAdd() throws {
        model.likedItems.insert(4)
        model.addAppInfos([AppInfo(icon: "", name: "d", description: "d desc", id: 4)])
        XCTAssertTrue(model.infoArray[3].like)
    }
    
    func testModelToggleLike() throws {
        model.toggleLike(3)
        XCTAssertTrue(model.infoArray[2].like)
        model.toggleLike(3)
        XCTAssertFalse(model.infoArray[2].like)
    }
}
