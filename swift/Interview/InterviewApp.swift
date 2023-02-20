//
//  InterviewApp.swift
//  Interview
//
//  Created by 梁宇峰 on 2023/2/17.
//

import SwiftUI

@main
struct InterviewApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView().environmentObject(ScdtAppDataModel())
        }
    }
}
