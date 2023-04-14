//
//  APPListUITests.swift
//  APPListUITests
//
//  Created by three on 2023/4/10.
//

import XCTest

final class APPListUITests: XCTestCase {

    let app = XCUIApplication()
    
    override func setUpWithError() throws {
        continueAfterFailure = false

        app.launch()
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testExample() throws {
        // UI tests must launch the application that they test.
        let app = XCUIApplication()
        app.launch()

        // Use XCTAssert and related functions to verify your tests produce the correct results.
    }

    func testLaunchPerformance() throws {
        if #available(macOS 10.15, iOS 13.0, tvOS 13.0, watchOS 7.0, *) {
            // This measures how long it takes to launch your application.
            measure(metrics: [XCTApplicationLaunchMetric()]) {
                XCUIApplication().launch()
            }
        }
    }
    
    func test_ListViewCell_Display() {
        let collectionViewsQuery = app.collectionViews
        
        let wait = collectionViewsQuery.cells.element(boundBy: 1).waitForExistence(timeout: 3)

        XCTAssertTrue(wait)
        
        let cellCount = collectionViewsQuery.cells.count
        
        XCTAssertGreaterThan(cellCount, 0)
    }
    
    func test_ListViewCellImage_Display() {
        let collectionViewsQuery = XCUIApplication().collectionViews
        let cell = collectionViewsQuery.children(matching: .cell).element(boundBy: 0)
        let imageExist = cell.images.firstMatch.waitForExistence(timeout: 5)
        
        XCTAssertTrue(imageExist)
    }
    
    func test_ListViewCellTitleLabel_Display() {
        let collectionViewsQuery = XCUIApplication().collectionViews
        let cell = collectionViewsQuery.children(matching: .cell).element(boundBy: 0)
        
        let titleExist = cell.staticTexts["appListItem_TitleLabel"].waitForExistence(timeout: 5)
        
        XCTAssertTrue(titleExist)
    }
    
    func test_ListViewCellInfoLabel_Display() {
        let cell = findFirstCell()
        
        let infoExist = cell.staticTexts["appListItem_InfoLabel"].waitForExistence(timeout: 3)
        
        XCTAssertTrue(infoExist)
    }
    
    func test_ListViewCellFavoriteBtn_Display() {
        let cell = findFirstCell()
        let btnExist = cell.buttons["appListItem_FavBtn"].waitForExistence(timeout: 3)
        
        XCTAssertTrue(btnExist)
    }
    
    
    func test_ListViewCellFavoriteBtn_Tao() {
        let cell = findFirstCell()
        let btnExist = cell.buttons["appListItem_FavBtn"].waitForExistence(timeout: 3)
        
        XCTAssertTrue(btnExist)
        
        let btn = cell.buttons["appListItem_FavBtn"]
        
        let state = btn.isSelected
        btn.tap()
        
        XCTAssertTrue(!state)
    }
    
    func findFirstCell() -> XCUIElement {
        let collectionViewsQuery = XCUIApplication().collectionViews
        let cell = collectionViewsQuery.children(matching: .cell).element(boundBy: 0)
        
        return cell
    }
}
