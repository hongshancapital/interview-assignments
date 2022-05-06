//
//  InterviewiOSDemoApp.swift
//  InterviewiOSDemo
//
//  Created by 寇云鹏 on 2022/5/6.
//

import SwiftUI

@main
struct InterviewiOSDemoApp: App {
    var body: some Scene {
        WindowGroup {
            HomePageView().environmentObject(Store())
        }
    }
}
