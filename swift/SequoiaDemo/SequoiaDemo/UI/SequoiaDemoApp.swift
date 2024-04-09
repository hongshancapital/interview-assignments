//
//  SequoiaDemoApp.swift
//  SequoiaDemo
//
//  Created by 王浩沣 on 2023/4/24.
//

import SwiftUI

@main
struct SequoiaDemoApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate

    let store = Store()
    var body: some Scene {
        WindowGroup {
            NavigationView {
                    ContentView()
                    .environmentObject(store)
                    .navigationTitle("App")
                    .navigationBarTitleDisplayMode(.large)
            }
        }
    }
}
