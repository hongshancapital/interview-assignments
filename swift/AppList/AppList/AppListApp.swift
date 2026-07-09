//
//  AppListApp.swift
//  AppList
//
//  Created by wozyao on 2022/10/25.
//

import SwiftUI

@main
struct AppListApp: App {
    
    @StateObject var viewModel = ViewModel()
    
    var body: some Scene {
        WindowGroup {
            ContentView().environmentObject(viewModel)
        }
    }
}
