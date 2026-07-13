//
//  MemoItem.swift
//  memo
//
//  Created by LI on 2021/11/14.
//

import Foundation
import SwiftUI

class MemoSection: Identifiable, ObservableObject {
    var id: Int { title.hashValue }
    var title: String = ""
    @Published private(set) var items: [MemoItem]
    
    init(title: String, items: [MemoItem]) {
        self.title = title
        self.items = items
        items.forEach { $0._section = self }
    }
    
    func add(item: MemoItem) {
        item._section = self
        items.append(item)
    }
    
    func remove(item: MemoItem) {
        items.removeAll { $0.id == item.id }
    }
    
    func isEmpty() -> Bool {
        items.isEmpty
    }
    
    func sort() {
        items.sort { lhs, rhs in
            if !lhs.checked && rhs.checked { // 选中的后置
                return true
            }
            if lhs.checked == rhs.checked && lhs.content < rhs.content { // 字符串排序
                return true
            }
            return false
        }
    }
}

class MemoItem: Identifiable, ObservableObject {
    var id: Int { content.hashValue }
    
    /// 所在分组
    fileprivate weak var _section: MemoSection? = nil
    
    /// 标题
    var content: String = "" {
        willSet {
            objectWillChange.send()
        }
    }
    /// 是否已完成
    var checked: Bool = false {
        willSet {
            objectWillChange.send()
        }
    }
    /// 是否编辑中
    var edit: Bool = false {
        willSet {
            objectWillChange.send()
        }
    }
    
    init(content: String, checked: Bool, edit: Bool = false) {
        self.content = content
        self.checked = checked
        self.edit = edit
    }
    
    func section() -> MemoSection {
        _section!
    }
}
