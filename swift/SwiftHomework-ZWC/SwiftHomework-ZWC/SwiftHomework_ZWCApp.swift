//
//  SwiftHomework_ZWCApp.swift
//  SwiftHomework-ZWC
//
//  Created by 油菜花 on 2022/2/16.
//

import SwiftUI

@main
struct SwiftHomework_ZWCApp: App {
    let appStore = HMAppstore()
    var body: some Scene {
        WindowGroup {
            ContentView(appStore: appStore)
        }
    }
}
