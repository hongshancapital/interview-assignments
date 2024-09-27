//
//  ImageProvider.swift
//  AuditionDemo
//
//  Created by dian bao on 2022/7/28.
//

import SwiftUI
import Combine

//MARK: -图片提供者
class ImageProvider : ObservableObject {
    //MARK: - 属性
    @Published
    var state = LoadContentState.LOADING                            //加载状态
    var image:UIImage? = nil                                        //提供图片
    var LoadErrorCode:Int = 0                                       //加载错误码
    private var cancellable: AnyCancellable?                        //发布者
    private let nrLoader = NetResourceLoader.sharedInstance         //网络资源加载器
    
    //MARK: - 方法
    func HandleLoadingFail(_ errorcode:Int)                         //加载错误处理
    {
        self.state = LoadContentState.FAIL
        //加载系统图标，理论上不会失败，即使失败后面显示的时候依然有为空判断
        self.image = UIImage.init(named: "loaderror")
        self.LoadErrorCode = errorcode
    }
    
    func loadImage(url: URL) {                                      //加载网络图片
        self.cancellable = nrLoader.imgPublisher(for: url)
            .sink(receiveCompletion: { completion in
                DispatchQueue.main.async {
                    switch completion {
                    case .finished:
                        self.state = LoadContentState.SUCCESS
                    case .failure(let error):
                        let UrlError = error as? URLError
                        let r = UrlError?.code
                        if( r != nil )
                        {
                            self.HandleLoadingFail(r!.rawValue)
                        }
                    }
                }
            }, receiveValue: { image in
                self.image = image
            })
    }
}
