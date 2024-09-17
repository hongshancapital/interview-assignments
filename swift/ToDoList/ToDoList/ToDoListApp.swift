//
//  ToDoListApp.swift
//  ToDoList
//
//  Created by berchan on 2021/12/18.
//

import SwiftUI

@main
struct ToDoListApp: App {
    
    @StateObject var model = TodoListViewModel()
    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(model)
        }
    }
}
