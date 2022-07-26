//
//  ChatAppList.swift
//  ChatApps
//
//  Created by XuNing on 2022/7/23.
//

import Foundation

struct ChatAppList: Codable {
  var totalCount: Int
  var page: Int
  var results: [ChatApp]
}
