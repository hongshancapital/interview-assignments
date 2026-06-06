//
//  IAppDemoApp.swift
//  IAppDemo
//
//  Created by lee on 2023/3/2.
//

import SwiftUI

@main
struct IAppDemoApp: App {
    var body: some Scene {
        WindowGroup {
            AppPageView().environmentObject(AppListViewModel())
        }
    }
}
