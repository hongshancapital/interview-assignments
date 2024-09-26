//
//  DemoApp.swift
//  Demo
//
//  Created by Xiaoping Tang on 2023/4/9.
//

import SwiftUI

@main
struct DemoApp: App {
    private var coreDataStack: CoreDataStack
    @ObservedObject var appsViewModel: AppsViewModel
    
    init() {
        coreDataStack = CoreDataStack(modelName: "Model")
        appsViewModel = AppsViewModel(appService: AppService(), coreDataStack: coreDataStack)
    }
    
    var body: some Scene {
        WindowGroup {
            AppsView()
                .environmentObject(appsViewModel)
                .environment(\.managedObjectContext, coreDataStack.managedContext)
        }
    }
}
