//
//  AppHunterApp.swift
//  AppHunter
//
//  Created by zhang shijie on 2023/5/24.
//

import SwiftUI

@main
struct AppHunterApp: App {
    var body: some Scene {
        WindowGroup {
            HomeView().environmentObject(HomeViewModel(apiService: AppInfoListService()))
        }
    }
}
