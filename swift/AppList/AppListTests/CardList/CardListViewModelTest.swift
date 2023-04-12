import XCTest
@testable import AppList

final class CardListViewModelTest: XCTestCase {
    
    var viewModel: CardListPageViewModel!
    var mockDataProvider = MockDataProvider()
    
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
        Resolver.shared.add(DataProvider.self, component: mockDataProvider)
        viewModel = CardListPageViewModel()
    }
    
    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    func testLoadDataWhenFailed() async {
        mockDataProvider.shouldFetchSuccess = false
        await viewModel.loadData()
        
        XCTAssertEqual(viewModel.cardList.count, 0)
        XCTAssertEqual(viewModel.pageState, .error)
        
    }
    
    func testLoadDataWhenSuccess() async {
        mockDataProvider.shouldLoadSuccess = true
        await viewModel.loadData()
        
        XCTAssertEqual(viewModel.cardList.count, 15)
        XCTAssertEqual(viewModel.pageState, .success)
    }
    
    func testPullToLoadMoreWhenSuccess() async {
        mockDataProvider.shouldFetchSuccess = true
        await viewModel.loadData()
        await viewModel.pullToLoadMore()
        
        XCTAssertEqual(viewModel.cardList.count, 30)
        XCTAssertFalse(viewModel.isLoadingMore)
        XCTAssertFalse(viewModel.loadMoreError)
    }
    
    func testPullToLoadMoreWhenFailed() async {
        mockDataProvider.shouldFetchSuccess = false
        await viewModel.loadData()
        await viewModel.pullToLoadMore()

        XCTAssertTrue(viewModel.loadMoreError)
    }
    
    func testPullToLoadMoreWhenNoMoreData() async {
        mockDataProvider.shouldFetchSuccess = true
        mockDataProvider.isNoMoreData = true
        await viewModel.loadData()
        await viewModel.pullToLoadMore()

        XCTAssertTrue(viewModel.noMoreData)
    }
    
    func testPullToRefreshWhenSuccess() async {
        mockDataProvider.shouldFetchSuccess = true
        await viewModel.loadData()
        await viewModel.pullToRefresh()
        
        XCTAssertEqual(viewModel.cardList.count, 15)
    }
    
    func testPullToRefreshWhenFailed() async {
        mockDataProvider.shouldFetchSuccess = false
        await viewModel.pullToRefresh()
        
        XCTAssertTrue(viewModel.refreshError)
    }
    
}
