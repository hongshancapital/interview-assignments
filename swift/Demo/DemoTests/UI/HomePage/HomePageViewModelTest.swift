//
//  HomePageViewModelTest.swift
//  DemoTests
//
//  Created by 葬花桥 on 2023/3/16.
//

import XCTest
@testable import Demo
import Combine

class MockDemoRestfulService: Mock, DemoRestfulService {
    
    enum Action: Equatable {
        case homePageList(limit: Int)
    }
    
    var actions: MockActions<Action>
    
    init(expected: [Action]) {
        self.actions = .init(expected: expected)
    }
    
    var expectedValue: Any?
    
    var response: [Application] = []
    
    /// 首页列表
    func homePageList(limit: Int) -> AnyPublisher<[Application], Error> {
        register(.homePageList(limit: limit))

        var publisher: AnyPublisher<[Application], Error>
        if let value = expectedValue as? [Application] {
            publisher = Just(value).setFailureType(to: Error.self).eraseToAnyPublisher()
        } else {
            publisher = Fail<[Application], Error>(error: MockError.valueNotSet).eraseToAnyPublisher()
        }
        
        return publisher
    }
    
    
    @MainActor
    func homePageList(limit: Int) async throws -> [Application] {
        register(.homePageList(limit: limit))
        
        return response
    }
}

final class HomePageViewModelTest: XCTestCase {
    var sut: HomePageView.ViewModel!
    var mockService: MockDemoRestfulService!
    let testKey = "https://www.test.com"
    let testImage = UIImage(systemName: "suit.heart.fill")!
    
    override func setUp() {
        super.setUp()
        mockService = MockDemoRestfulService(expected: [.homePageList(limit: 10)])
        mockService.expectedValue = testData()
        sut = HomePageView.ViewModel(service: mockService)
        
    }
   
    /// 首次请求测试
    func test_queryList() {
        let exp = XCTestExpectation(description: #function)

        let cancel = sut.$applications.weakSinkOn(self) {_,_ in
            
        } receiveValue: {
            if $1.value != nil {
                exp.fulfill()
            }
        }

        wait(for: [exp], timeout: 2)
        cancel.cancel()
        
        mockService.verify()
    }
    
    /// 加载更多测试
    func test_load_more() async throws {
        XCTAssertNotNil(sut.applications.value)
        mockService.expectedValue = testData() + testData()
        mockService.actions = .init(expected: [.homePageList(limit: 20)])
        
        sut.loadMore()
        
        for try await applications in sut.$applications
            .dropFirst().timeoutSequence(.milliseconds(3000), scheduler: DispatchQueue.main) {
            XCTAssertTrue((applications.value?.count ?? 0) == 20)
            break
        }
        
        mockService.verify()
    }
    
    /// 下拉刷新测试
    func test_refreshList() async throws {
        mockService = MockDemoRestfulService(expected: [.homePageList(limit: 10), .homePageList(limit: 10)])
        mockService.response = testData()
        sut = HomePageView.ViewModel(service: mockService)

                
        await sut.refreshList()
        
        for try await applications in sut.$applications
            .timeoutSequence(.milliseconds(3000), scheduler: DispatchQueue.main) {
            XCTAssertTrue((applications.value?.count ?? 0) == 10)
            break
        }
        
        
        mockService.verify()
    }
    
    /// 收藏测试
    func test_collectTapAction() async throws {
        for try await applications in sut.$applications
            .timeoutSequence(.milliseconds(3000), scheduler: DispatchQueue.main) {
            XCTAssertTrue((applications.value?.count ?? 0) == 10)
            break
        }
        
        let data = testData()
        
        sut.collectTapAction(application: data[0])
        XCTAssertTrue(sut.isFavourited(for: data[0]))
        
        sut.collectTapAction(application: data[0])
        XCTAssertFalse(sut.isFavourited(for: data[0]))
    }
    
    /// 是否最后一页测试
    func test_isLastPage() async throws {
        let data = testData()

        mockService = MockDemoRestfulService(expected: [.homePageList(limit: 10), .homePageList(limit: 20)])
        mockService.expectedValue = data
        sut = HomePageView.ViewModel(service: mockService)
        
        for try await applications in sut.$applications
            .timeoutSequence(.milliseconds(3000), scheduler: DispatchQueue.main) {
            XCTAssertTrue((applications.value?.count ?? 0) == 10)
            break
        }
        
        XCTAssertFalse(sut.isLastPage)
        
        mockService.expectedValue = data + data
        
        sut.loadMore()
        
        for try await applications in sut.$applications
            .dropFirst().timeoutSequence(.milliseconds(3000), scheduler: DispatchQueue.main) {
            XCTAssertTrue((applications.value?.count ?? 0) == 20)
            break
        }
        
        XCTAssertTrue(sut.isLastPage)
        
        mockService.verify()
    }
    
    func testData() -> [Application] {
        var applications = [Application]()
        for i in 0..<10 {
            applications.append(Application(trackName: "test\(i)", artworkUrl60: "png\(i)", description: "des\(i)"))
        }
        
        return applications
    }
}
