//
//  SwiftUI_DemoApp.swift
//  SwiftUI_Demo
//
//  Created by mazb on 2022/9/2.
//

import SwiftUI

@main
struct SwiftUI_DemoApp: App {
    var body: some Scene {
        WindowGroup {
            NavigationView{
                
                ContentView()
                    .navigationTitle("APP")
                    .background(Color.secondary)
            }
        }
    }
}
