//
//  ViewModel.swift
//  TodoLists
//
//  Created by Chr1s on 2021/10/29.
//

import Foundation
import Combine
import SwiftUI

class ViewModel: ObservableObject {
    
    let dataService = CoreDataService.shared
    
    @Published var todoGroups: [ToDoListGroup] = []
    @Published var searchText: String = ""
     
    var cancellables = Set<AnyCancellable>()
    var addGroupSubject = PassthroughSubject<String, Never>()
    var deleteGroupSubject = PassthroughSubject<ToDoListGroup, Never>()
    var addItemSubject = PassthroughSubject<(ToDoListGroup, String), Never>()
    var deleteItemSubject = PassthroughSubject<ToDoListItem, Never>()
    var updateItemSubject = PassthroughSubject<ToDoListItem, Never>()
    
    init() {
        
        /// 获取所有分组
        dataService.fetchGroupsPublisher()
            .receive(on: RunLoop.main)
            .sink { [weak self] groups in
                guard let self = self else { return }
                withAnimation {
                    self.todoGroups = groups
                }
            }
            .store(in: &cancellables)
        
        /*
            由PassthroughSubject启动Combine链
            转换为对应的CoreData操作Publisher
            操作完CoreData后，再转换为获取所有分组的Publisher
            最终得到的Output是所有分组数据，存储到`todoGroups`
         */
        
        /// 添加分组
        addGroupSubject
            .flatMap { group in
                self.addGroup(group)
            }
            .receive(on: RunLoop.main)
            .assign(to: &$todoGroups)
        
        /// 删除分组（占位）
        deleteGroupSubject
            .flatMap { group in
                self.deleteGroup(group)
            }
            .receive(on: RunLoop.main)
            .assign(to: &$todoGroups)
      
        /// 添加事项
        addItemSubject
            .flatMap { (group, content) in
                self.addItem(group: group, content: content)
            }
            .receive(on: RunLoop.main)
            .assign(to: &$todoGroups)
        
        /// 删除事项
        deleteItemSubject
            .flatMap { item in
                self.deleteItem(item)
            }
            .receive(on: RunLoop.main)
            .sink { [weak self] groups in
                guard let self = self else { return }
                withAnimation {
                    self.todoGroups = groups
                }
            }
            .store(in: &cancellables)

        /// 更新事项
        updateItemSubject
            .flatMap { item in
                self.updateItem(item)
            }
            .receive(on: RunLoop.main)
            .sink { [weak self] groups in
                guard let self = self else { return }
                withAnimation {
                    self.todoGroups = groups
                }
            }
            .store(in: &cancellables)
    }
        
    
    /*
        此Demo使用 `assertNoFailure` 排除错误，如有错误发生，
        则会导致Demo崩溃,适用于调试。每次flatMap转换的Publisher
        的Failure都为Never，错误被替换为空数组 []
     */
    
    /// 新增分组
    func addGroup(_ group: String) -> AnyPublisher<[ToDoListGroup], Never> {
        dataService.addGroupPublisher(group: group)
            .assertNoFailure()
            .flatMap { _ in
                self.dataService.fetchGroupsPublisher()
            }
            .eraseToAnyPublisher()
    }
    
    /// 删除分组
    func deleteGroup(_ group: ToDoListGroup) -> AnyPublisher<[ToDoListGroup], Never> {
        dataService.deleteGroupPublisher(group: group)
            .assertNoFailure()
            .flatMap { _ in
                self.dataService.fetchGroupsPublisher()
            }
            .eraseToAnyPublisher()
    }
    
    /// 新增事项
    func addItem(group: ToDoListGroup, content: String) -> AnyPublisher<[ToDoListGroup], Never> {
        dataService.addItemPublisher(group: group, content: content)
            .assertNoFailure()
            .flatMap { _ in
                self.dataService.fetchGroupsPublisher()
            }
            .eraseToAnyPublisher()
    }
    
    /// 删除事项
    func deleteItem(_ item: ToDoListItem) -> AnyPublisher<[ToDoListGroup], Never> {
        dataService.deleteItem(item: item)
            .assertNoFailure()
            .flatMap { _ in
                self.dataService.fetchGroupsPublisher()
            }
            .eraseToAnyPublisher()
    }
    
    /// 更新事项
    func updateItem(_ item: ToDoListItem) -> AnyPublisher<[ToDoListGroup], Never> {
        dataService.updateItem(item: item)
            .assertNoFailure()
            .flatMap { _ in
                self.dataService.fetchGroupsPublisher()
            }
            .eraseToAnyPublisher()
    }
}
