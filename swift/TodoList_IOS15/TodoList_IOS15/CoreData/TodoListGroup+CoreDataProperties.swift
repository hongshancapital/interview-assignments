//
//  TodoListGroup+CoreDataProperties.swift
//  TodoList_IOS15
//
//  Created by Chr1s on 2021/11/3.
//
//

import Foundation
import CoreData
import SwiftUI


extension TodoListGroup {

    @nonobjc public class func fetchRequest() -> NSFetchRequest<TodoListGroup> {
        return NSFetchRequest<TodoListGroup>(entityName: "TodoListGroup")
    }

    @NSManaged public var group: String
    @NSManaged public var date: Date
    @NSManaged public var items: NSSet?
    
    public var itemsArray: [ToDoListItem] {
        let set = items as? Set<ToDoListItem> ?? []
        
        /// - 对 TodoListItem 排序
        ///   - 目标：
        ///   1、未完成事项在前，已完成事项在后
        ///   2、两组事项均按时间排序
        ///   - 方法:
        ///   根据完成状态（isDone）将`基数`设置为 1.0（isDone = true）或 0.0001（isDone = false）
        ///   排序的内容为 <事项的记录日期距1970的时间间隔 * 基数>，让已完成的事项排在未完成的事项后面
        ///   同时，已完成项和未完成项都按照<事项的记录日期距1970的时间间隔 * 基数>分别排序
        ///   - Unsolved Problem:
        ///   是否有比直接设置0.0001更好的方式
        return set.sorted {
            let base1: Double = $0.isDone ? 1.0 : 0.0001
            let base2: Double = $1.isDone ? 1.0 : 0.0001
            return ($0.date.timeIntervalSince1970 * base1) < ($1.date.timeIntervalSince1970 * base2)
        }
    }
}

// MARK: Generated accessors for items
extension TodoListGroup {

    @objc(addItemsObject:)
    @NSManaged public func addToItems(_ value: ToDoListItem)

    @objc(removeItemsObject:)
    @NSManaged public func removeFromItems(_ value: ToDoListItem)

    @objc(addItems:)
    @NSManaged public func addToItems(_ values: NSSet)

    @objc(removeItems:)
    @NSManaged public func removeFromItems(_ values: NSSet)

}

extension TodoListGroup : Identifiable {

}
