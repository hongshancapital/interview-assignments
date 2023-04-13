//
//  AppListViewModel_Tests.swift
//  APPListTests
//
//  Created by three on 2023/4/12.
//

import XCTest
import APPList
import Combine

final class AppListViewModel_Tests: XCTestCase {
    
    var cancellables = Set<AnyCancellable>()

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testExample() throws {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
        // Any test you write for XCTest can be annotated as throws and async.
        // Mark your test throws to produce an unexpected failure when your test encounters an uncaught error.
        // Mark your test async to allow awaiting for asynchronous code to complete. Check the results with assertions afterwards.
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }
    
    func test_LoadDataByCombine_DoseSuccess() {
        let vm = AppListViewModel()
        
        var apps: [AppModel] = []
        let expectation = XCTestExpectation(description: "Should return items after a second.")
        vm.appListPublisher(pageId: 0, perPage: 10)
            .receive(on: DispatchQueue.main)
            .sink { statue in
                switch statue {
                case .finished:
                    expectation.fulfill()
                case .failure:
                    XCTFail()
                }
                
            } receiveValue: { response in
                apps.append(contentsOf: response.results)
            }
            .store(in: &cancellables)

        
        wait(for: [expectation], timeout: 3)
        XCTAssertGreaterThan(apps.count, 0)
    }
    
    func test_LoadDataByCombine_noData() {
        let vm = AppListViewModel()
        
        var apps: [AppModel] = []
        let expectation = XCTestExpectation(description: "Should return items after a second.")
        vm.appListPublisher(pageId: 6, perPage: 10)
            .receive(on: DispatchQueue.main)
            .sink { statue in
                switch statue {
                case .finished:
                    expectation.fulfill()
                case .failure:
                    XCTFail()
                }
                
            } receiveValue: { response in
                apps.append(contentsOf: response.results)
            }
            .store(in: &cancellables)

        
        wait(for: [expectation], timeout: 3)
        XCTAssertGreaterThanOrEqual(apps.count, 0)
    }
    
    func test_ReloadData_Success() {
        let vm = AppListViewModel()
        let expectation = XCTestExpectation(description: "Should return items after a second.")
        
        vm.$apps
            .dropFirst()
            .sink { apps in
                expectation.fulfill()
            }
            .store(in: &cancellables)
        
        vm.reloadData()
        
        wait(for: [expectation], timeout: 3)
        XCTAssertGreaterThan(vm.apps.count, 0)
    }
    
    func test_LoadMoreData_Success() {
        let vm = AppListViewModel()
        let expectation = XCTestExpectation(description: "Should return items after a second.")
        
        vm.$apps
            .dropFirst()
            .sink { apps in
                expectation.fulfill()
            }
            .store(in: &cancellables)
        
        vm.loadMoreData()
        
        wait(for: [expectation], timeout: 3)
        XCTAssertGreaterThan(vm.apps.count, 0)
    }
    
    func test_UpdateFavourite_Success() {
        let vm = AppListViewModel()
        let trackId = 12345
        let favSet = LocalStorage().loadFavs()
        
        let flag = favSet.contains(trackId)
        
        vm.updateFavourite(trackId: trackId)
        
        XCTAssertEqual(LocalStorage().loadFavs().contains(trackId), !flag)
    }

}
