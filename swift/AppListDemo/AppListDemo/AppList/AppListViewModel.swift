//
//  AppListViewModel.swift
//  AppListDemo
//
//  Created by 荣恒 on 2022/2/27.
//

import Foundation
import Combine

/// 刷新状态
public enum RefreshState {
    case refreshing
    case loadMore
    case node
}

// MARK: - ViewModel
/// AppList ViewModel
final public class AppListViewModel: ObservableObject {
    /// NavigationTitle
    @Published public var navigationTitle: String = "APP"
    /// EmptyView title
    @Published public var emptyTitle: String = "暂无数据"
    /// 是否展示进度
    @Published public var showProgress: Bool = true
    /// 是否加载中
    @Published public var isLoading: Bool = false
    /// 是否显示Toast
    @Published public var showToast: Bool = false
    /// toast 提示
    @Published public var toast: String = ""
    /// 加载状态
    @Published public var refreshState: RefreshState = .node
    /// 是否刷新
    @Published public var isRefreshing: Bool = false
    /// 是否加载更多
    @Published public var isLoadingMore: Bool = false
    /// App 列表数据
    @Published public var appList: [AppData] = []
    /// 是否有更多数据
    @Published public var hasMore: Bool = true
    /// App分页数
    @Published private var appListPage: Int = 0
    /// 点赞APP
    @Published private var likeApp: String = ""
    /// 取消点赞
    @Published private var cancelLikeApp: String = ""
    /// 请求网路错误
    @Published private var networkError: AppError?
    
    private var storage = Set<AnyCancellable>()
    private var firstPageAction: PassthroughSubject<Void, Never> = .init()
    private var nextPageAction: PassthroughSubject<Void, Never> = .init()
    
    init() {
        
        let firstPage = firstPageAction.debounce(for: 1, scheduler: RunLoop.main)
        let nextPage = nextPageAction.debounce(for: 1, scheduler: RunLoop.main)
        
        Publishers.Merge3(
            firstPage.map({ _ in RefreshState.refreshing }),
            nextPage.map({ _ in RefreshState.loadMore }),
            $isLoading.filter({ !$0 }).map({ _ in RefreshState.node })
        )
            .receive(on: RunLoop.main)
            .assign(to: &$refreshState)
                
        /// 分页逻辑
        let requestList = Publishers
            .Merge(firstPage.map({ _ in true }), nextPage.map({ _ in false }))
            .removeDuplicates()
            .map({ [weak self] in $0 ? 1 : (self?.appListPage ?? 0) + 1 })
            .flatMap {
                AppListDataService.requestAppList(page: $0)
                    .receive(on: RunLoop.main)
                    .handleEvents(
                        receiveSubscription: { [weak self] _ in self?.isLoading = true },
                        receiveOutput: { [weak self] _ in
                            switch self?.refreshState {
                            case .refreshing:
                                self?.appListPage = 1
                            case .loadMore:
                                self?.appListPage = (self?.appListPage ?? 0) + 1
                            default:
                                break
                            }
                            self?.isLoading = false
                        },
                        receiveCompletion: { [weak self] _ in self?.isLoading = false },
                        receiveCancel: { [weak self] in self?.isLoading = false }
                    )
                    .catch({ [weak self] error -> Just<[AppData]> in
                        self?.networkError = error
                        return .init([])
                    })
                            .filter({ !$0.isEmpty })
                            .removeDuplicates()
                            .share()
            }
            .eraseToAnyPublisher()
        
        /// Bing AppList
        requestList
            .flatMap({ [weak self] in Just(($0, self?.refreshState ?? .node)) })
            .filter({ $0.1 != .node })
            .map({ [weak self] list, state in
                if state == .refreshing {
                    return list
                }
                else {
                    return (self?.appList ?? []) + list
                }
            })
            .assign(to: &$appList)
        
        /// 点赞逻辑
        Publishers.Merge($likeApp, $cancelLikeApp)
            .filter({ !$0.isEmpty })
            .debounce(for: 0.5, scheduler: DispatchQueue.main)
            .delay(for: .seconds(0.3), scheduler: DispatchQueue.main)
            .sink { [weak self] appID in
                self?.handleLike(appID)
            }
            .store(in: &storage)
        
        $appList.map({ $0.isEmpty ? "暂无数据" : "" }).assign(to: &$emptyTitle)
                
        $appListPage.compactMap({ $0 < 2 }).removeDuplicates().assign(to: &$hasMore)
        
        $networkError.compactMap({ $0?.string }).assign(to: &$toast)
        
        Publishers.CombineLatest($appList.map(\.isEmpty), $isLoading)
            .map({ $0 && !$1 })
            .assign(to: &$showProgress)
        
        Publishers.Merge(
            $toast.map({ !$0.isEmpty }),
            $toast.filter({ !$0.isEmpty })
                .delay(for: .seconds(2), scheduler: DispatchQueue.main)
                .map({ _ in false })
        )
            .assign(to: &$showToast)
        
    }
}

// MARK: - Public
extension AppListViewModel {
    /// 刷新数据
    public func refreshData() {
        firstPageAction.send(())
    }
    
    /// 加载更多数据
    public func loadMoreData() {
        guard appListPage > 0 && appListPage < 2 else {
            return
        }
        
        nextPageAction.send(())
    }
    
    /// 处理点赞事件
    public func handleAppLike(_ app: AppData) {
        guard !app.id.isEmpty else {
            return
        }
        
        if app.like {
            cancelLikeApp = app.id
        }
        else {
            likeApp = app.id
        }
    }
}

// MARK: - Handler
extension AppListViewModel {
    
    func handleLike(_ appID: String) {
        guard
            let index = self.appList.firstIndex(where: { $0.id == appID })
        else {
            return
        }
        
        var appData = appList[index]
        appData.like = !appData.like
        appList[index] = appData
    }
    
}
