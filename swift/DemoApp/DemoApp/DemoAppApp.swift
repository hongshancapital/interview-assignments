//
//  DemoAppApp.swift
//  DemoApp
//
//  Created by Gao on 2022/7/9.
//

import SwiftUI

@main
struct DemoAppApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView().onAppear {
                    // 去除多余的打印信息
                    UserDefaults.standard.setValue(false, forKey: "_UIConstraintBasedLayoutLogUnsatisfiable")
                }
        }
    }
}
