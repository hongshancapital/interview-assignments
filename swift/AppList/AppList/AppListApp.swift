//
//  AppListApp.swift
//  AppList
//
//  Created by 黄伟 on 2022/6/9.
//

import SwiftUI

@main
struct AppListApp: App {
    var body: some Scene {
        WindowGroup {
            AppListView(applistVM: AppListVM())
        }
    }
}
