//
//  HSImageCache.swift
//  HSAppStore
//
//  Created by Sheng ma on 2022/5/16.
//
import Foundation
import UIKit
/**
 对图片进行缓存，实现了内存、磁盘缓存
 */

class HSImageCache {
    static let shared = HSImageCache()
    var cache = NSCache<AnyObject, AnyObject>()
    
    func imageForUrl(urlString: String, completionHandler:@escaping (_ data: Data?) -> ()) {
        let disckCache = FileManager.default.urls(for: .cachesDirectory, in: .userDomainMask).map(\.path).last
        let fileName = urlString.replacingOccurrences(of:"/", with: "")
        let fullPath = URL(fileURLWithPath: disckCache ?? "").appendingPathComponent(fileName).path
        // 异步获取图片
        DispatchQueue.global().async { [self] in
            let data: Data? = self.cache.object(forKey: urlString as AnyObject) as? Data
            // 缓存中存在直接去取并在主线程返回
            if let momeryData = data {
                DispatchQueue.main.async {
                    completionHandler(momeryData)
                }
                return
            }
            let data2: Data? = NSData(contentsOfFile: fullPath) as Data?
            if let disckData = data2 {
                DispatchQueue.main.async {
                    completionHandler(disckData)
                }
                return
            }
            let downloadTask: URLSessionDataTask = URLSession.shared.dataTask(with: URL(string: urlString)!, completionHandler: { (data, response, error) in
                if (error != nil) {
                    completionHandler(nil)
                    return
                }
                // 获得图片并且保存 主线程返回
                if data != nil {
                    self.cache.setObject(data as AnyObject, forKey: urlString as AnyObject)
                    NSData(data: data!).write(toFile: fullPath, atomically: true)
                    DispatchQueue.main.async {
                        completionHandler(data)
                    }
                    return
                }
            })
            downloadTask.resume()
        }
    }
}

