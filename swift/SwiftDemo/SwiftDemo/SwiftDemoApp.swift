//
//  SwiftDemoApp.swift
//  SwiftDemo
//
//  Created by teenloong on 2022/8/27.
//

import SwiftUI

@main
struct SwiftDemoApp: App {
    @StateObject private var store = Store()
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(store)
        }
    }
}
