//
//  SwiftUIJustPlayTests.swift
//  SwiftUIJustPlayTests
//
//  Created by wangrenzhu2021 on 2022/5/9.
//

import XCTest
import SwiftUIFlux

@testable import SwiftUIJustPlay
import SwiftUI

class SwiftUIJustPlayTests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
        
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testExample() throws {
        let endpoint = PageEndPoint.pageNo(pageIndex: 1, pageSize: 20)
  
        let rs = StoreProvider(store: store) {
            ContentView()
        }
        
        
        let promise = expectation(description: "Status code: 200")
        

        APIService.shared.GET(page: endpoint) {
            (result: Result<AppResult<AppItem>, APIService.APIError>) in
            switch result {
            case let .success(response):
                if (endpoint.pageIndex() == 0) {
                    
                    rs.store.dispatch(action: JustPlayActions.RefreshAppStoreList(page: endpoint,
                                        response: response))
                } else {
                    rs.store.dispatch(action: JustPlayActions.SetAppStoreList(page: endpoint,
                                        response: response))
                }
                break
            case let .failure(error):
                print(error)
                break
            }
            promise.fulfill()
        }
        wait(for: [promise], timeout: 5)
        XCTAssertEqual(rs.store.state.homeState.orderItems.count, 20)
    }

}
