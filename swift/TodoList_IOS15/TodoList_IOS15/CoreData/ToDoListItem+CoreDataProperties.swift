//
//  ToDoListItem+CoreDataProperties.swift
//  TodoList_IOS15
//
//  Created by Chr1s on 2021/11/3.
//
//

import Foundation
import CoreData


extension ToDoListItem {
    @nonobjc public class func fetchRequest() -> NSFetchRequest<ToDoListItem> {
        return NSFetchRequest<ToDoListItem>(entityName: "ToDoListItem")
    }

    @NSManaged public var content: String
    @NSManaged public var date: Date
    @NSManaged public var isDone: Bool
    @NSManaged public var group: TodoListGroup
}

extension ToDoListItem : Identifiable {

}
