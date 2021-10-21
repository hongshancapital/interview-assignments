//
//  HRAssinmentApp.swift
//  HRAssinment
//
//  Created by Henry Zhang on 2021/10/11.
//

import SwiftUI

@main
struct HRAssinmentApp: App {
    init() {
        LocalServiceManager.shared.startServer()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
