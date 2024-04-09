//
//  SequoiaDemoTests.swift
//  SequoiaDemoTests
//
//  Created by 王浩沣 on 2023/4/24.
//

import XCTest
@testable import SequoiaDemo

/*
 Store逻辑测试
 */

final class StoreUnitTest: XCTestCase {
    let store: Store = Store()
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
        store.appState = AppState()
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }


    ///测试store的dispatch方法
    func testStore_dispacth(){
        ///调用
        store.dispatch(.refreshData)
        ///检查是否符合预期
        XCTAssertEqual(store.appState.appDataLoadingState, .refreshing)
    }

    ///测试refreshData的AppAction时，appState的状态变化
    func testStoreReduce_refreshDataAction() throws {
        ///准备测试数据
        store.appState.error = NSError();
        ///调用
        let result = Store.reduce(state: store.appState, action: .refreshData)
        ///检查是否符合预期
        XCTAssertNil(result.0.error)
        XCTAssertNotNil(result.1)
        XCTAssertEqual(result.0.appDataLoadingState, .refreshing)
    }
    
    ///测试refreshDataDone的AppAction成功时，appState的状态变化
    func testStoreReduce_refreshDataDoneAction_success() throws {
        ///准备测试数据
        let testAppInfo = AppInfo.init(trackId: 100,
                                   trackName: "TestApp",
                                   description: "App For test",
                                   artworkUrl512: "")
        let testViewModel = AppInfoViewModel.init(with: testAppInfo)
        ///调用
        let result = Store.reduce(state: store.appState, action: .refreshDataDone(result: .success([testViewModel])))
        ///检查是否符合预期
        XCTAssertEqual(result.0.appInfoModels.count, 1)
        let viewModel = try XCTUnwrap(result.0.appInfoModels.first)
        XCTAssertEqual(testViewModel, viewModel)
    }
    
    ///测试refreshDataDone的AppAction失败时，appState的状态变化
    func testStoreReduce_refreshDataDoneAction_fail() throws {
        ///准备测试数据
        let testError = NSError(domain: "testError", code: -100)
        ///调用
        let result = Store.reduce(state: store.appState, action: .refreshDataDone(result: .failure(testError)))
        ///检查是否符合预期
        let error = try XCTUnwrap(result.0.error)
        XCTAssertEqual(error.localizedDescription, testError.localizedDescription)
   
    }
    
    ///测试loadNextPage的AppAction时，appState的状态变化
    func testStoreReduce_loadNextPageAction() throws{
        ///准备测试数据
        store.appState.error = NSError(domain: "testError", code: -100);
        ///调用
        let result = Store.reduce(state: store.appState, action: .loadNextPage)
        ///检查是否符合预期
        XCTAssertNil(result.0.error)
        XCTAssertNotNil(result.1)
        XCTAssertEqual(result.0.appDataLoadingState, .loadingNextPage)
    }
    
    ///测试loadNextPageDone的AppAction成功时，appState的状态变化
    func testStoreReduce_loadNextPageDoneAction_success() throws {
        ///准备测试数据
        let testAppInfo = AppInfo.init(trackId: 100,
                                   trackName: "TestApp",
                                   description: "App For test",
                                   artworkUrl512: "")
        let testViewModel = AppInfoViewModel.init(with: testAppInfo)
        ///调用
        let result = Store.reduce(state: store.appState, action: .loadNextPageDone(result: .success([testViewModel])))
        ///检查是否符合预期
        XCTAssertEqual(result.0.appInfoModels.count, 1)
        let viewModel = try XCTUnwrap(result.0.appInfoModels.first)
        XCTAssertEqual(testViewModel, viewModel)
    }
    
    ///测试loadNextPageDone的AppAction失败时，appState的状态变化
    func testStoreReduce_loadNextPageDoneAction_fail() throws {
        ///准备测试数据
        let testError = NSError(domain: "testError", code: -100)
        ///调用
        let result = Store.reduce(state: store.appState, action: .loadNextPageDone(result: .failure(testError)))
        ///检查是否符合预期
        let error = try XCTUnwrap(result.0.error)
        XCTAssertEqual(error.localizedDescription, testError.localizedDescription)
    }
    
    ///测试endOfData的AppAction时，appState的状态变化
    func testStoreReduce_endOfDataAction() throws {
        ///调用
        let result = Store.reduce(state: store.appState, action: .endOfData)
        ///检查是否符合预期
        XCTAssertNil(result.1)
        XCTAssertEqual(result.0.appDataLoadingState, .endOfData)
    }
    
    ///测试tapLike的AppAction时，appState的状态变化
    func testStoreReduce_tapLikeAction() throws {
        ///准备测试数据
        let testAppInfo = AppInfo.init(trackId: 100,
                                   trackName: "TestApp",
                                   description: "App For test",
                                   artworkUrl512: "")
        let testViewModel = AppInfoViewModel.init(with: testAppInfo)
        let beforeLikeState = testViewModel.isLiked
        store.appState.appInfoModels = [testViewModel]
        ///调用
        let result = Store.reduce(state: store.appState, action: .tapLike(appId: "100"))
        ///检查是否符合预期
        let viewModel = try XCTUnwrap(result.0.appInfoModels.first)
        XCTAssertEqual(viewModel.isLiked, !beforeLikeState)
    }


}
