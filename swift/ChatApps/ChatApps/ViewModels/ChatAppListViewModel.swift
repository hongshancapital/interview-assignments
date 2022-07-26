//
//  ChatAppListViewModel.swift
//  ChatApps
//
//  Created by XuNing on 2022/7/24.
//

import Foundation

enum ListState {
  case none, empty, error, loading
}

class ChatAppListViewModel: ObservableObject {
  @Published private(set) var apps: [ChatApp] = []
  @Published private(set) var state: ListState = .none

  var hasMore: Bool {
    return apps.count < totalCount
  }
  
  private var page = 1
  private var totalCount = 0
    
  private let provider: Api
  
  init(provider: Api) {
    self.provider = provider
  }
  
  @MainActor
  func refresh() async {
    guard state != .loading else {
      return
    }
    state = .loading
    
    page = 1
    let result = await provider.fetchChatApps(page: page)
    
    handleResult(result, isRefresh: true)
  }
  
  @MainActor
  func loadMore() async {
    guard state != .loading else {
      return
    }
    state = .loading
    
    page += 1
    let result = await provider.fetchChatApps(page: page)
    
    handleResult(result, isRefresh: false)
  }
  
  func favoriteApp(_ appId: UInt, isFavorite: Bool) async {
    guard let index = apps.firstIndex(where: { $0.trackId == appId }) else {
      return
    }
    apps[index].isFavorite = isFavorite
    
    let result = await provider.favoriteApp(appId: appId, isFavorite: isFavorite)
    switch result {
    case .failure(_):
      // Reverse on failure
      apps[index].isFavorite.toggle()
    case .success():
      break
    }
  }
  
  private func handleResult(_ result: Result<ChatAppList, Error>, isRefresh: Bool) {
    switch result {
    case .success(let appList):
      totalCount = appList.totalCount
      page = appList.page
      if isRefresh {
        apps.removeAll()
      }
      apps.append(contentsOf: appList.results)
      state = apps.isEmpty ? .empty : .none
    case .failure(_):
      state = .error
    }
  }
}

extension ChatAppListViewModel {
  
  convenience init(forPreiview provider: Api, apps: [ChatApp], state: ListState) {
    self.init(provider: provider)
    self.apps = apps
    self.state = state
  }
  
}
