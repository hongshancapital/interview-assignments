//
//  HSDemoTests.swift
//  HSDemoTests
//

//

import Combine
@testable import HSDemo
import XCTest

class HSDemoTests: XCTestCase {
    override func setUpWithError() throws {}

    override func tearDownWithError() throws {
        subscriptions = []
    }
    
    var subscriptions = Set<AnyCancellable>()
    
    func test_getting_artworks_success() {
        let result = Result<[ArtWork], APIError>.success([ArtWork.example1()])
        
        let fetcher = ArtWorkFetcher(service: APIMockService(result: result))
        
        let promise = expectation(description: "getting artworks")
        
        fetcher.$artWorkList.sink { breeds in
            if breeds.count > 0 {
                promise.fulfill()
            }
        }.store(in: &subscriptions)
        
        wait(for: [promise], timeout: 2)
    }
    
    func test_loading_error() {
        let result = Result<[ArtWork], APIError>.failure(APIError.badURL)
        let fetcher = ArtWorkFetcher(service: APIMockService(result: result))
         
        let promise = expectation(description: "show error message")
        fetcher.$artWorkList.sink { result in
            if !result.isEmpty {
                XCTFail()
            }
        }.store(in: &subscriptions)
        
        fetcher.$errorMessage.sink { message in
            if message != nil {
                promise.fulfill()
            }
        }.store(in: &subscriptions)
        
        wait(for: [promise], timeout: 2)
    }
}
