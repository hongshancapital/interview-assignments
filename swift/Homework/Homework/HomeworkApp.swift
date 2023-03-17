//
//  HomeworkApp.swift
//  Homework
//
//  Created by Andy on 2022/9/2.
//

import SwiftUI

@main
struct HomeworkApp: App {
    @StateObject private var viewModel = AppListViewModel()
    var body: some Scene {
        WindowGroup {
            ContentView().environmentObject(viewModel)
        }
    }
}
