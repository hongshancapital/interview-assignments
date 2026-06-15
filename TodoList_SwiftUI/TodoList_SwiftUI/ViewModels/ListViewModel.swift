//
//  ListViewModel.swift
//  TodoList_SwiftUI
//
//  Created by zengmuqiang on 2021/11/23.
//

import Foundation
import UIKit

class ListViewModel: ObservableObject {
    
    @Published var sectionItems: [SectionModel] = [] {
        didSet {
            saveItems()
        }
    }
    
    let listKey: String = "listKey"
    
    init() {
        getItems()
    }
    
    func getItems() {
        guard
            let data = UserDefaults.standard.data(forKey: listKey),
            let savedItems = try? JSONDecoder().decode([SectionModel].self, from: data)
        else { return }
        
        for item in savedItems {
            for subItem in item.items {
                subItem.isEditing = false
            }
        }
        
        sectionItems = savedItems
    }
    
    func deleteItem(indexSet: IndexSet) {
        sectionItems.remove(atOffsets: indexSet)
    }
    
    func moveItem(from: IndexSet, to: Int) {
        sectionItems.move(fromOffsets: from, toOffset: to)
    }
    
    func addItem(header: String) {
        let newItem = SectionModel(header: header)
        sectionItems.append(newItem)
    }
    
    func changeCheck(item: SectionModel) {
        var idx = 0
        for section in sectionItems {
            if section.id == item.id {
                sectionItems[idx] = SectionModel(header: section.header, items: section.items, checked: true)
            } else {
                section.changeCheck(checked: false)
            }
            idx += 1
        }
    }
    
    func getCheckItem() -> SectionModel? {
        if let idx = sectionItems.firstIndex(where: { $0.isChecked == true}) {
            return sectionItems[idx]
        }
        return nil
    }
    
    func saveItems() {
        if let encodeData = try? JSONEncoder().encode(sectionItems) {
            UserDefaults.standard.set(encodeData, forKey: listKey)
        }
    }
    
    func saveChildItem(value: String) {
        guard let curSection: SectionModel = getCheckItem() else { return }
        
        curSection.items.append(ItemModel(title: value, isCompleted: false))
        if let idx = sectionItems.firstIndex(where: { $0.isChecked == true }) {
            let itemList = sortItemList(itemList: curSection.items)
            sectionItems[idx] = SectionModel(header: curSection.header, items: itemList, checked: true)
        }
    }
    
    func updateChildItem(sectionItem: SectionModel, item: ItemModel) {
        guard let idx = sectionItems.firstIndex(where: {$0.id == sectionItem.id}) else { return }
        guard let childIdx = sectionItem.items.firstIndex(where: { $0.id == item.id }) else { return }
        
        sectionItem.items[childIdx] = ItemModel(title: item.title, isCompleted: item.isCompleted, isEditing: item.isEditing)
        let itemList = sortItemList(itemList: sectionItem.items)
        sectionItems[idx] = SectionModel(header: sectionItem.header, items: itemList)
    }
    
    func sortItemList(itemList: [ItemModel]) -> [ItemModel] {
        let sortedItemList = itemList.sorted { $0.prority > $1.prority}
        return sortedItemList
    }
    
    func deleteChildItem(section: SectionModel, item: ItemModel) {
        guard let idx = sectionItems.firstIndex(where: {$0.id == section.id}) else { return }
        guard let childIdx = section.items.firstIndex(where: { $0.id == item.id }) else { return }
        
        section.items.remove(at: childIdx)
        sectionItems[idx] = SectionModel(header: section.header, items: section.items)
//        if let idx = sectionItems.firstIndex(where: { $0.id == section.id}) {
////            section.items.removeAll(where: { $0.id == item.id })
//            section.items.remove(at: idx)
//            sectionItems[idx] = SectionModel(header: section.header, items: section.items)
//        }
    }
    
    func moveChildItem(section: SectionModel, from: IndexSet, to: Int) {
        if let idx = sectionItems.firstIndex(where: { $0.id == section.id}) {
            sectionItems[idx].items.move(fromOffsets: from, toOffset: to)
            saveItems()
        }
    }
}
