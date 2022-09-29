////
////  AppTests.swift
////  AppTests
////
////  Created by lizhao on 2022/9/27.
////
//
//import XCTest 
//
//final class AppTests: XCTestCase {
//
//    override func setUpWithError() throws {
//        // Put setup code here. This method is called before the invocation of each test method in the class.
//    }
//
//    override func tearDownWithError() throws {
//        // Put teardown code here. This method is called after the invocation of each test method in the class.
//    }
//
//    func testExample() throws {
//        // This is an example of a functional test case.
//        // Use XCTAssert and related functions to verify your tests produce the correct results.
//        // Any test you write for XCTest can be annotated as throws and async.
//        // Mark your test throws to produce an unexpected failure when your test encounters an uncaught error.
//        // Mark your test async to allow awaiting for asynchronous code to complete. Check the results with assertions afterwards.
//    }
//
//    func testPerformanceExample() throws {
//        // This is an example of a performance test case.
//        measure {
//            // Put the code you want to measure the time of here.
//        }
//    }
//}
//
////
////struct AppState {
////    var appList = AppList(page: 1, apps: [])
////}
////
////extension AppState {
////    struct AppList {
////        var loadingList = false
////        var loadingListError: AppError?
////        var page: Int
////        var apps: [AppModelWrapper]
////        var canLoadMore = true
////        var loadmoreState: LoadMoreViewState = .hidden
////
////        // 更新点赞状态
////        func updateFavoriteState(id: Int, isFavorite: Bool) {
////            apps.forEach { model in
////                if model.app.trackId == id {
////                    model.isFavorite = isFavorite
////                }
////            }
////        }
////    }
////}
