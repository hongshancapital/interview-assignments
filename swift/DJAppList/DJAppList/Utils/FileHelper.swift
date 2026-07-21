//
//  FileHelper.swift
//  AppList
//
//  Created by haojiajia on 2022/7/7.
//

import Foundation
import UIKit

struct FileHelper {
    
    static func loadJSON<T: Decodable>(from folderName: String, as type: T.Type) -> T {
        guard let url = Bundle.main.url(forResource: folderName, withExtension: "json") else {
            fatalError("Resource not found: \(folderName).json")
        }
        
        let data: Data
        do {
            try data = Data(contentsOf: url)
        } catch  {
            fatalError("Couldn't load data from \(folderName).json")
        }
        
        do {
            return try JSONDecoder().decode(type, from: data)
        } catch {
            fatalError("Decode fail from \(folderName).json as \(type)")
        }
    }
    
    static func getImage(folderName: String,
                         imageName: String) -> UIImage? {
        let imageURL = getImageURL(folderName: folderName, imageName: imageName)
        return UIImage(contentsOfFile: imageURL.path)
    }
    
    static func saveImage(_ image: UIImage,
                          folderName: String,
                          imageName: String) {
        let url = getImageURL(folderName: folderName, imageName: imageName)
        
        guard !FileManager.default.fileExists(atPath: url.path) else {
            Log("Already save image")
            return
        }
        
        if let data = image.pngData() {
            do {
                try data.write(to: url)
                Log("Save image success")
            } catch let error {
                Log("Save image fail. \(error)")
            }
        }
    }
    
    static func imageExists(folderName: String, imageName: String) -> UIImage? {
        let url = getImageURL(folderName: folderName, imageName: imageName)
        if FileManager.default.fileExists(atPath: url.path) {
            return UIImage(contentsOfFile: url.path)
        } else {
            return nil
        }
    }
    
    private static func getImageURL(folderName: String, imageName: String) -> URL {
        guard let url = FileManager.default.urls(for: .cachesDirectory, in: .userDomainMask).first,
              !folderName.isEmpty else {
            fatalError("File not found")
        }
        let folderURL = url.appendingPathComponent(folderName)
        
        if !FileManager.default.fileExists(atPath: folderURL.path) {
            do {
                try FileManager.default.createDirectory(at: url, withIntermediateDirectories: true)
                Log("Success create \(folderName) folder in \(url).")
            } catch let error {
                Log(error)
            }
        }
        
        var imageURL = folderURL.appendingPathComponent(imageName)
        imageURL.appendPathExtension("png")
        return imageURL
    }
    
}
