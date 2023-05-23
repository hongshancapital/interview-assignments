//
//  RestfulServiceTest.swift
//  DemoTests
//
//  Created by 葬花桥 on 2023/3/16.
//

import XCTest
@testable import Demo
import Combine


class TestRestfulWebRepository: RestfulWebRepository {
    let session: URLSession = .mockedResponsesOnly
    let baseURL = "https://api.test.com"
    let bgQueue = DispatchQueue(label: "test")
}

class DemoRestfulServiceTest: XCTestCase {
    var sut: DemoRealRestfulService!
    var subscriptions = Set<AnyCancellable>()
    typealias Mock = RequestMocking.MockedResponse
    override func setUp() {
        super.setUp()
        subscriptions.removeAll()
        sut = DemoRealRestfulService(webRepository: TestRestfulWebRepository())
    }
    
    func test_homePageList_succeed() throws {
        var applications = [Application]()
        for i in 0..<10 {
            applications.append(Application(trackName: "test\(i)", artworkUrl60: "png\(i)", description: "des\(i)"))
        }
        
        let testData = HomePageListResult(resultCount: 50, results: applications)

        
        let exp = XCTestExpectation(description: #function)
        try mock(.homePageList(limit: 10), result: .success(testData))
        sut.homePageList(limit: 10).sinkToResult {
            XCTAssertEqual($0.isSuccess, true)
            exp.fulfill()
        }.store(in: &subscriptions)
     
        wait(for: [exp], timeout: 2)
    }
    
    func test_async_homePageList_succeed() async throws {
        var applications = [Application]()
        for i in 0..<10 {
            applications.append(Application(trackName: "test\(i)", artworkUrl60: "png\(i)", description: "des\(i)"))
        }
        
        let testData = HomePageListResult(resultCount: 50, results: applications)

        
        try mock(.homePageList(limit: 10), result: .success(testData))
  
        let result = try await sut.homePageList(limit: 10)
        
        XCTAssertEqual(result.count, 10)
        
    }
    
    private func mock<T>(_ apiCall: DemoRealRestfulService.API, result: Result<T, Swift.Error>,
                         httpCode: HTTPCode = 200) throws where T: Encodable {
        let mock = try Mock(apiCall: apiCall, baseURL: sut.webRepository.baseURL, result: result, httpCode: httpCode)
        RequestMocking.add(mock: mock)
    }
    
}

