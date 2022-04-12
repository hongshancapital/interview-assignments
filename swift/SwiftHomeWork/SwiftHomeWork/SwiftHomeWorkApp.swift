//
//  SwiftHomeWorkApp.swift
//  SwiftHomeWork
//
//  Created by apple on 2022/4/12.
//

import SwiftUI

@main
struct SwiftHomeWorkApp: App {
    var body: some Scene {
        WindowGroup {
            NavigationView {
                ContentView()
            }.environmentObject(DataViewModel())
        }
    }
}
