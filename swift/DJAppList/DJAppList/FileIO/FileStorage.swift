//
//  FileStorage.swift
//  AppList
//
//  Created by haojiajia on 2022/7/10.
//

import Foundation
import UIKit

@propertyWrapper
struct FileStorage<T> {
    
    var value: T?
    
    let folderName: String
    let fileName: String
    
    let queue = DispatchQueue(label: (UUID().uuidString))
    
    init(folderName: String,
         imageName: String) {
        value = FileHelper.getImage(folderName: folderName, imageName: imageName) as? T
        self.folderName = folderName
        self.fileName = imageName
    }
   
    var wrappedValue: T? {
        set {
            value = newValue
            let folderName = self.folderName
            let imageName = self.fileName
            queue.async {
                if let value = newValue as? UIImage {
                    FileHelper.saveImage(value, folderName: folderName, imageName: imageName)
                }
            }
        }
        get { value }
    }
}
