//
//  AppListTests.swift
//  AppListTests
//
//  Created by 大洋 on 2022/8/21.
//

import XCTest
@testable import AppList

class AppListTests: XCTestCase {

    var viewModel: AppListViewModel?
    
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
        try super.setUpWithError()
        viewModel = AppListViewModel()

    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        try super.tearDownWithError()
        viewModel = nil
    }

    // MARK: - AppListFile, AppListModel
    func testAppListFileLoad() async throws {
        let model: AppModel = try await AppListFile.loadJSON(filename: "appList-1.json")
        XCTAssertEqual(model.resultCount, model.results.count, "解析获取数据与源数据不相等")
    }
    
    func testAppListFileEmpty() async throws {
        let model: AppModel = try await AppListFile.loadJSON(filename: "appList.json")
        XCTAssertEqual(model.resultCount, model.results.count, "解析获取数据与源数据相等")
        if let listModel = model.results.last {
            XCTAssertNotNil(listModel.description, "description 为空")
        }
    }
    
    func testAppListFileLoadFile() async throws {
        do {
            let model: AppModel = try await AppListFile.loadJSON(filename: "applist.json")
            XCTAssertEqual(model.resultCount, model.results.count, "解析获取数据与源数据不相等")
        } catch let error {
            XCTAssertNotNil(error as! LoadError, "错误类型返回错误")
        }
    }
    
    
    // MARK: - AppListViewModel
    func testAppListViewModel() async {
        guard let vm = viewModel else { return }
        XCTAssertTrue(vm.settings.loadState == .none, "初始化状态错误")
        
        vm.loadAppList()
        // 异步加载, 状态不变
        XCTAssertTrue(vm.settings.loadState == .none, "异步加载, 状态已改变")
        XCTAssertTrue(vm.appList.isEmpty, "异步加载, 数据已改变")
    }
    
    func testAsyncAppListViewModel() async {
        guard let vm = viewModel else { return }
        // 异步加载测试
        let loadExpectation = expectation(description: "加载数据")
        loadExpectation.isInverted = true
        vm.testRequestListData(1) { [weak vm] in
            print(vm?.appList.count as Any)
            if let vm = vm {
                XCTAssertTrue(vm.settings.loadState == .refresh, "异步加载, 状态未改变")
                XCTAssertTrue(!vm.appList.isEmpty, "异步加载, 数据未改变")
            }
            loadExpectation.fulfill()
        }
        
        await waitForExpectations(timeout: 2, handler: { error in
            guard let error = error else { return }
            print(error.localizedDescription)
        })
    }
    
    func testAsyncMoreAppListViewModel() async {
        guard let vm = viewModel else { return }

        
        guard let model: AppModel = try! await AppListFile.loadJSON(filename: "appList-1.json") else { return }
        vm.appList += model.results
        vm.settings.loadState = .refresh
        
        // 异步加载测试(错误文件)
        let loadMoreExpectation = expectation(description: "加载数据更多")
        loadMoreExpectation.isInverted = true
        vm.testRequestListData(6) { [weak vm] in
            if let vm = vm {
                XCTAssertTrue(vm.settings.loadState == .noMoreData, "异步加载, 状态错误")
            }
            loadMoreExpectation.fulfill()
        }
        
        await waitForExpectations(timeout: 2, handler: { error in
            guard let error = error else { return }
            print(error.localizedDescription)
        })
    }
    
    func testAsyncErrorAppListViewModel() async {
        guard let vm = viewModel else { return }
        // 异步加载测试(错误文件)
        let loadErrorExpectation = expectation(description: "加载数据(错误文件)")
        loadErrorExpectation.isInverted = true
        vm.testRequestListData(6) { [weak vm] in
            if let vm = vm {
                XCTAssertTrue(vm.settings.loadState == .loadError, "异步加载, 状态错误")
                XCTAssertTrue(vm.appList.isEmpty, "异步加载, 数据错误")
            }
            loadErrorExpectation.fulfill()
        }
        
        await waitForExpectations(timeout: 2, handler: { error in
            guard let error = error else { return }
            print(error.localizedDescription)
        })
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
