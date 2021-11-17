//
//  TodoListDemoApp.swift
//  TodoListDemo
//
//  Created by walker on 2021/11/17.
//

import SwiftUI

@main
struct TodoListDemoApp: App {
    var body: some Scene {
        WindowGroup {
            GTDHomeView(viewModel: GTDViewModel())
        }
    }
}
