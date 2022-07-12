//
//  QA_engineer_s_toolboxUITests.swift
//  QA engineer's toolboxUITests
//
//  Created by Xun Qu on 2022/7/12.
//

import XCTest

class QA_engineer_s_toolboxUITests: XCTestCase {
    var app: XCUIApplication!
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.

        // In UI tests it is usually best to stop immediately when a failure occurs.
//        continueAfterFailure = false

        // In UI tests it’s important to set the initial state - such as interface orientation - required for your tests before they run. The setUp method is a good place to do this.
        try super.setUpWithError()
        continueAfterFailure = false
        app = XCUIApplication()
        app.launch()
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    func testAddNewTool(){
        
        let app = XCUIApplication()
        let pleaseEnterEngineerSNameTextField = app.textFields["Please enter Engineer's name"]
        pleaseEnterEngineerSNameTextField.tap()
        let tool = UUID().uuidString
        pleaseEnterEngineerSNameTextField.typeText(tool)
        
        let e2eButton = app/*@START_MENU_TOKEN@*/.buttons["E2E"]/*[[".segmentedControls.buttons[\"E2E\"]",".buttons[\"E2E\"]"],[[[-1,1],[-1,0]]],[0]]@END_MENU_TOKEN@*/
        e2eButton.tap()
        app.buttons["Submit"].tap()
        
        let element = app.tables.cells[tool + ", :, E2E"]
        XCTAssert(element.exists)
    }
    
    func testAddNewToolWithoutName(){
        
        let app = XCUIApplication()
        
        let e2eButton = app/*@START_MENU_TOKEN@*/.buttons["E2E"]/*[[".segmentedControls.buttons[\"E2E\"]",".buttons[\"E2E\"]"],[[[-1,1],[-1,0]]],[0]]@END_MENU_TOKEN@*/
        e2eButton.tap()
        app.buttons["Submit"].tap()
        
        let element = app.tables.cells[", :, E2E"]
        XCTAssert(!element.exists)
    }

}
