//
//  AssignmentApp.swift
//  Assignment
//
//  Created by Tpphha on 2021/2/22.
//

import SwiftUI

let appState = AppState()

@main
struct AssignmentApp: App {
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(appState)
        }
    }
}
