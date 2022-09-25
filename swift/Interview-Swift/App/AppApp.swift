//
//  AppApp.swift
//  App
//
//  Created by lizhao on 2022/9/19.
//

import SwiftUI

@main
struct AppApp: App {
    var body: some Scene {
        WindowGroup {
            HomeListView().environmentObject(Store())
        }
    }
}
