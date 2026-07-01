//
//  DemoApp.swift
//  Demo
//
//  Created by 石超 on 2022/6/22.
//

import SwiftUI

@main
struct DemoApp: App {
    
    @StateObject private var modelData = ModelData()
    
    var body: some Scene {
        WindowGroup {
            ContentView().environmentObject(modelData)
        }
    }
}
