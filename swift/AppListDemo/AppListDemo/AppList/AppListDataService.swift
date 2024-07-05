//
//  AppListDataService.swift
//  AppListDemo
//
//  Created by 荣恒 on 2022/2/27.
//

import Foundation
import Combine
import UIKit.UIImage

// MARK: - AppListDataService
public struct AppListDataService {
    
    private static let appURL = "https://itunes.apple.com/search?entity=software&limit=20&term=chat"
    
    /// 点赞数据缓存
    private static var likeCache: [String : Bool] = [:]
    
    /// 请求AppList数据，模拟分页数据
    public static func requestAppList(page: Int) -> AnyPublisher<[AppData], AppError> {
        guard page <= 2 else {
            return Empty()
                .eraseToAnyPublisher()
        }
        
        return Future { promise in
            requestAppList(appURL, page) {
                promise($0)
            }
        }
        .eraseToAnyPublisher()
    }
    
    /// 点赞App
    public static func likeApp(_ appID: String) -> AnyPublisher<String, Never> {
        Future { promise in
            requestLikeApp(appID, like: true) {
                promise($0)
            }
        }
        .eraseToAnyPublisher()
    }
    
    /// 取消点赞
    public static func cancelLikeApp(_ appID: String) -> AnyPublisher<String, Never> {
        Future { promise in
            requestLikeApp(appID, like: false) {
                promise($0)
            }
        }
        .eraseToAnyPublisher()
    }
}

extension AppListDataService {
    
    typealias LikeCompetion = (Result<String, Never>) -> Void
    
    /// 请求AppList
    private static func requestAppList(_ urlString: String, _ page: Int, competion: @escaping (Result<[AppData], AppError>) -> Void) {
        guard let url = URL(string: urlString) else {
            competion(.failure(AppError.network("URL错误请检查！！！")))
            return
        }
        
        var request = URLRequest(url: url)
        request.timeoutInterval = 15
        
        URLSession.shared.dataTask(with: request, completionHandler: { data, res, error in
            if let networkError = error {
                competion(.failure(AppError.error(networkError)))
                return
            }
            guard
                let response = data,
                let json = try? JSONSerialization.jsonObject(with: response) as? [String : Any],
                let result = json["results"] as? [[String : Any]]
            else {
                competion(.failure(AppError.network("数据解析错误")))
                return
            }
            // 此处接口不支持分页，暂时用分页数代替第二页数据
            let appList = AppData.toArray(data: result)
                .map({ value -> AppData in
                    var newValue = value
                    newValue.id = "\(page)-\(value.id)"
                    newValue.title = "第\(page)页 \(value.title)"
                    return newValue
                })
            competion(.success(appList))
        })
            .resume()
    }
    
    /// 请求点赞操作
    private static func requestLikeApp(_ appID: String, like: Bool, competion: @escaping LikeCompetion) {
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.3) {
            if likeCache[appID] != like {
                likeCache[appID] = like
            }
            competion(.success(appID))
        }
    }
    
}

// MARK: - AppImageLoader
public struct AppImageLoader {
    
    public static func loadImage(with url: String) async -> (UIImage?, AppImageLoader.Error?) {
        if let image = AppImageCache.get(key: url) {
            return (image, nil)
        }
        guard let url = URL.init(string: url) else {
            return (nil, .init(code: .invalidUrl, message: "非法的图片地址"))
        }
        let request = URLRequest.init(url: url)
        do {
            let (data, _) = try await URLSession.shared.data(for: request)
            guard let image = UIImage.init(data: data) else {
                return (nil, .init(code: .invalidImageData, message: "非法的图片数据"))
            }
            AppImageCache.set(image: image, for: url.absoluteString)
            return (image, nil)
        } catch {
            print("图片下载失败")
        }
        return (nil, .init(code: .requestFail, message: "请求失败"))
    }
    
    private init() {}
}

extension AppImageLoader {
    public struct Error: Swift.Error {
        enum Code: Int {
            case invalidUrl
            case invalidImageData
            case requestFail
        }
        
        var message: String
        
        init(code: Code, message: String) {
            self.message = message
        }
    }
}


/// AppImageCache 图片换成
public struct AppImageCache {
    
    /// 利用NSCache，使用内存压缩&内存警告
    private static var cache: NSCache<NSString, UIImage> = .init()
    
    public static func set(image: UIImage, for key: String) {
        cache.setObject(image, forKey: key as NSString)
    }
    
    public static func get(key: String) -> UIImage? {
        cache.object(forKey: key as NSString)
    }
    
    private init() { }
    
}
