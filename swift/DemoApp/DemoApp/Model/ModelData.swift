//
//  ModelData.swift
//  DemoApp
//
//  Created by dev on 2023/3/10.
//

import Foundation
//import Combine

final class ModelData: ObservableObject{
    @Published var appInfos : [AppInfo] = []
    @Published var allDataIsLoaded : Bool = false
    @Published var favoriteAppIds : Set<Int> = []  // 收藏App的ID Set
    @Published var error : Error?
    
    private let linkUrl = "https://itunes.apple.com/search?entity=software&limit=25&term=chat"
    
    init(){
     reloadData()
    }
    func reloadData() {
          error = nil
          allDataIsLoaded = false
          guard let url = URL(string: linkUrl) else {return}
          URLSession.shared.dataTask(with: url) { data, response, error in
              if let error = error {
                  DispatchQueue.main.async {
                      self.error = error
                  }
              } else if let data = data {
                  do {
                      let decoder = JSONDecoder()
                      let responseInfo : ResponseInfo = try decoder.decode(ResponseInfo.self, from: data)
                      DispatchQueue.main.async {
                          self.appInfos = responseInfo.results
                      }
                  } catch {
                      DispatchQueue.main.async {
                          self.error = error
                      }
                  }
              }
          }.resume()
      }
    
    func loadMoreDataIfNeeded() {
         if allDataIsLoaded {
             return
         }
         guard let url = URL(string: linkUrl) else {return}
         URLSession.shared.dataTask(with: url) { data, response, error in
             if let error = error {
                 DispatchQueue.main.async {
                     self.error = error
                 }
             } else if let data = data {
                 do {
                     let decoder = JSONDecoder()
                     let responseInfo : ResponseInfo = try decoder.decode(ResponseInfo.self, from: data)
                     DispatchQueue.main.async {
                         self.appInfos = responseInfo.results
                         self.allDataIsLoaded = true
                     }
                 } catch {
                     DispatchQueue.main.async {
                         self.error = error
                     }
                 }
             }
         }.resume()
     }
}


