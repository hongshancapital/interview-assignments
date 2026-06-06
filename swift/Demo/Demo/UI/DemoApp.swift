//
//  DemoApp.swift
//  Demo
//
//  Created by milomiao on 2022/6/20.
//

import SwiftUI

@main
struct DemoApp: App {
    @StateObject var provider = AppDataProvider.shared
    var body: some Scene {
        WindowGroup {
            ContentView(feed: $provider.feed)
                .environmentObject(provider)
        }
    }
}
