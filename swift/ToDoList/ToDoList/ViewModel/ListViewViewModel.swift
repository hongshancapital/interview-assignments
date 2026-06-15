//
//  ListViewViewModel.swift
//  ToDoList
//
//  Created by 炎檬 on 2022/1/14.
//

import Foundation
import SwiftUI

let savedGroupsKey: String = "savedGroups"

final class ListViewViewModel: ObservableObject {
    @Published private(set) var originGroups: [GroupModel] = [] //原始数据
    
    @Published var filterGroups: [GroupModel] = [] //展示数据
    
    @Published var selectedGroup: String = "default group"
    
    private var searchKey: String = ""
    
    private var throttleTimer: Timer?
    
    private let defaults = UserDefaults.standard
    
    init() {
        do {
            if let savedGroups = try defaults.getCustomObject(forKey: savedGroupsKey, castTo: [GroupModel]?.self) {
                self.originGroups = savedGroups
            }
        } catch {
            print(error.localizedDescription)
        }
        //初始化默认数据
        if self.originGroups.isEmpty {
            self.originGroups = [GroupModel.init("default group", [ItemModel.init("this is the your first to do!", false)])]
        }
        self.filterGroups = originGroups
    }
    
    func checkItem(_ group: GroupModel, _ item: ItemModel) {
        if let index = originGroups.firstIndex(where: { $0.groupTitle == group.groupTitle }) {
            if let _index = originGroups[index].items.firstIndex(where: { $0.title == item.title }) {
                originGroups[index].items.remove(at: _index)
            }
            insertItem(item.title, !item.isChecked, index)
        }
    }
    
    func addGroup(_ groupTitle: String) {
        if let _ = originGroups.firstIndex(where: { $0.groupTitle == groupTitle }) {
            return
        }
        originGroups.append(GroupModel.init(groupTitle, []))
        updateFilterGroup()
        storageUpdate()
    }
    
    func removeItem(_ group: String, _ item: String) {
        if let index = originGroups.firstIndex(where: { $0.groupTitle == group }) {
            if let _index = originGroups[index].items.firstIndex(where: { $0.title == item }) {
                originGroups[index].items.remove(at: _index)
                updateFilterGroup()
                storageUpdate()
            }
        }
    }
    
    func addItem(_ itemTitle: String, _ isChecked: Bool = false) {
        if itemTitle.isEmpty {
            return
        }
        if let index = originGroups.firstIndex(where: { $0.groupTitle == selectedGroup }) {
            if let _ = originGroups[index].items.firstIndex(where: { $0.title == itemTitle }) {
                return
            }
            insertItem(itemTitle, isChecked, index)
        }
    }
    
    func onSearchingChanged(_ value: String) {
        searchKey = value
        throttleTimer?.invalidate()
        throttleTimer = nil
        throttleTimer = Timer.scheduledTimer(withTimeInterval: 0.3, repeats: false, block: { [weak self] _ in
            guard let self = self else {
                return
            }
            print(self.searchKey)
            self.updateFilterGroup()
        })
    }
    
    private func updateFilterGroup() {
        let result = originGroups.map{ group -> GroupModel in
            let items = group.items.filter{ item in
                Fuzzy.search(needle: searchKey, haystack: item.title)
            }
            return GroupModel.init(group.groupTitle, items)
        }
        filterGroups = result
    }
    
    private func insertItem(_ title: String, _ isChecked: Bool, _ groupIndex: Int) {
        if isChecked {
            let lastCheckedIdx = originGroups[groupIndex].items.firstIndex(where: { $0.isChecked }) ?? originGroups[groupIndex].items.count
            originGroups[groupIndex].items.insert(ItemModel.init(title, true), at: lastCheckedIdx)
        } else {
            let lastUnCheckedIdx = originGroups[groupIndex].items.firstIndex(where: { !$0.isChecked }) ?? 0
            originGroups[groupIndex].items.insert(ItemModel.init(title, false), at: lastUnCheckedIdx)
        }
        updateFilterGroup()
        storageUpdate()
    }
    
    private func storageUpdate() {
        do {
            try defaults.setCustomObject(originGroups, forKey: savedGroupsKey)
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
