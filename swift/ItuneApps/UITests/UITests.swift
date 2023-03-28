//
//  UITests.swift
//  UITests
//
//  Created by daicanglan on 2023/3/27.
//

import XCTest

final class UITests: XCTestCase {
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.

        // In UI tests it is usually best to stop immediately when a failure occurs.
        continueAfterFailure = false

        // In UI tests it’s important to set the initial state - such as interface orientation - required for your tests before they run. The setUp method is a good place to do this.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testScrollMore() throws {
        let app = XCUIApplication()
        app.launch()

        let collectionViewsQuery = XCUIApplication().collectionViews
        var cell = collectionViewsQuery.children(matching: .cell).element(boundBy: 2)
        cell.swipeUp()
        cell = collectionViewsQuery.children(matching: .cell).element(boundBy: 4)
        cell.swipeUp()
        cell = collectionViewsQuery.children(matching: .cell).element(boundBy: 4)
        cell.swipeDown()
    }

    func testClickLike() throws {
        let app = XCUIApplication()
        app.launch()

        let collectionViewsQuery = XCUIApplication().collectionViews
        let cell = collectionViewsQuery.children(matching: .cell).element(boundBy: 1)
        cell.images["Love"].tap()
        collectionViewsQuery.children(matching: .cell).element(boundBy: 4).images["Love"].tap()
        collectionViewsQuery.children(matching: .cell).element(boundBy: 3).images["Love"].tap()
        collectionViewsQuery.children(matching: .cell).element(boundBy: 2).images["Love"].tap()

        let element = cell.children(matching: .other).element(boundBy: 1).children(matching: .other).element
        element.swipeDown()
        element.swipeDown()
    }

    func testLaunchPerformance() throws {
        if #available(macOS 10.15, iOS 13.0, tvOS 13.0, watchOS 7.0, *) {
            // This measures how long it takes to launch your application.
            measure(metrics: [XCTApplicationLaunchMetric()]) {
                XCUIApplication().launch()
            }
        }
    }
}
