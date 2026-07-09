//
//  LoadableTest.swift
//  DemoTests
//
//  Created by 葬花桥 on 2023/3/16.
//

import XCTest
@testable import Demo

final class LoadableTest: XCTestCase {
    
    
    func test_helperFunctions() {
        let notRequested = Loadable<Int>.notRequested
        let loadingNil = Loadable<Int>.isLoading(last: nil)
        let loadingValue = Loadable<Int>.isLoading(last: 9)
        let loaded = Loadable<Int>.loaded(5)
        let failedErrValue = Loadable<Int>.failed(NSError.test)
        [notRequested, loadingNil].forEach {
            XCTAssertNil($0.value)
        }
        [loadingValue, loaded].forEach {
            XCTAssertNotNil($0.value)
        }
        [notRequested, loadingNil, loadingValue, loaded].forEach {
            XCTAssertNil($0.error)
        }
        XCTAssertNotNil(failedErrValue.error)
    }
}
