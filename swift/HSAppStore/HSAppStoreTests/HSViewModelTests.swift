//
//  HSViewModelTests.swift
//  HSAppStoreTests
//
//  Created by Sheng ma on 2022/5/25.
//

import XCTest
@testable import HSAppStore

@MainActor
class HSViewModelTests: XCTestCase {

    let viewModel = HSAppViewModel()
    
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testGetData() async throws {
        XCTAssert(viewModel.modelList.count == 0)
        
        // getData
        await viewModel.getAppData()
        XCTAssert(viewModel.modelList.count > 0)
        XCTAssert(!viewModel.hasError)
        XCTAssertNil(viewModel.errorMessage)
    }
    
    func testLoadMore() async throws {
        XCTAssert(viewModel.modelList.count == 0)
        
        //getData
        await viewModel.getAppData()
        let dataCount = viewModel.modelList.count
        XCTAssert(dataCount > 0)

        //loadMore
        await viewModel.loadMoreData()
        XCTAssert(viewModel.modelList.count > dataCount)
    }
    
    func testLikeApp() async throws {
        XCTAssert(viewModel.modelList.count == 0)
        await viewModel.getAppData()
        
        //getData
        XCTAssert(viewModel.modelList.count > 0)
        let id = viewModel.modelList.first!.id
        
        //click like
        viewModel.likeApp(id: id)
        XCTAssert(viewModel.modelList.first!.isFavorite)

        //cacel like
        viewModel.likeApp(id: id)
        XCTAssert(!viewModel.modelList.first!.isFavorite)
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

}
