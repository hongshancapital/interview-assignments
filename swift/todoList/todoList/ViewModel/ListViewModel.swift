//
//  ListViewModel.swift
//  todoList
//
//  Created by deng on 2021/12/6.
//

import Foundation
import SwiftUI

class ListViewModel: ObservableObject {
    @Published var sectionItems: [SectionModel] = [] {
        didSet {
            saveItems()
        }
    }

    let listItemKey: String = "list_item"
    init() {
        getItems()
    }

    func getItems() {
        guard let data = UserDefaults.standard.data(forKey: listItemKey),
              let saveItems = try? JSONDecoder().decode([SectionModel].self, from: data)
        else {
            return
        }
        for item in saveItems{
            for subItme in item.items {
                subItme.isEditing = false
            }
        }
        sectionItems = saveItems
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
        var index = 0
        for mitem in sectionItems {
            if mitem.id == item.id {
                sectionItems[index] = SectionModel(header: mitem.header, items: mitem.items, checkd: true)
            } else {
                mitem.changCheck(checked: false)
            }
            index += 1
        }
    }

    func getCheckItem() -> SectionModel? {
        if let index = sectionItems.firstIndex(where: { $0.isChecked == true }) {
            return sectionItems[index]
        }
        return nil
    }

    func saveSubItem(value: String) {
        guard let currentSection: SectionModel = getCheckItem() else {
            return
        }
        currentSection.items.append(ItemModel(title: value, isCompleted: false))
        if let index = sectionItems.firstIndex(where: { $0.isChecked == true }) {
            let mitems = sorttodoList(mItems: currentSection.items)
            sectionItems[index] = SectionModel(header: currentSection.header, items: mitems, checkd: true)
        }
    }

    func updateSubItem(sectionItem: SectionModel, item: ItemModel) {
        guard let index = sectionItems.firstIndex(where: { $0.id == sectionItem.id }) else {
            return
        }
        guard let subindex = sectionItem.items.firstIndex(where: { $0.id == item.id }) else {
            return
        }
        sectionItem.items[subindex] = ItemModel(title: item.title, isCompleted: item.isCompeleted, isEditing: item.isEditing)
        let mitems = sorttodoList(mItems: sectionItem.items)
        sectionItems[index] = SectionModel(header: sectionItem.header, items: mitems)
    }

    func deleteSubItems(section: SectionModel, item: ItemModel) {
        if let index = sectionItems.firstIndex(where: { $0.id == section.id }) {
            section.items.removeAll(where: { $0.id == item.id })
            sectionItems[index] = SectionModel(header: section.header, items: section.items)
        }
    }

    func moveSubItem(section: SectionModel, from: IndexSet, to: Int) {
        if let index = sectionItems.firstIndex(where: { $0.id == section.id }) {
            sectionItems[index].items.move(fromOffsets: from, toOffset: to)
            saveItems()
        }
    }

    func sorttodoList(mItems: [ItemModel]) -> [ItemModel] {
        let sortInfo = mItems.sorted { $0.prority > $1.prority }
        return sortInfo
    }

    func saveItems() {
        if let encodeData = try? JSONEncoder().encode(self.sectionItems) {
            UserDefaults.standard.set(encodeData, forKey: listItemKey)
        }
    }
}
