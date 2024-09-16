//
//  NetWorkTest.swift
//  DemoAppTests
//
//  Created by xiongmin on 2022/4/6.
//

import XCTest
@testable import DemoApp

class NetWorkTest: XCTestCase {
    
    struct TestDecodeErrorResponse: Decodable {
        let testString: String
    }

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testInvalidUrl() async throws {
        let urlString = "invalid url"
        do {
            let _: AppInfoListResponse = try await Network.shared.fetch(urlString)
            XCTAssert(false)
        } catch {
            if let error = error as? Network.NetworkError, case .invalidUrl = error {
                XCTAssert(true)
            }
        }
    }
    
    func testRequestFailed() async throws {
        let urlString = "http://www.test.com"
        do {
            let _: AppInfoListResponse = try await Network.shared.fetch(urlString)
            XCTAssert(false)
        } catch {
            if let error = error as? Network.NetworkError, case .requestFaild = error {
                XCTAssert(true)
            }
        }
    }
    
    func testRequstSuccess() async throws {
        let urlString = "https://itunes.apple.com/search"
        let resposonse: AppInfoListResponse? = try? await Network.shared.fetch(urlString,
                                                                    paramas: ["entity": "software",
                                                                              "limit": 50,
                                                                              "term": "chat"])
        XCTAssertNotNil(resposonse)
    }
    
    func testDecodeError() async throws {
        let urlString = "https://itunes.apple.com/search"
        do {
            let _: TestDecodeErrorResponse = try await Network.shared.fetch(urlString,
                                                                             paramas: ["entity": "software",
                                                                                       "limit": 50,
                                                                                       "term": "chat"])
            XCTAssert(false)
        } catch {
            if error is DecodingError {
                XCTAssert(true)
            } else {
                XCTAssert(false)
            }
        }
    }
    
    func testErrorParams() async throws {
        let urlString = "https://itunes.apple.com/search"
        let resposne: AppInfoListResponse? = try? await Network.shared.fetch(urlString,
                                                                         paramas: ["invalid": "software"])
        XCTAssertNotNil(resposne)
        XCTAssert(resposne!.resultCount == 0 )
    }
}
