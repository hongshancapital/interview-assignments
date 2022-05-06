//
//  FetchModelAppList.swift
//  FavoriteApps
//
//  Created by 刘明星 on 2022/5/6.
//

import Foundation

final class FetchModelAppList: ObservableObject {
    @Published var appInfos = [AppInfo]()
    @Published var isHaveMoreData = true
    
    // 苹果iTunes Search API提供的接口没有分页加载的功能，只有limit字段，limit=N，一次性返回前N条数据（N默认=50），N的范围是[1, 200]
    // 这里为了模拟分页加载的效果，设计一个逻辑：模拟每页加载20条数据
    // 1、定义一个属性limit，代表接口中limit的值
    // 2、定义一个属性数组appInfos，拼接请求回来的数据中比appInfos数组多的数据
    // 3、每次请求limit的值增加50，从请求结果中拼接请求结果中比appInfos数组多的数据
    var limit = 0
    
    func fetch(){
        limit += 20
        if limit >= 200 {
            print("没有更多数据了")
            isHaveMoreData = false
            return
        }
        
        let urlString = "https://itunes.apple.com/search?entity=software&limit=\(limit)&term=chat"
        guard let url = URL(string: urlString) else {
            print("Invalid URL")
            return
        }
        
        let request = URLRequest(url: url)
        URLSession.shared.dataTask(with: request) { data, response, error in
            if let error = error {
                print(error)
                return
            }
            
            guard let data = data,
                  let responseResult = try?JSONDecoder().decode(ResourceResponse.self, from: data) else {
                      print("数据解析错误");
                return
            }
            
            DispatchQueue.main.async {
                if self.appInfos.count == 0 {
                    self.appInfos.append(contentsOf: responseResult.results)
                } else {
                    self.appInfos.append(contentsOf: responseResult.results[self.appInfos.count...])
                }
            }
        }.resume()
    }
}

