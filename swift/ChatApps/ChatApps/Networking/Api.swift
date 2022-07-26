//
//  Api.swift
//  ChatApps
//
//  Created by XuNing on 2022/7/30.
//

import Foundation

protocol Api {
  func fetchChatApps(page: Int) async -> Result<ChatAppList, Error>
  func favoriteApp(appId: UInt, isFavorite: Bool) async -> Result<Void, Error>
}
