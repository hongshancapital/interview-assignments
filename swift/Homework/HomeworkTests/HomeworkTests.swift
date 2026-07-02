//
//  HomeworkTests.swift
//  HomeworkTests
//
//  Created by Andy on 2022/9/5.
//

import XCTest
@testable import Homework

class HomeworkTests: XCTestCase {
    
    var listView: AppListView!

    override func setUpWithError() throws {
        listView = AppListView()
    }

    override func tearDownWithError() throws {
        listView = nil
    }

    func testExample() throws {
       
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        measure {
            // Put the code you want to measure the time of here.
        }
    }

}
