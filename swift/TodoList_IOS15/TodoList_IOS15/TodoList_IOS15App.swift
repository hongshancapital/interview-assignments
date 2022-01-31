//
//  TodoList_IOS15App.swift
//  TodoList_IOS15
//
//  Created by Chr1s on 2021/11/2.
//

import SwiftUI

@main
struct TodoList_IOS15App: App {
    let persistenceController = PersistenceController.shared

    var body: some Scene {
        WindowGroup {
            ContentView()
                .environment(\.managedObjectContext, persistenceController.container.viewContext)
        }
    }
}
