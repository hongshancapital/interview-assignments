//
//  SwiftUIDemoTests.swift
//  SwiftUIDemoTests
//
//  Created by banban on 2022/5/16.
//

import XCTest
@testable import SwiftUIDemo

class SwiftUIDemoTests: XCTestCase {
    
    var _viewModel : ListViewModel?

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
        _viewModel = ListViewModel()
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
    
    /// 测试ListViewModel下拉刷新数据
    func testViewModelRefreshData() throws {
        
        if let viewModel = _viewModel {
            let expect1 = XCTestExpectation(description: "onRefreshSuccess")
            viewModel.onRefresh()
            DispatchQueue.main.asyncAfter(deadline: .now() + 2.0, execute: {
                expect1.fulfill()
            })
            let result = XCTWaiter(delegate: self).wait(for: [expect1], timeout: 5.0)
            switch result {
            case .completed:
                /// 判断数据不为空
                XCTAssertNotNil(viewModel.itemModels)
                ///  判断数组长度大于0
                XCTAssertGreaterThan(viewModel.itemModels.count , 0)
                ///  判断数组长度固定为15
                XCTAssertEqual(viewModel.itemModels.count, 15)
                /// 判断转换的ListItemModel中是否缺少数据
                viewModel.itemModels.forEach({ model in
                    XCTAssertNotNil(model.trackId)
                    XCTAssertNotNil(model.title)
                    XCTAssertNotNil(model.description)
                    XCTAssertNotNil(model.icon)
                })
            default :
                XCTFail()
            }
        } else {
            XCTFail()
        }
    }
    
    /// 测试ListViewModel上拉加载更多数据
    func testViewModelLoadMoreData() throws {
        
        if let viewModel = _viewModel {
            let expect1 = XCTestExpectation(description: "onRefreshSuccess")
            viewModel.onRefresh()
            DispatchQueue.main.asyncAfter(deadline: .now() + 2.0, execute: {
                expect1.fulfill()
            })
            let result = XCTWaiter(delegate: self).wait(for: [expect1], timeout: 5.0)
            switch result {
            case .completed:
                
                let expect2 = XCTestExpectation(description: "onLoadMoreDataSuccess")
                viewModel.loadMoreData()
                DispatchQueue.main.asyncAfter(deadline: .now() + 2.0, execute: {
                    expect2.fulfill()
                })
                let result2 = XCTWaiter(delegate: self).wait(for: [expect2], timeout: 5.0)
                
                switch result2 {
                case .completed:
                    /// 判断数组数量是否超过15个
                    XCTAssertGreaterThan(viewModel.itemModels.count , 15)
                default:
                    XCTFail()
                }
            default :
                XCTFail()
            }
        } else {
            XCTFail()
        }
    }
    
    /// 测试点击喜欢按钮点亮或取消点亮
    func testClickLikeButton() {
        if let viewModel = _viewModel {
            var testItemModel = ListItemModel(trackId: 1234567, title: "TestItem1", description: "TestItem1Description", icon: "")
            viewModel.itemModels.append(testItemModel)
            viewModel.onClickItemLikeButton(testItemModel)
            /// 测试按钮点亮
            XCTAssertTrue(viewModel.itemModels.first?.isLike ?? false)
            /// 测试按钮取消点亮
            testItemModel = viewModel.itemModels.first ?? ListItemModel(trackId: 1234567, title: "TestItem1", description: "TestItem1Description", icon: "",isLike: true);
            viewModel.onClickItemLikeButton(testItemModel)
            XCTAssertFalse(viewModel.itemModels.first?.isLike ?? true)
        } else {
            XCTFail()
        }
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
