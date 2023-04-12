import Foundation

class CardListPageViewModel: ObservableObject {
    @Published
    private(set)var cardList: [CardViewModel] = []
    
    @Published
    private(set)var pageState: PageState = .loading
    
    @Published
    private(set) var isLoadingMore = false
    
    private(set) var loadMoreError = false
    private(set) var refreshError = false
    private(set) var noMoreData = false
    
    private var pageNumber = 0
    private let stride = 15
    
    @Injection
    private var dataProvider: DataProvider
    
    @MainActor
    func loadData() async {
        do {
            self.cardList = try await dataProvider.fetchData(from: 0, stride: stride)
            self.pageState = .success
        } catch {
            pageState = .error
        }
    }
    
    @MainActor
    func pullToRefresh() async {
        pageNumber = 0
        do {
            cardList = try await dataProvider.fetchData(from: 0, stride: stride)
            noMoreData = false
        } catch {
            refreshError = true
        }
    }
    
    @MainActor
    func pullToLoadMore() async {
        if isLoadingMore || noMoreData {
            return
        }
        pageNumber += 1
        isLoadingMore = true
        do {
            let moreData = try await dataProvider.fetchData(from: pageNumber * stride, stride: stride)
            if moreData.isEmpty {
                noMoreData = true
            } else {
                cardList += moreData
            }
        } catch {
            loadMoreError = true
        }
        isLoadingMore = false
    }
}
