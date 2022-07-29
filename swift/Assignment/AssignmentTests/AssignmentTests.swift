//
//  AssignmentTests.swift
//  AssignmentTests
//
//  Created by shinolr on 2022/7/27.
//

import XCTest
@testable import Assignment

final class AssignmentTests: XCTestCase {
  
  var sut: AppListViewModel!
  
  override func setUpWithError() throws {
    try super.setUpWithError()
    
    sut = AppListViewModel()
  }
  
  override func tearDownWithError() throws {
    sut = nil
    try super.tearDownWithError()
  }
  
  func testInitializer() {
    XCTAssertTrue(sut.items.isEmpty)
    XCTAssertTrue(sut.canLoadMore)
    XCTAssertFalse(sut.isLoading)
  }
  
  func testInitialLoad() async {
    XCTAssertEqual(sut.items.count, 0)
    await sut.loadData()
    XCTAssertEqual(sut.items.count, 10)
  }
  
  func testLoadMore() async {
    await sut.loadData()
    XCTAssertEqual(sut.items.count, 10)
    await sut.loadMoreIfNeeded(currentItem: sut.items.last!)
    XCTAssertEqual(sut.items.count, 20)
  }
  
  func testRefresh() async {
    await sut.loadData()
    await sut.loadMoreIfNeeded(currentItem: sut.items.last)
    XCTAssertEqual(sut.items.count, 20)
    await sut.refresh()
    XCTAssertEqual(sut.items.count, 10)
  }
  
  func testOnlyLastItemTriggersLoadMore() async {
    await sut.loadData()
    XCTAssertEqual(sut.items.count, 10)
    await sut.loadMoreIfNeeded(currentItem: sut.items[3])
    XCTAssertEqual(sut.items.count, 10)
    await sut.loadMoreIfNeeded(currentItem: sut.items.last)
    XCTAssertEqual(sut.items.count, 20)
  }

  func testFavorite() async {
    await sut.loadData()
    XCTAssertFalse(sut.items.last!.isFavorite)
    await sut.toggleFavorite(for: sut.items.last!)
    XCTAssertTrue(sut.items.last!.isFavorite)
    await sut.toggleFavorite(for: sut.items.last!)
    XCTAssertFalse(sut.items.last!.isFavorite)
  }
}
