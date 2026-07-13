//
//  MemoDataSource.swift
//  memo
//
//  Created by LI on 2021/11/17.
//

import Foundation
import SwiftUI

class MemoDataSource: ObservableObject {
    static let `default` = MemoDataSource()
    
    private init() {}
    
    @Published private(set) var sections = [
        MemoSection(title: "SwiftUI Essentials", items: [
            MemoItem(content: "Building Lists and Navigation", checked: false),
            MemoItem(content: "Creating and Combining Views", checked: false),
            MemoItem(content: "Headling User Input", checked: false)
        ]),
        MemoSection(title: "Drawing and Animation", items: [
            MemoItem(content: "Animating Views and Transitions", checked: false),
            MemoItem(content: "Drawing Paths and Shapes", checked: false)
        ]),
        MemoSection(title: "App Design and Layout", items: [
            MemoItem(content: "Composing Complex Interfaces", checked: false),
            MemoItem(content: "Working with UI Controls", checked: false)
        ]),
        MemoSection(title: "Framework Integration", items: [
            MemoItem(content: "Interfacing with UIKit", checked: false),
            MemoItem(content: "Creating a watchOS App", checked: false),
            MemoItem(content: "Creating a masOS App", checked: false)
        ])
    ]
    
    /// MARK: add
    func add(momo content: String, sectionId: Int) {
        let section = sections.first { $0.id == sectionId }
        guard let section = section else { return }
        add(memo: content, in: section)
    }
    
    func add(memo content: String, in section: MemoSection) {
        guard content.isEmpty == false else { return }
        let item = MemoItem(content: content, checked: false)
        section.add(item: item)
        let _section = sections.first { $0.id == section.id }
        if _section == nil {
            sections.append(section)
        }
        objectWillChange.send()
    }
    
    /// MARK: delete
    func delete(item: MemoItem, sectionId: Int) {
        let section = sections.first { $0.id == sectionId }
        guard let section = section else { return }
        delete(item: item, in: section)
    }
    
    func delete(item: MemoItem, in section: MemoSection) {
        section.remove(item: item)
        if section.isEmpty() {
            sections.removeAll { $0.id == section.id }
        }
        objectWillChange.send()
    }
    
    /// MARK: sort
    /// 按照选中、标题顺序升序排序
    func sort(sectionId: Int) {
        let section = sections.first { $0.id == sectionId }
        guard let section = section else {
            return
        }
        sort(by: section)
    }
    
    /// 按照选中、标题顺序升序排序
    func sort(by section: MemoSection) {
        section.sort()
        objectWillChange.send()
    }
}
