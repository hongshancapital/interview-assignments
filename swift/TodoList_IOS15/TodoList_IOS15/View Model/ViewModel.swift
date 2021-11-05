//
//  ViewModel.swift
//  TodoLists
//
//  Created by Chr1s on 2021/10/29.
//

import Foundation
import Combine
import SwiftUI
import CoreData

/*
    不使用之前的ViewModel中的 @Published类型的TodoListGroup数组，
    而是在View中使用 @SectionedFetchRequest 绘制自带Section的列表
 */

class ViewModel: ObservableObject {
    
    var viewContext: NSManagedObjectContext?
    var cancellables = Set<AnyCancellable>()
    
    var addGroupSubject = PassthroughSubject<String, Never>()
    var deleteGroupSubject = PassthroughSubject<TodoListGroup, Never>()
    var addItemSubject = PassthroughSubject<(TodoListGroup, String), Never>()
    var deleteItemSubject = PassthroughSubject<ToDoListItem, Never>()
    var updateItemSubject = PassthroughSubject<ToDoListItem, Never>()
    
    init() {
        
        /*
            由PassthroughSubject启动Combine链
            转换为对应的CoreData操作Publisher
         */
        
        /// 添加分组
        addGroupSubject
            .flatMap {
                self.addGroupPublisher(group: $0)
            }
            .catch { error in
                Fail(error: CoreDataError.addError(error: error))
            }
            .sink { completion in
                switch completion {
                case .failure(let error):
                    fatalError("[CoreData] Add Group Error: \(error.description)")
                    break;
                case .finished:
                    break;
                }
            } receiveValue: { _ in }
            .store(in: &cancellables)

        /// 添加事项
        addItemSubject
            .flatMap {
                self.addItemPublisher(group: $0, content: $1)
            }
            .catch { error in
                Fail(error: CoreDataError.addError(error: error))
            }
            .sink { completion in
                switch completion {
                case .failure(let error):
                    fatalError("[CoreData] Add Item Error: \(error.description)")
                    break;
                case .finished:
                    break;
                }
            } receiveValue: { [weak self] _ in
                guard let self = self else { return }
                withAnimation {
                    self.objectWillChange.send()
                }
            }
            .store(in: &cancellables)

        /// 删除事项
        deleteItemSubject
            .flatMap {
                self.deleteItemPublisher(item: $0)
            }
            .catch { error in
                Fail(error: CoreDataError.addError(error: error))
            }
            .sink { completion in
                switch completion {
                case .failure(let error):
                    fatalError("[CoreData] Add Item Error: \(error.description)")
                    break;
                case .finished:
                    break;
                }
            } receiveValue: { _ in }
            .store(in: &cancellables)
 
        /// 更新事项
        updateItemSubject
            .flatMap {
                self.updateItemPublisher(item: $0)
            }
            .catch { error in
                Fail(error: CoreDataError.addError(error: error))
            }
            .sink { completion in
                switch completion {
                case .failure(let error):
                    fatalError("[CoreData] Add Item Error: \(error.description)")
                    break;
                case .finished:
                    break;
                }
            } receiveValue: { [weak self] _ in
                guard let self = self else { return }
                withAnimation {
                    self.objectWillChange.send()
                }
            }
            .store(in: &cancellables)
        
        /// 删除分组
        deleteGroupSubject
            .flatMap {
                self.deleteGroupPublisher(group: $0)
            }
            .catch { error in
                Fail(error: CoreDataError.addError(error: error))
            }
            .sink { completion in
                switch completion {
                case .failure(let error):
                    fatalError("[CoreData] Add Item Error: \(error.description)")
                    break;
                case .finished:
                    break;
                }
            } receiveValue: { [weak self] _ in
                guard let self = self else { return }
                withAnimation {
                    self.objectWillChange.send()
                }
            }
            .store(in: &cancellables)
 
    }
    
    // MARK: - 传递context
    public func updateContext(_ context: NSManagedObjectContext) {
        self.viewContext = context
    }
    
    /*
        处理CoreData的Publisher
     */
    
    private func addGroupPublisher(group: String) -> AnyPublisher<Void, Error> {
        return Future<Void, Error> { [weak self] promise in
            guard let context = self?.viewContext else { return }
            context.performAndWait {
                let newGroup = TodoListGroup(context: context)
                newGroup.group = group
                newGroup.date = Date()
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
    
    private func deleteGroupPublisher(group: TodoListGroup) -> AnyPublisher<Void, Error> {
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
    
    private func addItemPublisher(group: TodoListGroup, content: String) -> AnyPublisher<Void, Error> {
        return Future<Void, Error> { [weak self] promise in
            guard let context = self?.viewContext else { return }
            context.performAndWait {
                let newItem = ToDoListItem(context: context)
                newItem.content = content
                newItem.isDone = false
                newItem.date = Date()
                group.addToItems(newItem)
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
    
    private func deleteItemPublisher(item: ToDoListItem) -> AnyPublisher<Void, Error> {
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
    
    private func updateItemPublisher(item: ToDoListItem) -> AnyPublisher<Void, Error> {
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
