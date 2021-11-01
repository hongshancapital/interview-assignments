//
//  CoreDataService.swift
//  TodoLists
//
//  Created by Chr1s on 2021/10/29.
//

import Foundation
import CoreData
import Combine
import SwiftUI

enum CoreDataError: Error, CustomStringConvertible {

    case addError(error: Error)
    case deleteError(error: Error)
    case updateError(error: Error)
    case unknown

    var description: String {
        switch self {
        case .addError(let error):
            return "_CoreData Error When Add: \(error.localizedDescription)_"
        case .deleteError(let error):
            return "_CoreData Error When Delete: \(error.localizedDescription)_"
        case .updateError(let error):
            return "_CoreData Error When Update: \(error.localizedDescription)_"
        case .unknown:
            return "_unknown error_"
        }
    }
}

class CoreDataService {
    
    static let shared = CoreDataService()
 
    var cancellables = Set<AnyCancellable>()
    
    var viewContext: NSManagedObjectContext {
        return PersistenceController.shared.container.viewContext
    }
 
    @FetchRequest(entity: ToDoListGroup.entity(), sortDescriptors: [], predicate: nil, animation: .default) var groups: FetchedResults<ToDoListGroup>
    
    private init() { }
    
    /// 获取所有分组信息
    func fetchGroupsPublisher() -> AnyPublisher<[ToDoListGroup], Never> {
        
        return Future<[ToDoListGroup], Never> { [weak self] promise in
            
            guard let context = self?.viewContext else { return }
            
            context.performAndWait {
                do {
                    let result = try context.fetch(ToDoListGroup.fetchRequest())
                    promise(.success(result))
                } catch {
                    print("Fetch Groups Error: \(error), Replace error to empty array...")
                    promise(.success([]))
                }
            }
        }.eraseToAnyPublisher()
    }
        
    /// 新增分组
    /// - Parameters:
    ///   - group: 新增分组名 (String)
    func addGroupPublisher(group: String) -> AnyPublisher<Void, Error> {

        return Future<Void, Error> { [weak self] promise in

            guard let context = self?.viewContext else { return }

            context.performAndWait {
                let newGroup = ToDoListGroup(context: context)
                newGroup.group = group

                do {
                    try context.save()
                    promise(.success(Void()))
                } catch {
                    print("addGroup Error: \(error)")
                    promise(.failure(CoreDataError.addError(error: error)))
                }
            }
        }.eraseToAnyPublisher()
    }
    
    /// 删除分组
    /// - Parameters:
    ///   - group: 删除分组项 (ToDoListGroup)
    func deleteGroupPublisher(group: ToDoListGroup) -> AnyPublisher<Void, Error> {
        
        return Future<Void, Error> { [weak self] promise in
               
                guard let context = self?.viewContext else { return }
            
                context.performAndWait {
                    context.delete(group)
                    
                    do {
                        try context.save()
                        promise(.success(Void()))
                    } catch {
                        print("deleteGroup Error: \(error)")
                        promise(.failure(CoreDataError.deleteError(error: error)))
                    }
                }
            }
            .eraseToAnyPublisher()
    }
    
    /// 更新事项
    /// - Parameters:
    ///   - item: 指定的事项 (ToDoListItem)
    func updateItem(item: ToDoListItem) -> AnyPublisher<Void, Error> {
        
        return Future<Void, Error> { [weak self] promise in
            
            guard let context = self?.viewContext else { return }
            
            context.performAndWait {
                do {
                    try context.save()
                    promise(.success(Void()))
                } catch {
                    print("updateItem Error: \(error)")
                    promise(.failure(CoreDataError.updateError(error: error)))
                }
            }
        }.eraseToAnyPublisher()
    }
    
    /// 新增事项
    /// - Parameters:
    ///   - group: 指定的分组 (TodoListGroup)
    ///   - content: 新增的事项 (String)
    func addItemPublisher(group: ToDoListGroup, content: String) -> AnyPublisher<Void, Error> {

        return Future<Void, Error> { [weak self] promise in
            
            guard let context = self?.viewContext else { return }
            
            context.performAndWait {
                let newItem = ToDoListItem(context: context)
                newItem.content = content
                newItem.isDone = false
                newItem.date = Date()

                group.addToContains(newItem)
              
                do {
                    try context.save()
                    promise(.success(Void()))
                } catch {
                    let nsError = error as NSError
                    print("addItem Error: \(nsError)")
                    promise(.failure(CoreDataError.deleteError(error: error)))
                }
            }
        }.eraseToAnyPublisher()
    }
    
    /// 删除事项
    /// - Parameters:
    ///   - item: 指定的事项 (ToDoListItem)
    func deleteItem(item: ToDoListItem) -> AnyPublisher<Void, Error> {
        
        return Future<Void, Error> { [weak self] promise in
            
            guard let context = self?.viewContext else { return }
            
            context.performAndWait {
                context.delete(item)
                do {
                    try context.save()
                    promise(.success(Void()))
                } catch {
                    print("deleteItem Error: \(error)")
                    promise(.failure(CoreDataError.deleteError(error: error)))
                }
            }
        }.eraseToAnyPublisher()
    }
    
    // MARK: - Delete all CoreData
    public func clearAllCoreData() {
        let entities = PersistenceController.shared.container.managedObjectModel.entities
        entities.compactMap({ $0.name }).forEach(clearDeepObjectEntity)
    }

    private func clearDeepObjectEntity(_ entity: String) {
        let context = PersistenceController.shared.container.viewContext

        let deleteFetch = NSFetchRequest<NSFetchRequestResult>(entityName: entity)
        let deleteRequest = NSBatchDeleteRequest(fetchRequest: deleteFetch)

        do {
            try context.execute(deleteRequest)
            try context.save()
        } catch {
            let nsError = error as NSError
            print("Delete CoreData Error: \(nsError)")
        }
    }
}
