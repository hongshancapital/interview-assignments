//
//  NetworkAPITests.swift
//  AppTests
//
//  Created by august on 2022/3/24.
//

import XCTest
@testable import App

class NetworkAPITests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    func testNetworkInvalidUrl() async throws {
        let path = "xxxx"
        let api = NetworkAPI<HomeListResponse>(path: path)
        do {
            _ = try await api.fetchResponse()
        } catch let error {
            XCTAssertNotNil(error)
        }
    }
    
    func testNetworkInvalidUrlResponse() async throws {
        let path = "xxxx://asjkh"
        let api = NetworkAPI<HomeListResponse>(path: path)
        let response = try? await api.fetchResponse()
        XCTAssertNil(response)
    }
    
    func testUnsupportMethod() async throws {
        let path = "https://itunes.apple.com/search"
        let api = NetworkAPI<HomeListResponse>(path: path)
        do {
            _ = try await api.fetchResponse(with: "put")
        } catch let error {
            XCTAssertNotNil(error)
        }
    }
    
    func testNetworkInvalidParamatersResponse() async throws {
        let path = "https://itunes.apple.com/search"
        let api = NetworkAPI<HomeListResponse>(path: path)
        let response = try? await api.fetchResponse()
        XCTAssertTrue(response != nil)
        XCTAssertEqual(response?.resultCount, 0)
        XCTAssertEqual(response?.results.count, 0)
    }
    
    func testNetworkValidParamatersResponse() async throws {
        let path = "https://itunes.apple.com/search"
        let api = NetworkAPI<HomeListResponse>(path: path)
        let response = try? await api.fetchResponse(paramaters: ["entity":"software","limit":50,"term":"chat"])
        XCTAssertTrue(response != nil)
        XCTAssertTrue(response!.resultCount >= 0)
        XCTAssertTrue(response!.results.count >= 0)
    }
}
