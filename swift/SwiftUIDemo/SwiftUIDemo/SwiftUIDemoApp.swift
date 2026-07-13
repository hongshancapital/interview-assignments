//
//  SwiftUIDemoApp.swift
//  SwiftUIDemo
//
//  Created by Machao on 2022/4/15.
//

import SwiftUI

@main
struct SwiftUIDemoApp: App {
    let storage = DataStorage(dataProvider: FileDataProvider())
    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(storage)
        }
    }
}

