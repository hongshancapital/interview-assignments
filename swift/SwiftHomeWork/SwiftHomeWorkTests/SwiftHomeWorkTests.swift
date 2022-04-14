//
//  SwiftHomeWorkTests.swift
//  SwiftHomeWorkTests
//
//  Created by apple on 2022/4/12.
//

import XCTest
@testable import SwiftHomeWork

class SwiftHomeWorkTests: XCTestCase {

    let service =  MockNetService.init()

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testNetworkAPIRefresh() throws {
        let e = self.expectation(description: "api-refresh")
        Task {
            do {
                let items = try await service.refresh()
                XCTAssertFalse(items.isEmpty)
            } catch {
                print(error)
                XCTFail()
            }
            e.fulfill()
        }
        self.wait(for: [e], timeout: 10)
    }

    func testNetworkAPILoadMore() throws {
        let e = self.expectation(description: "api-loadmore")
        Task {
            do {
                let items = try await service.loadMore()
                XCTAssertFalse(items.isEmpty)
            } catch {
                print(error)
                XCTFail()
            }
            e.fulfill()
        }
        self.wait(for: [e], timeout: 10)
    }

    @MainActor func testLike() throws {
        let vm = DataViewModel.init(MockNetService())
        let mockEntity = try mockResult()
        vm.apps = [mockEntity]

        vm.$apps.sink { entities in
            XCTAssertEqual(entities.count, 1)
            XCTAssertFalse(entities.first!.favorite)
        }.cancel()

        vm.like(entity: mockEntity, favorite: true)
        vm.$apps.sink { entities in
            XCTAssertEqual(entities.count, 1)
            XCTAssertTrue(entities.first!.favorite)
        }.cancel()

        vm.like(entity: mockEntity, favorite: false)
        vm.$apps.sink { entities in
            XCTAssertEqual(entities.count, 1)
            XCTAssertFalse(entities.first!.favorite)
        }.cancel()
    }

}
