//
//  AppListTests.swift
//  AppListTests
//
//  Created by jay on 2022/2/22.
//

import XCTest
@testable import AppList

class AppListTests: XCTestCase {
    
    var page1: Page!
    var page2: Page!
    var page3: Page!
    var dataLoader: DataLoader!
    var decoder: Decoder!
    
    func testPage() throws {
        XCTAssertEqual(page1.totalPage, 9)
        XCTAssertEqual(page3.totalPage, 0)
        
        XCTAssertEqual(page1.nextPage, 5)
        
        XCTAssertEqual(page1.range, 30..<40)
        XCTAssertEqual(page2.range, 80..<83)
        XCTAssertEqual(page3.range, nil)
        
        XCTAssertEqual(page1.rangeOfAll, 0..<83)
        XCTAssertEqual(page2.rangeOfAll, 0..<83)
        XCTAssertEqual(page3.rangeOfAll, nil)
        
        XCTAssertEqual(page1.page(of: 82), 9)
        XCTAssertEqual(page3.page(of: 82), nil)
        
        XCTAssertEqual(page1.range(for: 9), 80..<83)
        XCTAssertEqual(page3.range(for: 9), nil)
        
        XCTAssertEqual(page1.range(of: 33), 30..<40)
        XCTAssertEqual(page1.range(of: 82), 80..<83)
        XCTAssertEqual(page3.range(of: 82), nil)
    }
    
    
    /// getData(of:) 依赖于 DataLoader 中的其他方法，所以测这个就可以了
    func testDataLoader() throws {
        let page = Page(total: 50)
        let tuple = dataLoader.getData(of: page)
        let total = tuple.total
        let item = tuple.list.first
        XCTAssertEqual(total, 50)
        XCTAssertEqual(item?.title ?? "", "Google Chat")
    }
    
    func testHomeViewModel() throws {
        testGetData()
        testLoadMore()
        testReload()
    }
    
    func testDataFetching(viewModel: HomeViewModel, method: () -> (), assertDataCount: Int) {
        let predicate = NSPredicate() { _,_ in
            viewModel.listDatas.count == assertDataCount
        }
        let expectation = XCTNSPredicateExpectation(predicate: predicate, object: viewModel)
        method()
        let result = XCTWaiter().wait(for: [expectation], timeout: 3.0)
        switch result {
        case .completed:
            XCTAssertEqual(viewModel.listDatas.count, assertDataCount)
            XCTAssertEqual(viewModel.page?.total, 50)

        default:
            XCTFail()
        }
    }

    func testGetData() {
        let homeViewModel = HomeViewModel()
        testDataFetching(viewModel: homeViewModel, method: homeViewModel.getData, assertDataCount: 10)
    }
    
    func testLoadMore() {
        let homeViewModel = HomeViewModel()
        let defaultPage = Page(total: 50, page: 1, perPage: 20)
        let defaultDatas = DataLoader().getData(of: defaultPage)
        homeViewModel.listDatas = defaultDatas.list
        homeViewModel.page = Page(total: 50, page: 2, perPage: 10)
        testDataFetching(viewModel: homeViewModel, method: homeViewModel.getData, assertDataCount: 30)
    }
    
    func testReload() {
        let homeViewModel = HomeViewModel()
        let defaultPage = Page(total: 50, page: 1, perPage: 20)
        let defaultDatas = DataLoader().getData(of: defaultPage)
        homeViewModel.listDatas = defaultDatas.list
        homeViewModel.page = Page(total: 50, page: 2, perPage: 10)
        testDataFetching(viewModel: homeViewModel, method: homeViewModel.reload, assertDataCount: 10)
    }
    
    override func setUpWithError() throws {
        page1 = Page(total: 83, page: 4, perPage: 10)
        page2 = Page(total: 83, page: 9, perPage: 10)
        page3 = Page(total: 0)
        
        dataLoader = DataLoader()
        
        decoder = Decoder()
    }
    
    override func tearDownWithError() throws {
        page1 = nil
        page2 = nil
        
        dataLoader = nil
        
        decoder = nil
    }
}
