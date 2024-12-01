//
//  ListDemoApp.swift
//  ListDemo
//
//  Created by Chr1s on 2022/2/21.
//

import SwiftUI

@main
struct ListDemoApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView()
                .onAppear {
                    // 去除多余的打印信息
                    UserDefaults.standard.setValue(false, forKey: "_UIConstraintBasedLayoutLogUnsatisfiable")
                }
        }
    }
}
