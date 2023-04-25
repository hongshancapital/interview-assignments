//
//  ListViewModel.swift
//  ToDoList
//
//  Copyright © 2022 ZhangHeng. All rights reserved.
//

import Foundation
import SwiftUI

class ListViewModel : ObservableObject{
    @Published private(set) var listModel : [ListGroup] = []
    @Published var showInputView: Bool = false
    @Published var isFocused: Bool = false

    init() {
        loadModel()
    }
    
    public func loadModel() -> Void {
        let jsonDecode = JSONDecoder()
        let jsonPath = Bundle.main.path(forResource: "TodoList", ofType: "json")!
        let jsonData : NSData = try! NSData.init(contentsOf: URL.init(fileURLWithPath: jsonPath))
        
        let models = try! jsonDecode.decode([ListGroup].self, from: jsonData as Data)
        
        self.listModel = models
        sortItems()
    }
    
    public func getGroupTitles() -> [String] {
        var titles:[String] = Array()
        for listItem in self.listModel {
            titles.append(listItem.sectionTitle)
        }
        
        return titles
    }
    
    func removeItem(section : Int, row : Int) {
        let group : ListGroup = listModel[section]
        group.listItems.remove(at: row)
        
        self.listModel = listModel
    }
    
    public func beginEditingItem(item: ListItem) {
        item.editMode = true;
        self.triggerRefresh()
    }
    
    public func finishEditItem(item :ListItem) {
        item.editMode = false;
        self.triggerRefresh()
    }
    
    public func finishItem(item : ListItem) {
        item.hasDone = true;
        self.triggerRefresh()
    }
    
    private func triggerRefresh() {
        sortItems()
        //trigger refresh event
        self.listModel = listModel
    }
    
    public func addTodo(content: String , sectionTitle:String) {
        var targetGroup:ListGroup?
        
        for group:ListGroup in listModel {
            if sectionTitle == group.sectionTitle {
                targetGroup = group
                break
            }
        }
        
        if targetGroup != nil {
            let item:ListItem = ListItem()
            item.title = content
            item.editMode = false
            item.hasDone = false
            
            targetGroup!.listItems.append(item)
            triggerRefresh()
        }
    }
    
    private func sortItems() {
        for listGroup in listModel {
            let sortItems = listGroup.listItems.sorted { itemFirst, itemSecond in
                return itemFirst.hasDone == false && itemSecond.hasDone == true
            }

            //remove empty title element
            let filterItems = sortItems.filter({
                return $0.title.count > 0
            })
            
            listGroup.listItems = filterItems
        }
    }
}
