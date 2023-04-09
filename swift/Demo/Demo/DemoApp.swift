//
//  DemoApp.swift
//  Demo
//
//  Created by Xiaoping Tang on 2023/4/9.
//

import SwiftUI

@main
struct DemoApp: App {
    @ObservedObject var appsViewModel = AppsViewModel(appService: AppService())
    var body: some Scene {
        WindowGroup {
            AppsView()
                .environmentObject(appsViewModel)
        }
    }
}
