//
//  DemoUITests.swift
//  DemoUITests
//
//  Created by GNR on 10/27/22.
//

import XCTest

final class DemoUITests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.

        // In UI tests it is usually best to stop immediately when a failure occurs.
        continueAfterFailure = false

        // In UI tests it’s important to set the initial state - such as interface orientation - required for your tests before they run. The setUp method is a good place to do this.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    func testLoadMore() throws {
        let app = XCUIApplication()
        app.launch()
                
        let collectionViewsQuery = XCUIApplication().collectionViews
        collectionViewsQuery.children(matching: .cell).element(boundBy: 6).children(matching: .other).element(boundBy: 1).children(matching: .other).element.children(matching: .other).element.children(matching: .other).element.swipeUp()
        collectionViewsQuery.children(matching: .cell).element(boundBy: 6).children(matching: .other).element(boundBy: 1).children(matching: .other).element.children(matching: .other).element.children(matching: .other).element.swipeUp()
        collectionViewsQuery.children(matching: .cell).element(boundBy: 6).children(matching: .other).element(boundBy: 1).children(matching: .other).element.children(matching: .other).element.children(matching: .other).element.swipeUp()
        collectionViewsQuery.children(matching: .cell).element(boundBy: 6).children(matching: .other).element(boundBy: 1).children(matching: .other).element.children(matching: .other).element.children(matching: .other).element.swipeUp()
        collectionViewsQuery.children(matching: .cell).element(boundBy: 6).children(matching: .other).element(boundBy: 1).children(matching: .other).element.children(matching: .other).element.children(matching: .other).element.swipeUp()
        collectionViewsQuery.children(matching: .cell).element(boundBy: 6).children(matching: .other).element(boundBy: 1).children(matching: .other).element.children(matching: .other).element.children(matching: .other).element.swipeUp()
        collectionViewsQuery.children(matching: .cell).element(boundBy: 6).children(matching: .other).element(boundBy: 1).children(matching: .other).element.children(matching: .other).element.children(matching: .other).element.swipeUp()
        collectionViewsQuery.children(matching: .cell).element(boundBy: 6).children(matching: .other).element(boundBy: 1).children(matching: .other).element.children(matching: .other).element.children(matching: .other).element.swipeUp()
        collectionViewsQuery.children(matching: .cell).element(boundBy: 6).children(matching: .other).element(boundBy: 1).children(matching: .other).element.children(matching: .other).element.children(matching: .other).element.swipeUp()
    }
    
    func testFavorite() throws {
        // UI tests must launch the application that they test.
        let app = XCUIApplication()
        app.launch()
   
        let collectionViewsQuery = app.collectionViews
        let addToFavoritesImage = collectionViewsQuery.children(matching: .cell).element(boundBy: 2).images["Add To Favorites"]
        addToFavoritesImage.tap()
        collectionViewsQuery/*@START_MENU_TOKEN@*/.images["Remove From Favorites"]/*[[".cells.images[\"Remove From Favorites\"]",".images[\"Remove From Favorites\"]"],[[[-1,1],[-1,0]]],[0]]@END_MENU_TOKEN@*/.tap()
        addToFavoritesImage.tap()
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
