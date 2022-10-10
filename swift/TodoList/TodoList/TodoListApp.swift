//
//  TodoListApp.swift
//  TodoList
//
//  Created by Huanrong on 11/2/21.
//

import SwiftUI

@main
struct TodoListApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView().environmentObject(ModelData())
        }
    }
}
