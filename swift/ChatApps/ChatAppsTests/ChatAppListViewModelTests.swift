//
//  ChatAppListViewModelTests.swift
//  ChatAppListViewModelTests
//
//  Created by XuNing on 2022/7/27.
//

import XCTest

enum MockError: Error {
  case error1, error2
}

class ChatAppListViewModelTests: XCTestCase {
  
  var vm: ChatAppListViewModel!
  var mockAPI: TestsApi!
  
  override func setUp() {
    super.setUp()
    mockAPI = TestsApi()
    vm = ChatAppListViewModel(provider: mockAPI)
  }
  
  override func tearDown() {
    vm = nil
    mockAPI = nil
    super.tearDown()
  }

  func testRefreshSuccess() async {
    mockAPI.resultChatAppList = MockData.page1
    
    DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
      XCTAssertEqual(self.vm.state, .loading)
    }
    await vm.refresh()
    XCTAssertEqual(vm.state, .none)
    XCTAssertEqual(vm.apps.count, 10)
  }
  
  func testLoadError() async {
    mockAPI.resultError = MockError.error1
    
    await vm.refresh()
    XCTAssertEqual(vm.state, .error)
    XCTAssertEqual(vm.apps.count, 0)
  }
  
  func testEmptyList() async {
    mockAPI.resultChatAppList = ChatAppList(totalCount: 0, page: 3, results: [])
    
    await vm.refresh()
    XCTAssertEqual(vm.state, .empty)
    XCTAssertEqual(vm.apps.count, 0)
  }
  
  func testLoadMore() async {
    mockAPI.resultChatAppList = MockData.page1
    await vm.refresh()
    
    mockAPI.resultChatAppList = MockData.page2
    await vm.loadMore()
    XCTAssertEqual(vm.apps.count, 20)
  }
  
  func testHasMore() async {
    mockAPI.resultChatAppList = MockData.page1
    
    await vm.refresh()
    XCTAssertTrue(vm.hasMore)
  }
  
  func testFavoriteApp() async {
    mockAPI.resultChatAppList = MockData.page1
    await vm.refresh()
    
    let app = MockData.page1.results[1]
    
    await vm.favoriteApp(app.id, isFavorite: true)
    XCTAssertTrue(MockData.page1.results[1].isFavorite)
  }
  
  func testFavoriteAppFailure() async {
    mockAPI.resultChatAppList = MockData.page1
    await vm.refresh()
    
    let app = MockData.page1.results[1]
    
    mockAPI.resultError = MockError.error2
    await vm.favoriteApp(app.id, isFavorite: true)
    XCTAssertFalse(MockData.page1.results[1].isFavorite)
  }
  
}
