//
//  RefreshTableDemoTests.swift
//  RefreshTableDemoTests
//
//  Created by yaojinhai on 2022/12/14.
//

import XCTest
@testable import RefreshTableDemo

class RefreshTableDemoTests: XCTestCase {
    
    var model: DataJsonModel? = nil

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
       model  = loadData("data.txt")
        
    }
    


    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        model = nil
    }

    func testExample() throws {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
        // Any test you write for XCTest can be annotated as throws and async.
        // Mark your test throws to produce an unexpected failure when your test encounters an uncaught error.
        // Mark your test async to allow awaiting for asynchronous code to complete. Check the results with assertions afterwards.
        
        XCTAssertNotNil(model, "获取数据模型失败")
        XCTAssertNotNil(model?.results, "数据列表转换失败")
        XCTAssert(model!.results!.count > 0, "数据列表为空")
        

        
        
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
