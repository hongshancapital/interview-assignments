//
//  NetworkApi.swift
//  ChatApps
//
//  Created by XuNing on 2022/7/30.
//

import Foundation

class NetworkApi: Api {
  func fetchChatApps(page: Int) async -> Result<ChatAppList, Error> {
    // Mock network request
    do {
      try await Task.sleep(nanoseconds: 1 * NSEC_PER_SEC)
    } catch {
      return .failure(error)
    }
    let res: ChatAppList
    if page == 1 {
      res = MockData.page1
    } else if page == 2 {
      res = MockData.page2
    } else {
      res = MockData.emptyPage(page: page)
    }
    return .success(res)
  }
  
  func favoriteApp(appId: UInt, isFavorite: Bool) async -> Result<Void, Error> {
    // Mock network request and data updates in server
    do {
      MockData.favoriteApp(appId: appId, isFavorite: isFavorite)
      try await Task.sleep(nanoseconds: 1)
    } catch {
      return .failure(error)
    }
    
    return .success(())
  }
}
