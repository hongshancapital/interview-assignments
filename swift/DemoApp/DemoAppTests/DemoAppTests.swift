//
//  DemoAppTests.swift
//  DemoAppTests
//
//  Created by Gao on 2022/7/9.
//

import XCTest
import Combine
@testable import DemoApp
import SwiftUI

class DemoAppTests: XCTestCase {
    var vm: ViewModel?

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
        vm = ViewModel()
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        vm = nil
    }
    
    func testViewModel() throws {
        let expectation = self.expectation(description: "延时等待fetch结束")
        DispatchQueue.global().asyncAfter(deadline: DispatchTime.now()+5) {
            expectation.fulfill()
        }
        waitForExpectations(timeout: 10)
        XCTAssert(vm?.data.count == 20, "获取的AppModel应为20")
        
    }
    
    func testLoadMore() throws {
        vm?.loadMoreSubject.send()
        let expectation = self.expectation(description: "延时等待fetch结束")
        DispatchQueue.global().asyncAfter(deadline: DispatchTime.now()+5) {
            expectation.fulfill()
        }
        waitForExpectations(timeout: 10)
        XCTAssert(vm?.data.count == 40, "获取的AppModel应为20")
    }
    
    func testFavotite() throws {
        let expectation = self.expectation(description: "延时等待fetch结束")
        DispatchQueue.global().asyncAfter(deadline: DispatchTime.now()+5) {
            expectation.fulfill()
        }
        waitForExpectations(timeout: 10)
        vm?.data[0].isFavorite = true;
        XCTAssert(vm?.data[0].isFavorite == true,  "AppModel的isFavorite应该为true")
        
    }
    

}
