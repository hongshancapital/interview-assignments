//
//  AppListApp.swift
//  AppList
//
//  Created by 大洋 on 2022/8/21.
//

import SwiftUI

@main
struct AppListApp: App {
    var body: some Scene {
        WindowGroup {
            AppListRootView()
                .environmentObject(AppListViewModel())
        }
    }
}
