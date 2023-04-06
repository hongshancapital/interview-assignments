//
//  testSwiftUITests.swift
//  testSwiftUITests
//
//  Created by pchen on 2023/3/12.


import XCTest
import Combine

@testable import testSwiftUI

@MainActor
final class testSwiftUITests: XCTestCase {

    var vm: AppViewModel = AppViewModel()
    var cancellable = Set<AnyCancellable>()
    
    
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testNetworkService() async {
        do {
            try await vm.refreshAppModels()
            XCTAssertNotEqual(vm.appModels.count, 0)
        }catch {
            XCTAssertNil(nil, error.localizedDescription.description)
        }
    }
    
    func testGetPageIndexArray() {
    
        var number = vm.getPageIndexArray(pageIndex: 1, array: [])
        XCTAssertEqual(number.count, 0)
        
        number = vm.getPageIndexArray(pageIndex: 0, array: [Int](1...20))
        XCTAssertEqual(number.count, 20)
        
        number = vm.getPageIndexArray(pageIndex: 0, array: [Int](1...22))
        XCTAssertEqual(number.count, 20)
        
        number = vm.getPageIndexArray(pageIndex: 1, array: [Int](1...22))
        XCTAssertEqual(number.count, 2)
        XCTAssertEqual(number.last as! Int, 22)
        XCTAssertEqual(number.first as! Int, 21)
        
        number = vm.getPageIndexArray(pageIndex: 2, array: [Int](1...22))
        XCTAssertEqual(number.count, 0)
        
        number = vm.getPageIndexArray(pageIndex: 1, array: [Int](1...42))
        XCTAssertEqual(number.count, 20)
    }
    
    
    func testExample() throws {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
        // Any test you write for XCTest can be annotated as throws and async.
        // Mark your test throws to produce an unexpected failure when your test encounters an uncaught error.
        // Mark your test async to allow awaiting for asynchronous code to complete. Check the results with assertions afterwards.
    }

}
