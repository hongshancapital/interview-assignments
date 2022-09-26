//
//  DJAppListApp.swift
//  DJAppList
//
//  Created by haojiajia on 2022/7/7.
//

import SwiftUI

@main
struct DJAppListApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(AppStore())
        }
    }
}
