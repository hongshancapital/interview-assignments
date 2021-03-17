//
//  SwiftUIHomeWorkApp.swift
//  SwiftUIHomeWork
//
//  Created by RemiliaScarlet on 2021/3/15.
//

import SwiftUI
import CoreData

@main
struct SwiftUIHomeWorkApp: App {
    //let persistenceController = PersistenceController.shared
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                //.environment(\.managedObjectContext, persistenceController.container.viewContext)
        }
    }
}
