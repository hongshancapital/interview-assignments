//
//  DemoAppApp.swift
//  DemoApp
//
//  Created by 王俊 on 2022/8/30.
//

import SwiftUI

@main
struct DemoAppApp: App {
    @StateObject private var modelData = ModelData()
    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(modelData)
        }
    }
}
