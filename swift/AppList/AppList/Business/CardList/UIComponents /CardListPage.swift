import SwiftUI

struct CardListPage: View {
    @StateObject var viewModel = CardListPageViewModel()
    
    var body: some View {
        NavigationStack {
            Group {
                switch viewModel.pageState {
                case .loading: PageLoadingView()
                case .success: contentView
                case .error: PageErrorView()
                }
            }
            .navigationTitle("App")
            .navigationBarTitleDisplayMode(.automatic)
        }
    }
    
    var contentView: some View {
        List {
            CardListView(cardList: viewModel.cardList)
            
            RefreshFooterView(isLoadingMore: viewModel.isLoadingMore)
                .task {
                    await viewModel.pullToLoadMore()
                }
        }
        .background(Color.gray.opacity(0.1))
        .refreshable {
            await viewModel.pullToRefresh()
        }
    }
}
