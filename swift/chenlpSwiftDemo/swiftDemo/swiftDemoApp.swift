//
//  swiftDemoApp.swift
//  swiftDemo
//
//  Created by chenlp on 2022/4/11.
//

import SwiftUI

@main
struct swiftDemoApp: App {
    var body: some Scene {
        WindowGroup {
            HomeView().environmentObject(UserData())
        }
    }
}
