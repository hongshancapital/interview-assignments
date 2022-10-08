//
//  InterviewAssignmentApp.swift
//  Shared
//
//  Created by Vic Zhang on 2021/12/10.
//

import SwiftUI

@main
struct InterviewAssignmentApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView(viewModel: ToDoListViewModel())
        }
    }
}
