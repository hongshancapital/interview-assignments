import XCTest
@testable import AppList

final class CardListViewModelTest: XCTestCase {
    
    var viewModel: CardListPageViewModel!
    var mockDataProvider = MockDataProvider()
    
    @MainActor
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
        Resolver.shared.add(DataProvider.self, component: mockDataProvider)
        viewModel = CardListPageViewModel()
    }
    
    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    @MainActor
    func testLoadDataWhenSuccess() async {
        mockDataProvider.shouldLoadSuccess = true
        await mockDataProvider.loadCardList()
        
        XCTAssertEqual(viewModel.pageState, .loading)
        XCTAssertEqual(viewModel.cardList.count, 0)
    }
    
    @MainActor
    func testLoadDataWhenFailed() async {
        mockDataProvider.shouldLoadSuccess = false
        await mockDataProvider.loadCardList()
        
        XCTAssertEqual(viewModel.pageState, .error)
    }
    
    @MainActor
    func testPullToLoadMoreWhenSuccess() async {
        mockDataProvider.shouldFetchSuccess = true
        let beforePull = viewModel.cardList.count
        await viewModel.pullToLoadMore()
        let afterPull = viewModel.cardList.count
        XCTAssertEqual(afterPull - beforePull, 15)
        XCTAssertFalse(viewModel.isLoadingMore)
        XCTAssertFalse(viewModel.loadMoreError)
    }
    
    @MainActor
    func testPullToLoadMoreWhenFailed() async {
        mockDataProvider.shouldFetchSuccess = false
        await viewModel.pullToLoadMore()

        XCTAssertTrue(viewModel.loadMoreError)
    }
    
    @MainActor
    func testPullToLoadMoreWhenNoMoreData() async {
        mockDataProvider.shouldFetchSuccess = true
        mockDataProvider.isNoMoreData = true
        await viewModel.pullToLoadMore()
        XCTAssertTrue(viewModel.noMoreData, "test load more when provider doesn't have any data")
        
        await viewModel.pullToLoadMore()
        XCTAssertTrue(viewModel.noMoreData, "test load more when pull action triggered and viewModel stored status is no more data")
    }
    
    @MainActor
    func testPullToRefreshWhenSuccess() async {
        mockDataProvider.shouldFetchSuccess = true
        await viewModel.pullToRefresh()
        
        XCTAssertEqual(viewModel.cardList.count, 15)
    }
    
    @MainActor
    func testPullToRefreshWhenFailed() async {
        mockDataProvider.shouldFetchSuccess = false
        await viewModel.pullToRefresh()
        
        XCTAssertTrue(viewModel.refreshError)
    }
    
}
