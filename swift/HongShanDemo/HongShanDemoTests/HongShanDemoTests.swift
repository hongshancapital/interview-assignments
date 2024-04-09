//
//  HongShanDemoTests.swift
//  HongShanDemoTests
//
//  Created by 林纪涛 on 2023/4/10.
//

import XCTest
@testable import HongShanDemo

final class HongShanDemoTests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
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

    func testDataInterface() throws {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
        // Any test you write for XCTest can be annotated as throws and async.
        // Mark your test throws to produce an unexpected failure when your test encounters an uncaught error.
        // Mark your test async to allow awaiting for asynchronous code to complete. Check the results with assertions afterwards.
  
        let expectation = self.expectation(description: "load list data")
        
        let viewModel = AppListViewModel()
        viewModel.requestListData(page: 0) { data in
            XCTAssert(data != nil)
            expectation.fulfill()
        }
        
        self.waitForExpectations(timeout: 10) { error in
            if let error = error {
                XCTFail("timeout errored: \(error)")
            }
        }
    }
    func testprocessModelToList() throws {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
        // Any test you write for XCTest can be annotated as throws and async.
        // Mark your test throws to produce an unexpected failure when your test encounters an uncaught error.
        // Mark your test async to allow awaiting for asynchronous code to complete. Check the results with assertions afterwards.
        let expectation = self.expectation(description: "text Process Model To List")
        
        let viewModel = AppListViewModel()
        viewModel.requestListData(page: 0) { data in
            XCTAssert(data != nil)
            viewModel.processModelToList(data!)
            
            expectation.fulfill()
        }
        
        self.waitForExpectations(timeout: 10) { error in
            if let error = error {
                XCTFail("timeout errored: \(error)")
            }
        }
    }
    
    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
