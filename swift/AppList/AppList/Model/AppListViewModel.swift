//
//  AppListViewModel.swift
//  AppList
//
//  Created by 大洋 on 2022/8/21.
//

import SwiftUI
import Combine


enum AppListLoadState {
    case none
    case refresh
    case loadError
    case loadMore
    case loadMoreError
    case noMoreData
}

class AppListViewModel: ObservableObject {
    
    @Published var settings = AppListSettings()
    @Published var appList: [AppListModel] = []
    @Published var likeList: Set<Int> = []

    private var pageIndex = 1
    private var disposeBag = Set<AnyCancellable>()
}

extension AppListViewModel {
    func loadAppList() {
        pageIndex = 1
        requestListData(pageIndex)
    }
    
    func reloadAppList() {
        if !appList.isEmpty {
            settings.loadState = .refresh
        }
        loadAppList()
    }
    
    func loadMoreAppList(_ reload: Bool = false) {
        if !reload { pageIndex += 1 }
        settings.loadState = .loadMore
        requestListData(pageIndex)
    }
    
    
    private func requestListData(_ pageIndex: Int) {
        LoadFileRequest(index: pageIndex)
            .publisher
            .sink(receiveCompletion: { complete in
                if case .failure(let error) = complete {
                    switch error {
                    case .noFile:
                        if self.settings.loadState == .none {
                            self.settings.loadState = .loadError
                        } else {
                            self.settings.loadState = .noMoreData
                        }
                    case .loadError:
                        if self.settings.loadState == .none {
                            self.settings.loadState = .loadError
                        } else {
                            self.settings.loadState = .loadMoreError
                        }
                    }
                }
            }, receiveValue: { value in
                if self.settings.loadState == .refresh {
                    self.appList.removeAll()
                }
                self.appList += value.results
                self.settings.loadState = .refresh
            })
            .store(in: &disposeBag)
    }
}


struct AppListSettings {
    var noMore = false
    
    var loadState: AppListLoadState = .none {
        didSet {
            switch loadState {
            case .noMoreData:
                noMore = true
            default:
                noMore = false
            }
        }
    }
}



extension AppListViewModel {
    
    // test method
    
    func testRequestListData(_ pageIndex: Int, completeHandler: @escaping () -> Void) {
        LoadFileRequest(index: pageIndex)
            .publisher
            .sink(receiveCompletion: { complete in
                if case .failure(let error) = complete {
                    switch error {
                    case .noFile:
                        if self.settings.loadState == .none {
                            self.settings.loadState = .loadError
                        } else {
                            self.settings.loadState = .noMoreData
                        }
                    case .loadError:
                        if self.settings.loadState == .none {
                            self.settings.loadState = .loadError
                        } else {
                            self.settings.loadState = .loadMoreError
                        }
                    }
                    completeHandler()
                }
            }, receiveValue: { value in
                if self.settings.loadState == .refresh {
                    self.appList.removeAll()
                }
                self.appList += value.results
                self.settings.loadState = .refresh
                completeHandler()
            })
            .store(in: &disposeBag)
    }
}
