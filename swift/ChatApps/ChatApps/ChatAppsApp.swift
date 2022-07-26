//
//  ChatAppsApp.swift
//  ChatApps
//
//  Created by XuNing on 2022/7/23.
//

import SwiftUI

@main
struct ChatAppsApp: App {
  private let provider = NetworkApi()

  var body: some Scene {
    WindowGroup {
      ChatAppListView(provider: provider)
    }
  }
}
