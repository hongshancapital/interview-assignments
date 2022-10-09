//
//  SCDTApp.swift
//  SCDT
//
//  Created by Zhao Sam on 2022/8/5.
//

import SwiftUI

@main
struct SCDTApp: App {
    var body: some Scene {
        WindowGroup {
            Home().environmentObject(ApplicationViewModel())
        }
    }
}
