//
//  SocialAppListTests.swift
//  SocialAppListTests
//
//  Created by 刘飞 on 2023/4/29.
//

import XCTest
@testable import SocialAppList

class SocialAppListTests: XCTestCase {

    override func setUp() {
        super.setUp()
    }

    override func tearDown() {
        super.tearDown()
    }

    let vm = ListViewModel()
    func testViewModel() {
        let exp = expectation(description: #function)
        
        vm.send(.refresh(isFirst: true))
        
        DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
            XCTAssertEqual(self.vm.showEmptyView, self.vm.list.isEmpty)
            XCTAssertFalse(self.vm.refreshing)
            
            let a = self.vm.list.count % 20
            XCTAssertEqual(self.vm.hasMore, !(a > 0 && a < 20))

            exp.fulfill()
        }
        
        waitForExpectations(timeout: 3, handler: nil)
    }
}
