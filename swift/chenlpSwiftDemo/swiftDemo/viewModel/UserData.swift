//
//  UserData.swift
//  swiftDemo
//
//  Created by chenlp on 2022/4/11.
//

import Combine
import Foundation

class UserData: ObservableObject {
    @Published var recommendPostList: PostList = loadPostListData("PostListData.txt")
    @Published var  pageCount: Int = 0
    private var recommendPostDic: [Int: Int] = [:]
    
    init() {
        for i in pageCount..<recommendPostList.results.count {
            let post = recommendPostList.results[i]
            recommendPostDic[post.artistId] = i
        }
    }
}

extension UserData {
    func postList() -> PostList {
        return recommendPostList
    }
    
    func post(forId id: Int) -> Post? {
        if let index = self.recommendPostDic[id] {
            return self.recommendPostList.results[index]
        }
        return nil
    }
    
    func update(_ post: Post) {
        if let index = recommendPostDic[post.artistId] {
            recommendPostList.results[index] = post
        }
    }
}


class getData: ObservableObject{
    @Published var data = [Doc]()
    @Published var count = 1
    
    init() {
        updateData()
    }
    
    func updateData(){
        let url = "http://api.plos.org/search?q=title:%22Food%22&start=\(count)&rows=10"
        
        let session = URLSession(configuration: .default)
        session.dataTask(with: URL(string: url)!) { data, _, error in
            if error != nil{
                print((error?.localizedDescription)!)
                return
            }

            do{
                let json = try JSONDecoder().decode(Detail.self, from: data!)
                let oldData = self.data
                
                DispatchQueue.main.async {
                    self.data = oldData + json.response.docs
                    self.count += 10
                }
            }
            catch{
                print("error::::\(error)" )
            }
        }.resume()
    }
}
