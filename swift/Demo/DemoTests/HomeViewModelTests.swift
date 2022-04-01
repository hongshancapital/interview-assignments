//
//  HomeViewModelTests.swift
//  DemoTests
//
//  Created by 杨立鹏 on 2022/3/30.
//

import XCTest

class HomeViewModelTests: XCTestCase {
    let vm = HomeViewModel()

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testHomeViewModelTestsRequestDataShouldBeSuccess() async throws {
        let vm = HomeViewModel()

        let result = try? await vm.requestData()

        XCTAssertNotNil(result)
        XCTAssertTrue(result?.results.count == result?.resultCount)
    }

    func testHomeViewModelTestsGetDataShouldBeSuccess() async throws {
        let testItems = DemoMockData.testItems

        await vm.getData(isRefreshData: true)

        let testItem1 = testItems.first!
        let requestItem1 = vm.items.first!

        XCTAssert(testItem1.trackId == requestItem1.trackId)
        XCTAssert(testItem1.trackCensoredName == requestItem1.trackCensoredName)
        XCTAssert(testItem1.description == requestItem1.description)
        XCTAssert(testItem1.artworkUrl60 == requestItem1.artworkUrl60)
    }

    func testHomeViewModelTestsGetDataPageableShouldBeSuccess() async throws {
        await vm.getData(isRefreshData: true)
        XCTAssertEqual(vm.pageNumber, 1)
        XCTAssertGreaterThanOrEqual(vm.pageNumber * vm.pageSize, vm.items.count)

        await vm.getData()
        XCTAssertEqual(vm.pageNumber, 2)
        XCTAssertGreaterThanOrEqual(vm.pageNumber * vm.pageSize, vm.items.count)

        await vm.getData()
        XCTAssertEqual(vm.pageNumber, 3)
        XCTAssertGreaterThanOrEqual(vm.pageNumber * vm.pageSize, vm.items.count)

        await vm.getData()
        XCTAssertEqual(vm.pageNumber, 3)
        XCTAssertGreaterThanOrEqual(vm.pageNumber * vm.pageSize, vm.items.count)

        await vm.getData(isRefreshData: true)
        XCTAssertEqual(vm.pageNumber, 1)
        XCTAssertGreaterThanOrEqual(vm.pageNumber * vm.pageSize, vm.items.count)
    }

    func testHomeViewModelTestsToggleLikeStateShouldBeSuccess() async throws {
        await vm.getData(isRefreshData: true)

        if let randomItem = vm.items.randomElement() {
            vm.toggleLikeState(item: randomItem)

            XCTAssertTrue(vm.likedItems.contains(randomItem))

            vm.toggleLikeState(item: randomItem)

            XCTAssertFalse(vm.likedItems.contains(randomItem))
        }
    }
}
