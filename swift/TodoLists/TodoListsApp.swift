//
//  TodoListsApp.swift
//  TodoLists
//
//  Created by Chr1s on 2021/10/28.
//

import SwiftUI

@main
struct TodoListsApp: App {
    let persistenceController = PersistenceController.shared

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
