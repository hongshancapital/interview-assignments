//
//  STFileManager.swift
//  STBaseProject
//
//  Created by stack on 2018/10/10.
//  Copyright © 2018 ST. All rights reserved.
//

import UIKit

public class STFileManager: NSObject {
    
    /// 写入文件
    public static func writeToFile(content: String, filePath: String) {
        let exist = self.fileExistAt(path: filePath)
        if exist.0 {
            if content.count > 0 {
                var originContent = self.readFromFile(filePath: filePath)
                if originContent.count < 1 {
                    originContent = content
                } else {
                    originContent = "\(originContent)\n\(content)"
                }
                if let data = originContent.data(using: .utf8) {
                    do {
                        try data.write(to: URL.init(fileURLWithPath: filePath))
                    } catch {
                        print("writeToFile Err: \(error.localizedDescription)")
                    }
                }
            }
        }
    }
    
    /// 读取文件内容
    public static func readFromFile(filePath: String) -> String {
        var originContent = ""
        let exist = self.fileExistAt(path: filePath)
        if exist.0 {
            do {
                originContent = try String.init(contentsOfFile: filePath)
            } catch {
                print("readFromFile Err: \(error.localizedDescription)")
            }
        }
        return originContent
    }
    
    /// 获取沙盒根路径
    public static func getHomePath() -> String {
        return NSHomeDirectory()
    }

    /// 获取tmp路径
    public static func getTmpPath() -> String {
        return NSTemporaryDirectory()
    }

    /// 获取Documents路径
    public static func getDocumentsPath() -> String {
        let paths = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true)
        let documentsDirectory = paths[0]
        return documentsDirectory
    }

    /// 获取Library路径
    public static func getLibraryPath() -> String {
        let paths = NSSearchPathForDirectoriesInDomains(.libraryDirectory, .userDomainMask, true)
        let documentsDirectory = paths[0]
        return documentsDirectory
    }

    /// 获取LibraryCache路径
    public static func getLibraryCachePath() -> String {
        let paths = NSSearchPathForDirectoriesInDomains(.cachesDirectory, .userDomainMask, true)
        let documentsDirectory = paths[0]
        return documentsDirectory
    }

    /// 检查文件、文件夹是否存在
    ///
    /// - Parameter path: 文件路径
    ///
    /// - Returns: (路径是否存在，是否是文件夹)
    public static func fileExistAt(path: String) -> (Bool, Bool) {
        let fileManager = FileManager.default
        var isDirectory: ObjCBool = false
        let exist = fileManager.fileExists(atPath: path, isDirectory: &isDirectory)
        return (exist, isDirectory.boolValue)
    }

    /// 创建文件路径
    public static func createDirectory(path: String) -> Void {
        let fileManager = FileManager.default
        var isDirectory: ObjCBool = false
        let exist = fileManager.fileExists(atPath: path, isDirectory: &isDirectory)
        if !exist {
            do {
                try fileManager.createDirectory(at: URL.init(fileURLWithPath: path), withIntermediateDirectories: true, attributes: nil)
            } catch {
                print("createDirectory Err : \(error.localizedDescription)")
            }
        }
    }
    
    /// 创建文件
    public static func create(filePath: String, fileName: String) -> String {
        self.createDirectory(path: filePath)
        if let pathURL = URL(string: filePath) {
            let path = pathURL.appendingPathComponent(fileName)
            let fileManager = FileManager.default
            let exist = self.fileExistAt(path: path.absoluteString)
            if !exist.0 {
                fileManager.createFile(atPath: path.absoluteString, contents: nil, attributes: nil)
            }
            return path.absoluteString
        }
        return ""
    }

    /// 复制文件or文件夹
    public static func copyItem(atPath: String, toPath: String) {
        let fileManager = FileManager.default
        do {
            try fileManager.copyItem(atPath: atPath, toPath: toPath)
        } catch {
            print("copyItem Err : \(error.localizedDescription)")
        }
    }

    /// 移动文件or文件夹
    public static func moveItem(atPath: String, toPath: String) {
        let fileManager = FileManager.default
        do {
            try fileManager.moveItem(atPath: atPath, toPath: toPath)
        } catch {
            print("moveItem Err : \(error.localizedDescription)")
        }
    }

    /// 删除文件or文件夹
    public static func removeItem(atPath: String) {
        let fileManager = FileManager.default
        if fileManager.fileExists(atPath: atPath) {
            do {
                try fileManager.removeItem(atPath: atPath)
            } catch {
                print("removeItem Err : \(error.localizedDescription)")
            }
        }
    }

    /// 获取目录下所有内容
    public static func getContentsOfDirectory(atPath: String) -> Array<String> {
        var contentArr: [String] = [String]()
        let fileManager = FileManager.default
        do {
            contentArr = try fileManager.contentsOfDirectory(atPath: atPath)
        } catch {
            print("getContentsOfDirectory Err : \(error.localizedDescription)")
        }
        return contentArr
    }
    
    public class func st_logWriteToFile() -> Void {
        let userDefault = UserDefaults.standard
        if let origintContent = userDefault.object(forKey: STConstants.st_outputLogPath()) as? String {
            let path = STFileManager.create(filePath: "\(STFileManager.getLibraryCachePath())/outputLog", fileName: "log.txt")
            STFileManager.writeToFile(content: origintContent, filePath: path)
        }
    }
}
