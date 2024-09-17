//
//  LocalServerAppApp.swift
//  LocalServerApp
//
//  Created by 梁泽 on 2021/9/30.
//

import SwiftUI

@main
struct LocalServerAppApp: App {
    init() {
        LocalConnectManager.shared.startLocalServer()
    }
    
    var body: some Scene {
        WindowGroup {
            HomeView()
        }
    }
}
