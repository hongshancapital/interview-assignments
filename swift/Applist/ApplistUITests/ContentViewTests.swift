//
//  LoadingMoreViewTests.swift
//  ApplistUITests
//
//  Created by wulei7 on 2023/1/30.
//

import XCTest

final class ContentViewTests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.

        // In UI tests it is usually best to stop immediately when a failure occurs.
        continueAfterFailure = false

        // In UI tests it’s important to set the initial state - such as interface orientation - required for your tests before they run. The setUp method is a good place to do this.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    func testLikeBtn() throws {
        let app = XCUIApplication()
        app.launch()
        
        let collectionViewsQuery = XCUIApplication().collectionViews
        let cell = collectionViewsQuery.children(matching: .cell).element(boundBy: 0)
        cell.buttons["Love"].tap()
        
        let loveButton = collectionViewsQuery.children(matching: .cell).element(boundBy: 1).buttons["Love"]
        loveButton.tap()
        loveButton.tap()
        
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
