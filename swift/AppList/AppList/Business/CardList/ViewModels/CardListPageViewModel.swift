import Foundation
import Combine

@MainActor
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
    
    private var anyCancellable = Set<AnyCancellable>()
    
    init() {
        addSubject()
    }
    
    func pullToRefresh() async {
        pageNumber = 0
        do {
            cardList = try await dataProvider.fetchData(from: 0, stride: stride)
            noMoreData = false
        } catch {
            refreshError = true
        }
    }
    
    func pullToLoadMore() async {
        if isLoadingMore || noMoreData {
            return
        }
        isLoadingMore = true
        do {
            let moreData = try await dataProvider.fetchData(from: (pageNumber + 1) * stride, stride: stride)
            pageNumber += 1
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
    
    private func addSubject() {
        dataProvider.loadDataSubject
            .receive(on: DispatchQueue.main)
            .sink { [weak self] completion in
                guard let self else { return }
                switch completion {
                case .failure(_): self.pageState = .error
                case .finished: fetchFirstPageData()
                }
            } receiveValue: { _ in
            }
            .store(in: &anyCancellable)
    }
    
    private func fetchFirstPageData() {
        Task {
            do {
                self.cardList = try await dataProvider.fetchData(from: 0, stride: stride)
                self.pageState = .success
            } catch {
                pageState = .error
            }
        }
    }
}
