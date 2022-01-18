//
//  ToDoListApp.swift
//  ToDoList
//
//  Created by 炎檬 on 2022/1/13.
//

import SwiftUI

/*
 MVVM architecture
 Model - data point
 View - UI
 ViewModel - manages Models for View
 
 */

@main
struct ToDoListApp: App {
    var body: some Scene {
        WindowGroup {
            NavigationView {
                ListView()
            }
        }
    }
}
