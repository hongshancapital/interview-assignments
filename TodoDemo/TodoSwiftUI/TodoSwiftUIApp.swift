//
//  TodoSwiftUIApp.swift
//  TodoSwiftUI
//
//  Created by Jader Yang on 2021/12/3.
//

import SwiftUI

@main
struct TodoSwiftUIApp: App {
    
    @StateObject var listViewModel: TodoListViewModel = TodoListViewModel()
    
    var body: some Scene {
        WindowGroup {
            NavigationView{
                ListView()
            }
            .navigationViewStyle(StackNavigationViewStyle())
            .environmentObject(listViewModel)
        }
    }
}
