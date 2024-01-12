//
//  OTWebCacheLoaderTests.swift
//  OTAppStoreTests
//
//  Created by liuxj on 2022/3/19.
//

import XCTest

@testable import OTAppStore

class OTWebCacheLoaderTests: XCTestCase {

    let webCacheLoader = OTWebCacheLoader()

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testLoadData() async throws {
        let data1 = try? await webCacheLoader.loadData(from: "invail url")
        XCTAssertNil(data1)

        let data2 = try? await self.webCacheLoader.loadData(from: "https://is2-ssl.mzstatic.com/image/thumb/Purple116/v4/06/8c/e5/068ce5a0-8a33-41ee-488a-95067d2b241a/source/60x60bb.jpg")

        XCTAssertNotNil(data2)
    }


    func testGetDataThrows() async throws {
        do {
            let _ = try await self.webCacheLoader.loadData(from: "https://w.e.c")
            XCTFail("Excepted to throw OTNetworkError.badResponse while await, but succeed")
        } catch {
            XCTAssertEqual(error as? OTNetworkError, OTNetworkError.badResponse)
        }


        do {
            let _ = try await self.webCacheLoader.loadData(from: "invaild url")
            XCTFail("Excepted to throw OTNetworkError.invaildUR while await, but succeed")
        } catch {
            XCTAssertEqual(error as? OTNetworkError, OTNetworkError.invaildURL)
        }

    }



    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
