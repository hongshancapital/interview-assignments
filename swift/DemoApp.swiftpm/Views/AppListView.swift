import SwiftUI
import MJModule

struct AppListView: View {
    
    @State var total = 0
    @State var arrApps: [AppInfo] = []
    @State var isRefreshLoading = false
    @State var isLoadingMore = false
    
    var body: some View {
        NavigationView {
            ZStack {
                if total == 0 {
                    if isRefreshLoading {
                        LoadingView()
                    } else {
                        NoDataView()
                    }
                } else {
                    List {
                        ForEach(arrApps, id: \.self) { item in
                            AppInfoCell(appInfo: item)
                                .listRowBackground(EmptyView())
                                .listRowSeparator(.hidden)
                        }
                        // 加载更多
                        if arrApps.count < total {
                            LoadingMoreCell()
                                .listRowBackground(EmptyView())
                                .listRowSeparator(.hidden)
                                .onAppear {
                                    self.loadMore()
                                }
                        } else {
                            if total > 0 {
                                NoMoreDataCell()
                                    .listRowBackground(EmptyView())
                                    .listRowSeparator(.hidden)
                            }
                        }
                    }
                    .listStyle(.plain)
                    .background(.clear)
                    .refreshable {
                        await reload()
                    }
                }
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .background(.clear)
            .onAppear {
                Task {
                    await self.reload()
                }
            }
            .background(Color(UIColor.systemGray6))
            .navigationTitle("App")
        }
        .navigationViewStyle(.stack)
    }
    
    func reload() async {
        if isRefreshLoading {
            return
        }
        isRefreshLoading = true
        // 刷新时忽略加载更多
        isLoadingMore = false
        do {
            let response =  try await WebAppListResource().loadOnce()
            if isRefreshLoading {
                total = response.totalCount
                arrApps = response.dataList
                isRefreshLoading = false
            }
        } catch {
            LogError(error)
            isRefreshLoading = false
        }
    }
    
    func loadMore() {
        if isRefreshLoading {
            // 刷新时忽略加载更多
            return
        }
        if isLoadingMore {
            return
        }
        isLoadingMore = true
        
        WebAppListResource(startIndex: arrApps.count).loadOnce { result in
            DispatchQueue.main.async {
                if !isLoadingMore {
                    return
                }
                switch result {
                case .success(let response):
                    if response.startIndex == arrApps.count {
                        total = response.totalCount
                        var arrTmp = arrApps
                        arrTmp.append(contentsOf: response.dataList)
                        arrApps = arrTmp;
                    }
                case .failure(let error):
                    LogError(error)
                }
                isLoadingMore = false
            }
        }
    }
}


struct AppListView_Preview: PreviewProvider {
    static var previews: some View {
        AppListView(
            total: MockAppList.shared.appList.count,
            arrApps: MockAppList.shared.appList,
            isRefreshLoading: true,
            isLoadingMore: false)
    }
}
