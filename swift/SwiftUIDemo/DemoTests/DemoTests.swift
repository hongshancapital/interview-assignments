//
//  DemoTests.swift
//  DemoTests
//
//  Created by 石超 on 2022/6/22.
//

import XCTest
@testable import Demo

class DemoTests: XCTestCase {
    
    var modelData = ModelData()
    
    // MARK: - 测试Mock数据是否成功
    func testMockData() throws {
        XCTAssertNotNil(modelData.results?.results, "Mock数据为空，加载失败")
        XCTAssertEqual(modelData.results!.results!.count, 50, "Mock数据未完全加载")
    }

    // MARK: - 测试首页数据
    func testLoadFirstPageData() throws {
        DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) {
            self.modelData.apps = self.modelData.getApps()
            XCTAssertEqual(self.modelData.apps.count, self.modelData.pageCount * self.modelData.pageIndex, "首页数据加载失败")
        }
    }
    
    // MARK: - 测试加载更多
    func testLoadMoreData() throws {
        
        XCTAssertFalse(modelData.isRefreshing, "正在刷新数据...")
        XCTAssertFalse(modelData.isNoMoreData, "已加全部数据...")
        
        self.modelData.isLoading = true
        self.modelData.pageIndex += 1
        
        DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) {
            self.modelData.apps = self.modelData.getApps()
            XCTAssertEqual(self.modelData.apps.count, 50, "数据加载更多失败")
            XCTAssertEqual(self.modelData.apps.count, self.modelData.pageCount * self.modelData.pageIndex, "数据加载更多失败")
            
            XCTAssertTrue(self.modelData.isNoMoreData, "数据未全部加载...")
            XCTAssertFalse(self.modelData.isLoading, "全部加载完成...")
        }
    }
    
    // MARK: - 测试刷新页面
    func testRefreshData() throws {
        
        XCTAssertFalse(modelData.isRefreshing, "正在刷新数据...")
        XCTAssertFalse(modelData.isLoading, "正在加载数据...")
        
        self.modelData.isRefreshing = true
        self.modelData.pageIndex = 1
        
        DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) {
            self.modelData.apps = self.modelData.getApps()
            XCTAssertEqual(self.modelData.apps.count, self.modelData.pageCount, "页面刷新失败")
            
            XCTAssertFalse(self.modelData.isNoMoreData, "页面刷新失败...")
            XCTAssertFalse(self.modelData.isRefreshing, "刷新页面完成...")
        }
    }
}
