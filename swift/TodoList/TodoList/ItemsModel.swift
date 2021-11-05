//
//  ItemsModel.swift
//  TodoList
//
//  Created by Huanrong on 11/2/21.
//

import Foundation

struct ItemsModel: Identifiable {
    let id = UUID()
    var name: String
    var done: Bool = false
    var edit: Bool = false
}

struct ItemsSection: Identifiable {
    let id = UUID()
    var sectionName: String
    var items:[ItemsModel]
}

final class ModelData: ObservableObject {
    //Default data
    @Published var sections = [
        ItemsSection(sectionName: "SwiftUI Essentials", items:
                        [ItemsModel(name: "Building Lists and Navigation"),
                         ItemsModel(name: "Creating and Combining Views", done: true),
                         ItemsModel(name: "Handing User Input", done: true)]),
        ItemsSection(sectionName: "Drawing and Animation", items:
                        [ItemsModel(name: "Animating Views and Transitions"),
                         ItemsModel(name: "Drawing Paths and Shapes", done: true)]),
        ItemsSection(sectionName: "App Design and Layout", items:
                        [ItemsModel(name: "Composing Complex Interfaces"),
                         ItemsModel(name: "Working with UI Controls")]),
        ItemsSection(sectionName: "Framework Integration", items:
                        [ItemsModel(name: "Interfacing with UIKIt"),
                         ItemsModel(name: "Creating a watchOS App")])
    ]
    
    //Add new item
    func addItem(section: String, item: ItemsModel) {
        if let s = sections.firstIndex(where: { $0.sectionName == section}) {
            sections[s].items.insert(item, at: 0)
        }
    }
    
    //Items click action, change the state of done.
    func click(item: ItemsModel) {
        if let index = getIndexPathFrom(item: item) {
            sections[index.section].items[index.row].done.toggle()
            sections[index.section].items.sort { a, b in
                if a.done == false { return true }
                return false
            }
        }
    }
    
    //Items long press, change the state of edit.
    func edit(item: ItemsModel) {
        if let index = getIndexPathFrom(item: item) {
            sections[index.section].items[index.row].edit.toggle()
        }
    }
    
    //Get IndexPath of item form sections
    func getIndexPathFrom(item: ItemsModel) -> (IndexPath?) {
        var section:Int? = nil
        var row:Int? = nil
        section = sections.firstIndex { section in
            row = section.items.firstIndex(where: { $0.id == item.id })
            if row != nil { return true }
            return false
        }
        
        if let s = section, let r = row {
            return IndexPath(row: r, section: s)
        }
        return nil
    }
}
