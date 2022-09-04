//
//  SwiftUI_DemoTests.swift
//  SwiftUI_DemoTests
//
//  Created by mazb on 2022/9/2.
//

import XCTest

@testable import SwiftUI_Demo

class SwiftUI_DemoTests: XCTestCase {

    var listVM: MListViewModel!
    
    override func setUpWithError() throws {
        listVM =  MListViewModel()
    }

    override func tearDownWithError() throws {
        listVM = nil
    }

    func testExample() throws {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
//        listVM.loadMore()
        
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
