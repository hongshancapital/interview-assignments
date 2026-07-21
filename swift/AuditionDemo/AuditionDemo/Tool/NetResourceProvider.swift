//
//  NetResourceProvider.swift
//  AuditionDemo
//
//  Created by dian bao on 2022/7/28.
//

import Combine
import SwiftUI

//MARK: -加载状态枚举
enum LoadContentState{
    case LOADING          //正在加载
    case SUCCESS          //加载成功
    case FAIL             //加载失败
}

//MARK: -网络资源加载器
class NetResourceLoader {
    //MARK: - 实现单例
    static let sharedInstance = NetResourceLoader()
    //MARK: - 初始化方法
    private init(urlSession: URLSession = .shared,
                 imgCache: NSCache<NSURL, UIImage> = .init(),
                 dictCache: NSCache<NSURL, NSDictionary> = .init()
    ) {
        self.urlSession = urlSession
        self.imgCache = imgCache
        self.dictCache = dictCache
    }
    
    //MARK: - 常量属性
    //Demo仅需要特殊处理此种网络错误
    static let ERROR_NET_UNCONNECT = -1009                                                  //网络连接断开
    //MARK: - 变量属性
    private let urlSession: URLSession
    private let imgCache: NSCache<NSURL, UIImage>
    private let dictCache: NSCache<NSURL, NSDictionary>
    
    //MARK: - 方法
    func imgPublisher(for url: URL) -> AnyPublisher<UIImage, Error> {                       //提供图片发布者
        if let image = imgCache.object(forKey: url as NSURL) {
            return Just(image)
                .setFailureType(to: Error.self)
                .receive(on: DispatchQueue.main)
                .eraseToAnyPublisher()
        } else {
            return urlSession
                .dataTaskPublisher(for: url)
                .map(\.data)
                .tryMap { data in
                    guard let image = UIImage(data: data) else {
                        throw URLError(.badServerResponse, userInfo: [
                            NSURLErrorFailingURLErrorKey: url
                        ])
                    }
                    return image
                }
                .receive(on: DispatchQueue.main)
                .handleEvents(receiveOutput: { [imgCache] image in
                    imgCache.setObject(image, forKey: url as NSURL)
                })
                .eraseToAnyPublisher()
        }
    }
    
    func dictPublisher(for url: URL) -> AnyPublisher<NSDictionary, Error> {                 //提供字典发布者
        if let dict = dictCache.object(forKey: url as NSURL) {
            return Just(dict)
                .setFailureType(to: Error.self)
                .receive(on: DispatchQueue.main)
                .eraseToAnyPublisher()
        } else {
            return urlSession
                .dataTaskPublisher(for: url)
                .map(\.data)
                .tryMap { data in
                    
                    guard let dict = try? JSONSerialization.jsonObject(with: data, options: .mutableContainers) else {
                        throw URLError(.badServerResponse, userInfo: [
                            NSURLErrorFailingURLErrorKey: url
                        ])
                    }
                    if( (dict as? NSDictionary) == nil )
                    {
                        throw URLError(.badServerResponse, userInfo: [
                            NSURLErrorFailingURLErrorKey: url
                        ])
                    }
                    
                    return dict as! NSDictionary
                }
                .receive(on: DispatchQueue.main)
                .handleEvents(receiveOutput: { [dictCache] dict in
                    dictCache.setObject(dict, forKey: url as NSURL)
                })
                .eraseToAnyPublisher()
        }
    }
}
