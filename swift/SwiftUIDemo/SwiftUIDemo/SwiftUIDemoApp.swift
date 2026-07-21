//
//  SwiftUIDemoApp.swift
//  SwiftUIDemo
//
//  Created by chenghao on 2022/5/17.
//

import SwiftUI

@main
struct SwiftUIDemoApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView().environmentObject(DataStorage(dataProvider: FileDataProvider()))
        }
    }
}
