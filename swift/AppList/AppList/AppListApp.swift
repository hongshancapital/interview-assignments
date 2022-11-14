//
//  AppListApp.swift
//  AppList
//
//  Created by Xiaojian Duan on 2022/11/13.
//

import SwiftUI

@main
struct AppListApp: App {
    private let viewModel = AppListViewModel()
    
    var body: some Scene {
        WindowGroup {
            AppListView(viewModel: viewModel)
        }
    }
}
