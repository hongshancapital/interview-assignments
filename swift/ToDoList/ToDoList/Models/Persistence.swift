//
//  Persistence.swift
//  ToDoList
//
//  Created by berchan on 2021/12/18.
//

import CoreData
import SwiftUI

class PersistenceController {
    static let shared = PersistenceController()

    static var preview: PersistenceController = {
        let result = PersistenceController(inMemory: true)
        let viewContext = result.container.viewContext
        for _ in 0..<10 {
            let newItem = ToDoItem(context: viewContext)
            newItem.timestamp = Date()
            newItem.isFinished = false
            newItem.content = "to-do"
            
            let group = ToDoGroup(context: viewContext)
            group.timestamp = Date()
            group.name = "one"
            newItem.group = group
        }
        do {
            try viewContext.save()
        } catch {
            // Replace this implementation with code to handle the error appropriately.
            // fatalError() causes the application to generate a crash log and terminate. You should not use this function in a shipping application, although it may be useful during development.
            let nsError = error as NSError
            fatalError("Unresolved error \(nsError), \(nsError.userInfo)")
        }
        return result
    }()

    let container: NSPersistentContainer

    init(inMemory: Bool = false) {
        container = NSPersistentContainer(name: "ToDoList")
        if inMemory {
            container.persistentStoreDescriptions.first!.url = URL(fileURLWithPath: "/dev/null")
        }
        container.loadPersistentStores(completionHandler: {[weak self](storeDescription, error) in
            if let error = error as NSError? {
                fatalError("Unresolved error \(error), \(error.userInfo)")
            } else {
                self?.setupDefaultGroup()
            }
        })
    }
    
    // MARK: - setup group
    func setupDefaultGroup() {
        
        if !isFirstInstall() {
            return
        }
        
        let viewContext = container.viewContext
        let defaultGroups = ["one", "two", "three", "four"]
        for name in defaultGroups {
            let newItem = ToDoGroup(context: viewContext)
            newItem.timestamp = Date()
            newItem.name = name
            do {
                try viewContext.save()
            } catch {
                let nsError = error as NSError
                fatalError("Unresolved error \(nsError), \(nsError.userInfo)")
            }
        }
    }
}
