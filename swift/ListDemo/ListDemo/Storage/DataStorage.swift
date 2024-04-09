//
//  DataStorage.swift
//  ListDemo
//
//  Created by 王明友 on 2023/6/11.
//

import Foundation

@propertyWrapper
struct DataStorage<T: Codable> {
    var value: T?

    let directory: FileManager.SearchPathDirectory
    let fileName: String

    let queue = DispatchQueue(label: (UUID().uuidString))

    init(directory: FileManager.SearchPathDirectory, fileName: String) {
        value = try? JsonDataHelper.loadJSON(from: directory, fileName: fileName)
        self.directory = directory
        self.fileName = fileName
    }

    var wrappedValue: T? {
        set {
            value = newValue
            let directory = self.directory
            let fileName = self.fileName
            queue.async {
                if let value = newValue {
                    try? JsonDataHelper.writeJSON(value, to: directory, fileName: fileName)
                } else {
                    try? JsonDataHelper.delete(from: directory, fileName: fileName)
                }
            }
        }
        
        get { value }
    }
}
