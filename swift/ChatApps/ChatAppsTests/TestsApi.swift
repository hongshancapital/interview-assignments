//
//  TestsApi.swift
//  ChatAppsTests
//
//  Created by XuNing on 2022/7/30.
//

import Foundation

class TestsApi: Api {
  var resultChatAppList: ChatAppList?
  var resultError: Error?
  
  func fetchChatApps(page: Int) async -> Result<ChatAppList, Error> {
    try! await Task.sleep(nanoseconds: 1 * NSEC_PER_SEC)
    
    if let resultError = resultError {
      return .failure(resultError)
    } else {
      return .success(resultChatAppList!)
    }
  }
  
  func favoriteApp(appId: UInt, isFavorite: Bool) async -> Result<Void, Error> {
    try! await Task.sleep(nanoseconds: 1)
    
    if let resultError = resultError {
      return .failure(resultError)
    } else {
      MockData.favoriteApp(appId: appId, isFavorite: isFavorite)
      return .success(())
    }
  }
}
