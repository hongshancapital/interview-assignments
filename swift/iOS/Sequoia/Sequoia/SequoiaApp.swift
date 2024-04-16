//
//  SequoiaApp.swift
//  Sequoia
//
//  Created by 徐锐 on 2024/4/11.
//

import SwiftUI

@main
struct SequoiaApp: App {
    @State private var modelData = DataManager.instance
    var body: some Scene {
        WindowGroup {
            ContentView()
                .environment(modelData)
        }
    }
}
