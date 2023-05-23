//
//  RestfulWebRepositoryTest.swift
//  DemoTests
//
//  Created by 葬花桥 on 2023/3/16.
//

import XCTest
@testable import Demo
import Combine

final class RestfulWebRepositoryTest: XCTestCase {
    var sut: RestfulWebRepository!
    var subscriptions = Set<AnyCancellable>()
    typealias Mock = RequestMocking.MockedResponse
    override func setUp() {
        super.setUp()
        subscriptions.forEach { $0.cancel() }
        subscriptions.removeAll()
        sut = DemoRealRestfulRepository(session: .mockedResponsesOnly, baseURL: "https://api.test.com")
    }
    
    override func tearDown() {
        RequestMocking.removeAllMocks()
    }

    func test_webRepository_success() throws {
        let data = TestData()
        try mock(.test, result: .success(data))
        let exp = XCTestExpectation(description: "Completion")
        sut.call(endpoint: API.test).sinkToResult { (result: Result<TestData, Error>) in
            XCTAssertTrue(Thread.isMainThread)
            result.assertSuccess(value: data)
            exp.fulfill()
        }.store(in: &subscriptions)
        wait(for: [exp], timeout: 2)
    }
    
    func test_webRepository_httpCodeFailure() throws {
        let data = TestData()
        try mock(.test, result: .success(data), httpCode: 500)
        let exp = XCTestExpectation(description: "Completion")
        sut.call(endpoint: API.test).sinkToResult { (result: Result<TestData, Error>) in
            XCTAssertTrue(Thread.isMainThread)
            result.assertFailure("Unexpected HTTP code: 500")
            exp.fulfill()
        }.store(in: &subscriptions)
        wait(for: [exp], timeout: 2)
    }
    
    func test_webRepository_no_network() throws {
        let testError = NSError(domain: NSURLErrorDomain, code: -1009, userInfo: nil)
        try mock(.test, result: Result<TestData, Error>.failure(testError))
        let exp = XCTestExpectation(description: "Completion")
        sut.call(endpoint: API.test).sinkToResult { (result: Result<TestData, Error>) in
            XCTAssertTrue(Thread.isMainThread)
            result.assertFailure(testError.localizedDescription)
            exp.fulfill()
        }.store(in: &subscriptions)
        wait(for: [exp], timeout: 2)
    }
    
    func test_webRepository_networkingError() throws {
        let error = NSError.test
        try mock(.test, result: Result<TestData, Error>.failure(error))
        let exp = XCTestExpectation(description: "Completion")
        sut.call(endpoint: API.test).sinkToResult { (result: Result<TestData, Error>) in
            XCTAssertTrue(Thread.isMainThread)
            result.assertFailure(error.localizedDescription)
            exp.fulfill()
        }.store(in: &subscriptions)
        wait(for: [exp], timeout: 2)
    }
    
    func test_webRepository_requestURLError() {
        let exp = XCTestExpectation(description: "Completion")
        sut.call(endpoint: API.urlError).sinkToResult { (result: Result<TestData, Error>) in
            XCTAssertTrue(Thread.isMainThread)
            result.assertFailure(Demo.APIError.invalidURL.localizedDescription)
            exp.fulfill()
        }.store(in: &subscriptions)
        wait(for: [exp], timeout: 2)
    }
    
    func test_webRepository_requestBodyError() {
        let exp = XCTestExpectation(description: "Completion")
        sut.call(endpoint: API.bodyError).sinkToResult { (result: Result<TestData, Error>) in
            XCTAssertTrue(Thread.isMainThread)
            result.assertFailure(APIError.fail.localizedDescription)
            exp.fulfill()
        }.store(in: &subscriptions)
        wait(for: [exp], timeout: 2)
    }
    
    func test_webRepository_noHttpCodeError() throws {
        let response = URLResponse(url: URL(fileURLWithPath: ""),
                                   mimeType: "example", expectedContentLength: 0, textEncodingName: nil)
        let mock = try Mock(apiCall: API.test, baseURL: sut.baseURL, customResponse: response)
        RequestMocking.add(mock: mock)
        let exp = XCTestExpectation(description: "Completion")
        sut.call(endpoint: API.test).sinkToResult { (result: Result<TestData, Error>) in
            XCTAssertTrue(Thread.isMainThread)
            result.assertFailure(Demo.APIError.unexpectedResponse.localizedDescription)
            exp.fulfill()
        }.store(in: &subscriptions)
        wait(for: [exp], timeout: 2)
    }
    
    private func mock<T>(_ apiCall: API, result: Result<T, Swift.Error>,
                         httpCode: HTTPCode = 200) throws where T: Encodable {
        let mock = try Mock(apiCall: apiCall, baseURL: sut.baseURL, result: result, httpCode: httpCode)
        RequestMocking.add(mock: mock)
    }
}

fileprivate extension RestfulWebRepositoryTest {
    struct TestData: Codable, Equatable {
        var name: String = "WangPeiXin"
        var age: Int = 12
    }
    
    enum API: APICall {
        case test
        case urlError
        case bodyError
        case noHttpCodeError
        
        var path: String {
            if self == .urlError {
                return "😋😋😋"
            }
            return "/test/path"
        }
        var method: HTTPMethod { .post }
        var headers: [String: String]? { nil }
        func body() throws -> Data? {
            if self == .bodyError { throw APIError.fail }
            return nil
        }
    }
    
    enum APIError: Swift.Error, LocalizedError {
        case fail
        var errorDescription: String? { "fail" }
    }
}
