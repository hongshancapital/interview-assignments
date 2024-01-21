//
//  StoreMock.swift
//  SequoiaDemoTests
//
//  Created by 王浩沣 on 2023/5/7.
//

import Foundation
import XCTest
@testable import SequoiaDemo
import Combine

///用于测试的Store
class MockStore: Store {
    var expecation: XCTestExpectation? = nil
    
    var dispatchAction: AppAction?
    
    override func dispatch(_ action: AppAction) {
        guard let expecation = expecation else {
            return
        }
        expecation.fulfill()
        dispatchAction = action
    }
}
