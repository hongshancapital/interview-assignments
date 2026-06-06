//
//  HongShanDemoApp.swift
//  HongShanDemo
//
//  Created by 木木 on 2022/5/12.
//

import SwiftUI

@main
struct HongShanDemoApp: App {
    let persistenceController = PersistenceController.shared

    var body: some Scene {
        WindowGroup {
            ContentView()
                .environment(\.managedObjectContext, persistenceController.container.viewContext)
        }
    }
}
