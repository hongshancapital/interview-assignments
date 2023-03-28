//
//  ItuneAppsTests.swift
//  ItuneAppsTests
//
//  Created by daicanglan on 2023/3/27.
//

@testable import ItuneApps
import XCTest
final class ItuneAppsTests: XCTestCase {
    let vm = AppViewModel()

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testAddLikeIdOrDeleteId() {
        vm.toggleLike(trackId: 100001)
        XCTAssertTrue(vm.likeAppIds.contains(100001), "包含100001")
        vm.toggleLike(trackId: 100001)
        XCTAssertFalse(vm.likeAppIds.contains(100001), "不包含100001")
    }

    func testLoadApps() {
        let expectation = self.expectation(description: "loadApps")
        var error: Error?
        var result: AppModel?
        let token = SubscriptionToken()
        LoadAppListRequest(baseUrl: "https://12313.com").publisher
            .sink { complete in
                switch complete {
                case let .failure(err):
                    error = err
                case .finished:
                    break
                }
                token.unseal()

                expectation.fulfill()
            } receiveValue: { value in
                result = value
            }
            .seal(in: token)
        waitForExpectations(timeout: 10)
        XCTAssertNotNil(error, "\(error!.localizedDescription)")
        XCTAssertNil(result)
//        debugPrint("tokenModel: \(tokenModel)")
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        measure {
            // Put the code you want to measure the time of here.
        }
    }
}
