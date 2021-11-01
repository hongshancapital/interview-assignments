//
//  ToDoListGroup+CoreDataProperties.swift
//  TodoLists
//
//  Created by Chr1s on 2021/10/29.
//
//

import Foundation
import CoreData


extension ToDoListGroup {

    @nonobjc public class func fetchRequest() -> NSFetchRequest<ToDoListGroup> {
        return NSFetchRequest<ToDoListGroup>(entityName: "ToDoListGroup")
    }

    @NSManaged public var group: String?
    @NSManaged public var contains: NSSet?
    
    public var wrappedGroup: String {
        group ?? "unknown group"
    }

    public var itemsArray: [ToDoListItem] {
        let set = contains as? Set<ToDoListItem> ?? []

        return set.sorted {
            let base1: Double = $0.isDone ? 0.0001 : 1.0
            let base2: Double = $1.isDone ? 0.0001 : 1.0
            return ($0.wrappedDate.timeIntervalSinceNow * base1) < ($1.wrappedDate.timeIntervalSinceNow * base2)
        }
    }
    
    public func filterItem(text: String = "") -> [ToDoListItem] {
        var set = contains as? Set<ToDoListItem> ?? []
        
        if !text.isEmpty {
            set = set.filter { item in
                item.wrappedContent.lowercased().contains(text.lowercased())
            }
        }
        
        /// - 对 TodoListItem 排序
        ///   - 目标：
        ///   1、未完成事项在前，已完成事项在后
        ///   2、两组事项均按时间排序
        ///   - 方法:
        ///   根据完成状态（isDone）将`基数`设置为 0.0001（isDone = true）或 1.0（isDone = false）
        ///   排序的内容为 <事项的记录日期距现在的时间间隔 * 基数>，让已完成的事项排在未完成的事项后面
        ///   同时，已完成项和未完成项都按照<事项的记录日期距现在的时间间隔 * 基数>分别排序
        return set.sorted {
            let base1: Double = $0.isDone ? 0.0001 : 1.0
            let base2: Double = $1.isDone ? 0.0001 : 1.0
            return ($0.wrappedDate.timeIntervalSinceNow * base1) < ($1.wrappedDate.timeIntervalSinceNow * base2)
        }
    }
}

// MARK: Generated accessors for contains
extension ToDoListGroup {

    @objc(addContainsObject:)
    @NSManaged public func addToContains(_ value: ToDoListItem)

    @objc(removeContainsObject:)
    @NSManaged public func removeFromContains(_ value: ToDoListItem)

    @objc(addContains:)
    @NSManaged public func addToContains(_ values: NSSet)

    @objc(removeContains:)
    @NSManaged public func removeFromContains(_ values: NSSet)

}

extension ToDoListGroup : Identifiable {

}
