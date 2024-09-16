//
//  AppHunterTests.swift
//  AppHunterTests
//
//  Created by zhang shijie on 2023/5/24.
//

@testable import AppHunter
import Combine
import XCTest

final class AppHunterTests: XCTestCase {
    private var cancellables = Set<AnyCancellable>()
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testNotFoundJson() throws {
        var service = MockAppInfoListService()
        service.jsonName = "not real name"
        let cancellable = service.requestApps(from: .appInfoList(page: 0)).sink { res in
            switch res {
            case .finished:
                XCTAssert(false)
            case .failure:
                XCTAssertTrue(true)
            }
        } receiveValue: { res in
            XCTAssertNil(res.results)
        }
        cancellables.insert(cancellable)
    }

    func testDecodeJsonfailed() throws {
        var service = MockAppInfoListService()
        service.jsonName = "res_error"
        let cancellable = service.requestApps(from: .appInfoList(page: 0)).sink { res in
            switch res {
            case .finished:
                XCTAssert(false)
            case .failure:
                XCTAssertTrue(true)
            }
        } receiveValue: { res in
            XCTAssertNil(res.results)
        }
        cancellables.insert(cancellable)
    }

    func testValidJson() throws {
        var service = MockAppInfoListService()
        service.jsonName = "res"
        let cancellable = service.requestApps(from: .appInfoList(page: 0)).sink { res in
            switch res {
            case .finished:
                XCTAssert(true)
            case .failure:
                XCTAssertTrue(false)
            }
        } receiveValue: { res in
            XCTAssertNotNil(res.results)
        }
        cancellables.insert(cancellable)
    }

    func testLoadMoreInvalidPage() throws {
        var service = MockAppInfoListService()
        service.jsonName = "res"
        let cancellable = service.requestApps(from: .appInfoList(page: 100)).sink { res in
            switch res {
            case .finished:
                XCTAssert(true)
            case .failure:
                XCTAssertTrue(false)
            }
        } receiveValue: { res in
            XCTAssertNil(res.results)
        }
        cancellables.insert(cancellable)
    }

    func testLoadMoreInvalidPage2() throws {
        var service = MockAppInfoListService()
        service.jsonName = "res"
        let cancellable = service.requestApps(from: .appInfoList(page: -100)).sink { res in
            switch res {
            case .finished:
                XCTAssert(true)
            case .failure:
                XCTAssertTrue(false)
            }
        } receiveValue: { res in
            XCTAssertNil(res.results)
        }
        cancellables.insert(cancellable)
    }

    func testClickCollectBtn() throws {
        var service = MockAppInfoListService()
        var viewModel = HomeViewModel(apiService: service)
        viewModel.addCollection(item: service.mockCollectAppInfo)
        XCTAssertTrue(viewModel.collectAppTrackIds.contains(service.mockCollectAppInfo.id))
    }

    func testCancelCollecBtn() throws {
        var service = MockAppInfoListService()
        var viewModel = HomeViewModel(apiService: service)
        viewModel.addCollection(item: service.mockCollectAppInfo)
        XCTAssertTrue(viewModel.collectAppTrackIds.contains(service.mockCollectAppInfo.id))
        viewModel.addCollection(item: service.mockCollectAppInfo)
        XCTAssertFalse(viewModel.collectAppTrackIds.contains(service.mockCollectAppInfo.id))
    }

    func testExample() throws {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
        // Any test you write for XCTest can be annotated as throws and async.
        // Mark your test throws to produce an unexpected failure when your test encounters an uncaught error.
        // Mark your test async to allow awaiting for asynchronous code to complete.
        // Check the results with assertions afterwards.
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        measure {
            // Put the code you want to measure the time of here.
        }
    }
}
