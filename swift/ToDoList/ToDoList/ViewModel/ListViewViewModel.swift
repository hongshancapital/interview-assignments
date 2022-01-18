//
//  ListViewViewModel.swift
//  ToDoList
//
//  Created by 炎檬 on 2022/1/14.
//

import Foundation
import SwiftUI

let savedGroupsKey: String = "savedGroups"

class ListViewViewModel: ObservableObject {
    @Published private(set) var groups: [GroupModel] = []
    
    @Published var selectedGroup: String = "default group"
    
    @Published var filterGroups: [GroupModel] = []
    
    @Published private var selectedItem: ItemModel?
    
    private var throttleTimer: Timer?
    
    private let defaults = UserDefaults.standard
    
    init() {
        do {
            if let savedGroups = try defaults.getCustomObject(forKey: savedGroupsKey, castTo: [GroupModel]?.self) {
                self.groups = savedGroups
            }
        } catch {
            print(error.localizedDescription)
        }
        //初始化默认数据
        if self.groups.isEmpty {
            self.groups = [GroupModel.init("default group", [ItemModel.init("this is the your first to do!", false)])]
        }
    }
    
    func checkItem(_ group: GroupModel, _ item: ItemModel) {
        if let index = groups.firstIndex(where: { $0.groupTitle == group.groupTitle }) {
            if let _index = groups[index].items.firstIndex(where: { $0.title == item.title }) {
                groups[index].items.remove(at: _index)
            }
            insertItem(item.title, !item.isChecked, index)
        }
    }
    
    func addGroup(_ groupTitle: String) {
        if let _ = groups.firstIndex(where: { $0.groupTitle == groupTitle }) {
            return
        }
        groups.append(GroupModel.init(groupTitle, []))
        storageUpdate()
    }
    
    func removeItem(_ group: String, _ item: String) {
        if let index = groups.firstIndex(where: { $0.groupTitle == group }) {
            if let _index = groups[index].items.firstIndex(where: { $0.title == item }) {
                groups[index].items.remove(at: _index)
                storageUpdate()
            }
        }
    }
    
    func addItem(_ itemTitle: String, _ isChecked: Bool = false) {
        if itemTitle.isEmpty {
            return
        }
        if let index = groups.firstIndex(where: { $0.groupTitle == selectedGroup }) {
            if let _ = groups[index].items.firstIndex(where: { $0.title == itemTitle }) {
                return
            }
            insertItem(itemTitle, isChecked, index)
        }
    }
    
    func onSearchingChanged(_ value: String) {
        throttleTimer?.invalidate()
        throttleTimer = nil
        throttleTimer = Timer.scheduledTimer(withTimeInterval: 0.3, repeats: false, block: { [weak self] _ in
            guard let self = self else {
                return
            }
            print(value)
            let result = self.groups.map{ group -> GroupModel in
                let items = group.items.filter{ item in
                    Fuzzy.search(needle: value, haystack: item.title)
                }
                return GroupModel.init(group.groupTitle, items)
            }
            self.filterGroups = result
        })
    }
    
    private func insertItem(_ title: String, _ isChecked: Bool, _ groupIndex: Int) {
        if isChecked {
            let lastCheckedIdx = groups[groupIndex].items.firstIndex(where: { $0.isChecked }) ?? groups[groupIndex].items.count
            groups[groupIndex].items.insert(ItemModel.init(title, true), at: lastCheckedIdx)
        } else {
            let lastUnCheckedIdx = groups[groupIndex].items.firstIndex(where: { !$0.isChecked }) ?? 0
            groups[groupIndex].items.insert(ItemModel.init(title, false), at: lastUnCheckedIdx)
        }
        storageUpdate()
    }
    
    private func storageUpdate() {
        do {
            try defaults.setCustomObject(groups, forKey: savedGroupsKey)
        } catch {
            print(error.localizedDescription)
        }
    }
    
}

extension UserDefaults {
    enum ObjectSavableError: String, LocalizedError {
        case unableToEncode = "Unable to encode object into data"
        case noValue = "No data object found for the given key"
        case unableToDecode = "Unable to decode object into given type"
        
        var errorDescription: String? {
            rawValue
        }
    }
    
    func setCustomObject<Object>(_ object: Object, forKey: String) throws where Object: Encodable {
        let encoder = JSONEncoder()
        do {
            let data = try encoder.encode(object)
            set(data, forKey: forKey)
        } catch {
            throw ObjectSavableError.unableToEncode
        }
    }
    
    func getCustomObject<Object>(forKey: String, castTo type: Object.Type) throws -> Object where Object: Decodable {
        guard let data = data(forKey: forKey) else { throw ObjectSavableError.noValue }
        let decoder = JSONDecoder()
        do {
            let object = try decoder.decode(type, from: data)
            return object
        } catch {
            throw ObjectSavableError.unableToDecode
        }
    }
}
