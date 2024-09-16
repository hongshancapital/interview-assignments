//
//  ToDoListItem+CoreDataProperties.swift
//  TodoLists
//
//  Created by Chr1s on 2021/10/30.
//
//

import Foundation
import CoreData


extension ToDoListItem {

    @nonobjc public class func fetchRequest() -> NSFetchRequest<ToDoListItem> {
        return NSFetchRequest<ToDoListItem>(entityName: "ToDoListItem")
    }

    @NSManaged public var content: String?
    @NSManaged public var isDone: Bool
    @NSManaged public var date: Date?
    @NSManaged public var belong: ToDoListGroup?

    public var wrappedContent: String {
        content ?? "unknown content"
    }
    
    public var wrappedDate: Date {
        date ?? Date()
    }
}

extension ToDoListItem : Identifiable {

}
