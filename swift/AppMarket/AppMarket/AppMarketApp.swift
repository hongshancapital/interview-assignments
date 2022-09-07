//
//  AppMarketApp.swift
//  AppMarket
//
//  Created by xcz on 2022/8/25.
//

import SwiftUI

@main
struct AppMarketApp: App {
    var body: some Scene {
        WindowGroup {
            NavigationView {
                AppListView()
                    .navigationTitle("App")
            }
        }
    }
}
