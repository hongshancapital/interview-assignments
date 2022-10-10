//
//  TodoListViewModel.swift
//  ToDoList
//
//  Created by berchan on 2021/12/18.
//

import Foundation
import CoreData

struct ToDoListGroup: Identifiable {
    var id: String
    var name: String
    var items: [ToDoItem]
}

class TodoListViewModel: ObservableObject {
    
    
    @Published var groups: [ToDoListGroup] = []
    @Published var groupNames: [String] = []

    let viewContenxt = PersistenceController.shared.container.viewContext
    
    let itemFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateStyle = .short
        formatter.timeStyle = .medium
        return formatter
    }()
    
    private var groupEntities: [ToDoGroup] = []
    private lazy var fetchedResultsController: NSFetchedResultsController<NSFetchRequestResult> = {
           
        let context = PersistenceController.shared.container.viewContext
        let fetchRequest = NSFetchRequest<NSFetchRequestResult>.init()
        let itemEntity = NSEntityDescription.entity(forEntityName: "ToDoItem", in: context)
        fetchRequest.entity = itemEntity
        fetchRequest.fetchLimit = 300

        let sort = NSSortDescriptor(keyPath: \ToDoItem.timestamp, ascending: false)
        fetchRequest.sortDescriptors = [sort]

        let aFetchedResultsController = NSFetchedResultsController.init(fetchRequest: fetchRequest, managedObjectContext: context, sectionNameKeyPath: "groupName", cacheName: nil)
        return aFetchedResultsController
    }()
    
    init() {
        let _ = PersistenceController.shared
    }
    
    func fetchData() {
        fetchToDoGroup()
        fetchToDoList()
    }
    
    // MARK: - Public
    // MARK: fetch to do list
    func fetchToDoList() {
        
        do {
            try fetchedResultsController.performFetch()
        } catch {
            self.groups = []
            print("home Fetch Error:\(error.localizedDescription)")
        }
        
        if let sections = fetchedResultsController.sections, sections.count > 0 {
            
            var groupLists: [ToDoListGroup] = []
            for section in sections {
                guard let objects = section.objects as? [ToDoItem] else {
                    continue
                }
                
                let completedObjects = objects.filter { item in
                    item.isFinished == true
                }
                
                let notCompletedObjects = objects.filter { item in
                    item.isFinished == false
                }
                
                let listGroup = ToDoListGroup(id: section.name, name: section.name, items: notCompletedObjects + completedObjects)
                groupLists.append(listGroup)
            }
            
            self.groups = groupLists
        }
    }
    
    // MARK: - fetch to do group
    func fetchToDoGroup() {
        let request = NSFetchRequest<NSFetchRequestResult>.init(entityName: "ToDoGroup")
        do {
            if let foundEntities = try viewContenxt.fetch(request) as? [ToDoGroup] {
                self.groupEntities = foundEntities
                let groupNames = foundEntities.map({ group in
                    group.name ?? ""
                })
                self.groupNames = groupNames
            }
        } catch  {
            self.groupEntities = []
            self.groupNames = []
            print("\(error.localizedDescription)")
        }
    }
    
    // MARK: - add & delete & update to do row
    func addItem(_ content: String, groupName: String) throws {
        
        guard let group = self.groupEntities.filter({ group in
            group.name == groupName
        }).first else {
            throw NSError.init(domain: "com.todolist.error", code: 0, userInfo: [NSLocalizedDescriptionKey: "add Failed: group not exists"])
        }
        
        let newItem = ToDoItem(context: viewContenxt)
        newItem.timestamp = Date()
        newItem.group = group
        newItem.content = content
        newItem.groupName = group.name
        newItem.isFinished = false
                        
        do {
            try viewContenxt.save()
            fetchToDoList()
        } catch  {
            let nsError = error as NSError
            print("add row failed:%@", nsError.localizedDescription)
            throw nsError
        }
    }

    func deleteItem(item: ToDoItem) throws {
        viewContenxt.delete(item)
        do {
            try viewContenxt.save()
            fetchToDoList()
        } catch  {
            let nsError = error as NSError
            print("update row failed:%@", nsError.localizedDescription)
            
            throw nsError
        }
    }
    
    func updateItem(item: ToDoItem) throws {
        do {
            try viewContenxt.save()
            fetchToDoList()
        } catch  {
            let nsError = error as NSError
            print("delete row failed:%@", nsError.localizedDescription)
            
            throw nsError
        }
    }
    
    // MARK: - add group
    func addGroup(name: String) throws {
        let group = ToDoGroup(context: viewContenxt)
        group.name = name
        group.timestamp = Date()
        
        do {
            try viewContenxt.save()
            fetchToDoGroup()
        } catch  {
            let nsError = error as NSError
            print("add group failed:%@", nsError.localizedDescription)
            throw nsError
        }
    }
}
