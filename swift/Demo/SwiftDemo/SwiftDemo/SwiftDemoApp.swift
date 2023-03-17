//
//  SwiftDemoApp.swift
//  SwiftDemo
//
//  Created by 彭军涛 on 2022/7/10.
//

import SwiftUI

@main
struct SwiftDemoApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView().environmentObject(DemoViewModel())
        }
    }
}
