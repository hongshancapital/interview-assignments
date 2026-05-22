//

//
//  InterviewDemoTests.swift
//  InterviewDemoTests
//
//  Created by 霍橙 on 2023/2/3.
//  
//
    

import XCTest
@testable import InterviewDemo

class InterviewDemoTests: XCTestCase {

    override func setUpWithError() throws {
    }

    override func tearDownWithError() throws {
    }
    
    func testAppDataRowViewModel() {
        let appData = AppData(id: 0, name: "name", icon: "icon", desc: "desc", isLike: false)
        let appDataRowViewModel = AppDataRowViewModel(appData: appData)
        appDataRowViewModel.changeIsLike {
            XCTAssertTrue(appDataRowViewModel.appData.isLike)
        }
        
        appDataRowViewModel.changeIsLike {
            XCTAssertFalse(appDataRowViewModel.appData.isLike)
        }
    }
}

