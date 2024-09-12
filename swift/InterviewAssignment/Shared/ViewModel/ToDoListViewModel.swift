//
//  ToDoListViewModel.swift
//  InterviewAssignment (iOS)
//
//  Created by Vic Zhang on 2021/12/10.
//

import Foundation
import SwiftUI
import Combine

class ToDoListViewModel: ObservableObject {
    @Published var state: ViewModelState = .loading
    
    // Search text
    @Published  var searchText: String = ""
    
    var originalToDoItems: [ToDoItem] = [ToDoItem]()
    @Published var toDoItems:[String: [ToDoItem]] = [String: [ToDoItem]]()
    @Published var categories: [String] = [String]()
    
    private var cancellables : [AnyCancellable] = [AnyCancellable]()
    
    //private let searchSubject = PassthroughSubject<String, Never>()
    
    init() {
    }
    
    func loadAll() {
        self.state = .loading
        
        ToDoListAPI.searchToDoList("")
            //.debounce(for: .seconds(0.5), scheduler: DispatchQueue.main, options: .none)
            .print()
            .receive(on: DispatchQueue.main)
            .sink(receiveCompletion: {[unowned self] _ in self.state = .loaded}, receiveValue: {[unowned self] res in
                self.originalToDoItems = res
                let groupedItems = Dictionary(grouping: originalToDoItems.sorted(), by: {$0.category})
                self.categories = Array(groupedItems.keys)
                self.toDoItems = groupedItems
            }).store(in: &cancellables)
    }
    
    func search(by text: String) {
        originalToDoItems.publisher.print()
//            .compactMap {item -> ToDoItem? in
//                if (text.isEmpty || item.name.contains(text)) {
//                    return item
//                }
//                return nil
//            }
            .filter({ item -> Bool in
                guard !text.isEmpty else {
                    return true
                }

                return item.name.contains(text)
            })
            .collect()
            .receive(on: DispatchQueue.main)
            .sink(receiveCompletion: {[unowned self] _ in self.state = .loaded}, receiveValue: {[unowned self] res in
                let searchedResults : [ToDoItem] = res
                self.toDoItems = Dictionary(grouping: searchedResults.sorted(), by: {$0.category})
            })
            .store(in: &cancellables)
    }
    
    func add(category: String, name: String) -> Void {
        if (category.isEmpty || name.isEmpty) {
            return
        }
        
        defer {
            self.toDoItems = Dictionary(grouping: originalToDoItems.sorted(), by: {$0.category})
        }
        
        let id = self.originalToDoItems.count
        self.originalToDoItems.append(ToDoItem(id: id, name: name, category: category, finished: false))
    }
    
    func modifyOrDelete(id: Int, categoryName: String, name: String, finished: Bool) -> Void {
        defer {
            self.toDoItems = Dictionary(grouping: originalToDoItems.sorted(), by: {$0.category})
            self.state = .loaded
        }
        
        guard let index = originalToDoItems.firstIndex(where: {$0.id == id}) else {
            return
        }
        
        if (name.isEmpty) { // delete
            self.originalToDoItems.removeAll(){$0.id == id}
        } else { // modify
            originalToDoItems[index].name = name
            originalToDoItems[index].finished = finished
        }
    }
}
