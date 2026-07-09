//
//  AppStoreDemoApp.swift
//  AppStoreDemo
//
//  Created by wuxi on 2023/3/18.
//

import SwiftUI
import Combine

@main
struct AppStoreDemoApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView().environmentObject(Store())
        }
    }
}
