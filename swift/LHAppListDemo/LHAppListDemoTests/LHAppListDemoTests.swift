//
//  LHAppListDemoTests.swift
//  LHAppListDemoTests
//
//  Created by lzh on 2022/3/26.
//

import XCTest
@testable import LHAppListDemo

class LHAppListDemoTests: XCTestCase {

    let vm = LHViewModel()
    
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testViewModelLoadMoreData() throws {
        let predicate = NSPredicate() { [weak self] _,_ in
            self!.vm.models.count <= 15 && self!.vm.models.count > 0
        }
        let expectation = XCTNSPredicateExpectation(predicate: predicate, object: vm)
        vm.loadMoreAppInfo()
        let result = XCTWaiter().wait(for: [expectation], timeout: 5.0)
        switch result {
        case .completed:
            XCTAssertGreaterThan(vm.models.count, 0)
            XCTAssertLessThanOrEqual(vm.models.count, 15)

        default:
            XCTFail()
        }
    }
    
    func testViewModelLoadRecentData() throws {
        let predicate = NSPredicate() { [weak self] _,_ in
            self!.vm.models.count <= 15 && self!.vm.models.count > 0
        }
        let expectation = XCTNSPredicateExpectation(predicate: predicate, object: vm)
        vm.loadRecentAppInfo()
        let result = XCTWaiter().wait(for: [expectation], timeout: 5.0)
        switch result {
        case .completed:
            XCTAssertLessThanOrEqual(vm.models.count, 15)
            XCTAssertGreaterThan(vm.models.count, 0)

        default:
            XCTFail()
        }
    }
    
    func testViewModelLike() throws {
        let predicate = NSPredicate() { [weak self] _,_ in
            self!.vm.models.count <= 15 && self!.vm.models.count > 0
        }
        let expectation = XCTNSPredicateExpectation(predicate: predicate, object: vm)
        vm.loadRecentAppInfo()
        let result = XCTWaiter().wait(for: [expectation], timeout: 5.0)
        switch result {
        case .completed:
            XCTAssertLessThanOrEqual(vm.models.count, 15)
            XCTAssertGreaterThan(vm.models.count, 0)
            vm.like(app: vm.models.first!)
            XCTAssertEqual(vm.models.first?.isLike, true)
        default:
            XCTFail()
        }
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
