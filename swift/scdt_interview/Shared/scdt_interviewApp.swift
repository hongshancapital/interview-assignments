//
//  scdt_interviewApp.swift
//  Shared
//
//  Created by Ray Tao on 2022/7/12.
//

import SwiftUI

@main
struct scdt_interviewApp: App {
    let store = Store()
    
    var body: some Scene {
        WindowGroup {
            ContentView().environmentObject(store)
        }
    }
}
