//
//  DemoApp.swift
//  Demo
//
//  Created by Kai on 2022/2/16.
//

import SwiftUI

@main
struct DemoApp: App {
        
    var body: some Scene {
        WindowGroup {
            ContentView().environmentObject(DataManager())
        }
    }
}
