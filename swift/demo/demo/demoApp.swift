//
//  demoApp.swift
//  demo
//
//  Created by 朱伟 on 2022/2/11.
//

import SwiftUI

@main
struct demoApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView().environmentObject(DataManager())
        }
    }
}
