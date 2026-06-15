//
//  TodoList_SwiftUIApp.swift
//  TodoList_SwiftUI
//
//  Created by zengmuqiang on 2021/11/22.
//

import SwiftUI

/**
 MVVM  Architecture
 Model  - data point
 View    - UI
 ViewModel  - manager Models for View
 */



@main
struct TodoList_SwiftUIApp: App {
    
    @StateObject var listViewModel: ListViewModel = ListViewModel()
     
    var body: some Scene {
        WindowGroup {
            NavigationView {
                ListView()
            }
            .navigationViewStyle(StackNavigationViewStyle())
            .environmentObject(listViewModel)
        }
    }
}
