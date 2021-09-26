//
//  Item+CoreDataClass.swift
//  SearchApp
//
//  Created by Changgeng Wang on 2021/9/26.
//
//

import Foundation
import CoreData

extension CodingUserInfoKey {
    static let context = CodingUserInfoKey(rawValue: "context")
}

@objc(Item)
public class Item: NSManagedObject,Codable {
    
    enum CodingKeys: String, CodingKey {
        case brand = "brand"
        case category = "category"
        case product = "product"
        case price = "price"
        case stockstatus = "stockstatus"
        case timestamp = "timestamp"
    }
    
    public required convenience init(from decoder: Decoder) throws {
        guard let contextUserInfoKey = CodingUserInfoKey(rawValue: "context"),
              let managedObjectContext = decoder.userInfo[contextUserInfoKey] as? NSManagedObjectContext,
              let entity = NSEntityDescription.entity(forEntityName: "Item", in: managedObjectContext) else {
                  fatalError("Could not decode Item!")
              }
        self.init(entity: entity, insertInto: managedObjectContext)
        let values = try decoder.container(keyedBy: CodingKeys.self)
        do {
            self.brand = try values.decode(String.self, forKey: .brand)
            self.category = try values.decode(String.self, forKey: .category)
            self.product = try values.decode(String.self, forKey: .product)
            self.price = try values.decode(Double.self, forKey: .price)
            self.stockstatus = Int16(try values.decode(Int.self, forKey: .stockstatus))
            self.timestamp = try values.decode(Date.self, forKey: .timestamp)
        } catch {
            print(error)
        }
    }
    
    public func encode(to encoder: Encoder) throws {
        var container = encoder.container(keyedBy: CodingKeys.self)
        try container.encode(brand, forKey: .brand)
        try container.encode(category, forKey: .category)
        try container.encode(product, forKey: .product)
        try container.encode(price, forKey: .price)
        try container.encode(stockstatus, forKey: .stockstatus)
        try container.encode(timestamp, forKey: .timestamp)
    }
    
}
