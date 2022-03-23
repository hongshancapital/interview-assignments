//
//  ListDemoTests.swift
//  ListDemoTests
//
//  Created by mac on 2022/3/18.
//

import XCTest
@testable import ListDemo

class ListDemoTests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testExample() throws {
        try refreshData()
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }
    
    func refreshData() throws {
        let viewModel = ListContentModel()
        viewModel.refreshData()
    }

}
