//
//  DemoApp.swift
//  DemoApp
//
//  Created by xiongmin on 2022/4/2.
//

import SwiftUI

@main
struct DemoApp: App {
    var body: some Scene {
        WindowGroup {
            let appInfoViewModel = AppInfoViewModel()
            AppInfoListView(viewModel: appInfoViewModel)
        }
    }
}
