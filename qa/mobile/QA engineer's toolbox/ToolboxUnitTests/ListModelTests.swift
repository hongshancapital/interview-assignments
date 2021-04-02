//
//  ListModelTests.swift
//  ToolboxUnitTests
//
//  Created by evan on 2021/4/2.
//

import XCTest
@testable import QA_engineer_s_toolbox

class ListModelTests: XCTestCase {
    var listModel: ToolboxListModel?

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testInitialization() {
        listModel = ToolboxListModel()
        XCTAssertEqual(listModel?.options, [])
        XCTAssertEqual(listModel?.results, [])

        let results = [ToolboxModel(id: 1, name: "TestName", tools: "TestTools")]
        let options = ["1", "2", "3"]
        listModel = ToolboxListModel(results: results, options: options)
        XCTAssertEqual(listModel?.options, options)
        XCTAssertEqual(listModel?.results, results)
    }

    func testSubmit() {
        listModel = ToolboxListModel()
        XCTAssertLessThan(listModel!.optionsIndex, listModel!.options.count, "Options out of range!")
        listModel?.submit()

        let options = ["1", "2", "3"]
        let results = [ToolboxModel(id: 1, name: "TestName", tools: "TestTools")]
        listModel?.options = options
        listModel?.results = results
        listModel?.submit()
        let promise = expectation(description: "Load result")
        listModel?.loadToolboxList { value in
            XCTAssertEqual(results, value)
            promise.fulfill()
        }
        wait(for: [promise], timeout: 5)
    }
}
