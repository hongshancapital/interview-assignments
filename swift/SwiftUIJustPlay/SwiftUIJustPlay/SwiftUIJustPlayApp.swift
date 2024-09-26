//
//  SwiftUIJustPlayApp.swift
//  SwiftUIJustPlay
//
//  Created by wangrenzhu2021 on 2022/5/8.
//

import SwiftUI
import SwiftUIFlux

let store = Store<AppState>(reducer: appStateReducer,
                            state: AppState())

@main
struct SwiftUIJustPlayApp: App {
    var body: some Scene {
        WindowGroup {
            StoreProvider(store: store) {
                ContentView()
            }
        }
    }
}
