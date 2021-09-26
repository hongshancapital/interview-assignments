//
//  Item+CoreDataProperties.swift
//  SearchApp
//
//  Created by Changgeng Wang on 2021/9/26.
//
//

import Foundation
import CoreData


extension Item {

    @nonobjc public class func fetchRequest() -> NSFetchRequest<Item> {
        return NSFetchRequest<Item>(entityName: "Item")
    }

    @NSManaged public var timestamp: Date?
    @NSManaged public var brand: String?
    @NSManaged public var product: String?
    @NSManaged public var category: String?
    @NSManaged public var price: Double
    @NSManaged public var stockstatus: Int16

}

extension Item : Identifiable {

}
