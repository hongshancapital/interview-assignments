//
//  SocialAppListSwiftUITests.swift
//  SocialAppListSwiftUITests
//
//  Created by luobin on 2022/3/25.
//

import XCTest
@testable import SocialAppListSwiftUI
import Combine

@MainActor
class SocialAppListViewModelTests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    func testAppListEmpty() {
        let viewModel = SocialAppListViewModel()
        XCTAssert(viewModel.appList.isEmpty)
    }
    
    func testRefreshingState() async {
        let viewModel = SocialAppListViewModel()
        XCTAssert(viewModel.refreshState == .unintialized)
        XCTAssert(viewModel.loadingMoreState == .unintialized)

        var guessState: SocialAppListViewModel.LoadingState = .unintialized
        viewModel.$refreshState.subscribe(Subscribers.Sink<SocialAppListViewModel.LoadingState, Never>(receiveCompletion: { _ in
            
        }, receiveValue: { value in
            XCTAssertEqual(value, guessState)
            if guessState == .unintialized {
                guessState = .loading
            } else if guessState == .loading {
                guessState = .succeed
            }
        }))
        
        await viewModel.refresh()
        
        XCTAssertNotEqual(viewModel.refreshState, .unintialized)
        XCTAssertNotEqual(viewModel.refreshState, .loading)
        XCTAssert(viewModel.loadingMoreState == .unintialized)
    }
    
    func testLoadingMoreState() async {
        let viewModel = SocialAppListViewModel()
        XCTAssert(viewModel.refreshState == .unintialized)
        XCTAssert(viewModel.loadingMoreState == .unintialized)

        var guessState: SocialAppListViewModel.LoadingState = .unintialized
        
        let subscriber = Subscribers.Sink<SocialAppListViewModel.LoadingState, Never>(receiveCompletion: { _ in
            
        }, receiveValue: { value in
            XCTAssertEqual(value, guessState)
            if guessState == .unintialized {
                guessState = .loading
            } else if guessState == .loading {
                guessState = .succeed
            }
        })
        viewModel.$loadingMoreState.subscribe(subscriber)
        
        XCTAssert(viewModel.loadingMoreState == .unintialized)
        await viewModel.loadMore()
        XCTAssert(viewModel.loadingMoreState == .unintialized)
        
        await viewModel.refresh()
        
        if viewModel.refreshState == .succeed {
            XCTAssert(viewModel.loadingMoreState == .unintialized)
            
            await viewModel.loadMore()
            
            XCTAssertNotEqual(viewModel.loadingMoreState, .unintialized)
            XCTAssertNotEqual(viewModel.loadingMoreState, .loading)
            
            subscriber.cancel()

            await viewModel.loadMore()
            
            XCTAssertNotEqual(viewModel.loadingMoreState, .unintialized)
            XCTAssertNotEqual(viewModel.loadingMoreState, .loading)
        }
    }


    func testRefresh() async throws {
        let viewModel = SocialAppListViewModel()
        await viewModel.refresh()
        XCTAssertLessThanOrEqual(viewModel.appList.count, viewModel.pageSize)
        await viewModel.refresh()
        await viewModel.refresh()
        await viewModel.refresh()
        XCTAssertLessThanOrEqual(viewModel.appList.count, viewModel.pageSize)
    }
    
    func testLoadMore() async throws {
        let viewModel = SocialAppListViewModel()
        
        await viewModel.loadMore()
        XCTAssertEqual(viewModel.appList.count, 0)
        
        await viewModel.refresh()
        XCTAssertLessThanOrEqual(viewModel.appList.count, viewModel.pageSize)

        if viewModel.isHasMore {
            await viewModel.loadMore()
            XCTAssertLessThanOrEqual(viewModel.appList.count, viewModel.pageSize * 2)
            XCTAssertGreaterThanOrEqual(viewModel.appList.count, viewModel.pageSize)
        } else {
            let count = viewModel.appList.count
            await viewModel.loadMore()
            XCTAssertEqual(viewModel.appList.count, count)
        }
        
        if viewModel.isHasMore {
            await viewModel.loadMore()
            XCTAssertLessThanOrEqual(viewModel.appList.count, viewModel.pageSize * 3)
            XCTAssertGreaterThanOrEqual(viewModel.appList.count, viewModel.pageSize)
        } else {
            let count = viewModel.appList.count
            await viewModel.loadMore()
            XCTAssertEqual(viewModel.appList.count, count)
        }
        
        if viewModel.isHasMore {
            await viewModel.loadMore()
            XCTAssertLessThanOrEqual(viewModel.appList.count, viewModel.pageSize * 4)
            XCTAssertGreaterThanOrEqual(viewModel.appList.count, viewModel.pageSize)
        } else {
            let count = viewModel.appList.count
            await viewModel.loadMore()
            XCTAssertEqual(viewModel.appList.count, count)
        }
        
        if viewModel.isHasMore {
            await viewModel.loadMore()
            XCTAssertLessThanOrEqual(viewModel.appList.count, viewModel.pageSize * 5)
            XCTAssertGreaterThanOrEqual(viewModel.appList.count, viewModel.pageSize)
        } else {
            let count = viewModel.appList.count
            await viewModel.loadMore()
            XCTAssertEqual(viewModel.appList.count, count)
        }
        
        if viewModel.isHasMore {
            await viewModel.loadMore()
            XCTAssertLessThanOrEqual(viewModel.appList.count, viewModel.pageSize * 6)
            XCTAssertGreaterThanOrEqual(viewModel.appList.count, viewModel.pageSize)
        } else {
            let count = viewModel.appList.count
            await viewModel.loadMore()
            XCTAssertEqual(viewModel.appList.count, count)
        }

    }
    
    func testRefreshAfterLoadMore() async throws {
        let viewModel = SocialAppListViewModel()
        await viewModel.refresh()
        XCTAssertLessThanOrEqual(viewModel.appList.count, viewModel.pageSize)
        
        if viewModel.isHasMore {
            await viewModel.loadMore()
            XCTAssertLessThanOrEqual(viewModel.appList.count, viewModel.pageSize * 5)
            XCTAssertGreaterThanOrEqual(viewModel.appList.count, viewModel.pageSize)
            
            await viewModel.refresh()
            XCTAssertEqual(viewModel.appList.count, viewModel.pageSize)
        }
    }
    
    func testPageSize() async {
        var viewModel = SocialAppListViewModel(pageSize: 20)
        await viewModel.refresh()
        XCTAssertLessThanOrEqual(viewModel.appList.count, viewModel.pageSize)
        
        if viewModel.isHasMore {
            await viewModel.loadMore()
            XCTAssertLessThanOrEqual(viewModel.appList.count, viewModel.pageSize * 5)
            XCTAssertGreaterThanOrEqual(viewModel.appList.count, viewModel.pageSize)
            
            await viewModel.refresh()
            XCTAssertEqual(viewModel.appList.count, viewModel.pageSize)
        }
        
        viewModel = SocialAppListViewModel(pageSize: 30)
        await viewModel.refresh()
        XCTAssertLessThanOrEqual(viewModel.appList.count, viewModel.pageSize)
        
        if viewModel.isHasMore {
            await viewModel.loadMore()
            XCTAssertLessThanOrEqual(viewModel.appList.count, viewModel.pageSize * 5)
            XCTAssertGreaterThanOrEqual(viewModel.appList.count, viewModel.pageSize)
            
            await viewModel.refresh()
            XCTAssertEqual(viewModel.appList.count, viewModel.pageSize)
        }

    }
    
    // MARK - Private
    private func constructAppModel() -> SocialAppModel {
        let url = URL(string: "https://is5-ssl.mzstatic.com/image/source/100x100bb.jpg")!
        let model = SocialAppModel(trackName: "LoopChat: College Chats+Social", description: "LoopChat is the #1 plug for all things college: find your class chats", artworkUrl60: url)
        return model
    }

}

