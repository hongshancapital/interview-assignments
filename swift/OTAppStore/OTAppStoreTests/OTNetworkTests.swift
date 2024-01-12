//
//  OTNetworkTests.swift
//  OTAppStoreTests
//
//  Created by liuxj on 2022/3/19.
//

import XCTest

@testable import OTAppStore

class OTNetworkTests: XCTestCase {
    
    let network = OTNetwork.shared
    
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }
    
    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    func testGetData() async throws {
        let requestPath = "https://itunes.apple.com/search"
        let requestParams: OTNetworkParams = ["entity": "software",
                                              "limit": 50,
                                              "term": "chat"]
        
        let appData: AppData = try await self.network.getData(from: requestPath, params: requestParams)
        XCTAssertNotNil(appData)
    }
    
    func testGetDataThrows() async throws {
        do {
            let _: AppModel = try await self.network.getData(from: "invaild url", params: [:])
            XCTFail("Excepted to throw OTNetworkError.parseError while await, but succeed")
        } catch {
            XCTAssertEqual(error as? OTNetworkError, OTNetworkError.invaildURL)
        }
        
    
        let requestPath = "https://itunes.apple.com/search"
        let requestParams: OTNetworkParams = ["entity": "software",
                                              "limit": 50,
                                              "term": "chat"]
        
        do {
            let _: AppModel = try await self.network.getData(from: requestPath, params: requestParams)
            XCTFail("Excepted to throw OTNetworkError.parseError while await, but succeed")
        } catch {
            XCTAssertEqual(error as? OTNetworkError, OTNetworkError.parseError)
        }
    }
    
    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }
    
}
