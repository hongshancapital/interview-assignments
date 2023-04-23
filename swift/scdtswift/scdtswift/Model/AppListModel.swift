//
//  AppListModel.swift
//  scdtswift
//
//  Created by esafenet on 2023/2/13.
//

import Foundation
import Alamofire
import SwiftyJSON

class AppListModel: ObservableObject {
    @Published var data: [RowModel] = []
    @Published var isLoading = false
    
    init() { }
    
    func getApplist1() {
        isLoading = true
        DispatchQueue.main.asyncAfter(deadline: .now() + 3.0) {
            self.data.append(RowModel(
                artworkUrl60: "artworkUrl60",
                description: "description",
                trackName: "trackName"))
            self.data.append(RowModel(
                artworkUrl60: "artworkUrl60",
                description: "description",
                trackName: "trackName"))
            self.isLoading = false
        }
    }
    
    func getApplist() {
        guard let url = URL(string: "https://itunes.apple.com/search?entity=software&limit=10&term=chat") else {
            print("Invalid URL")
            return
        }
        isLoading = true
        AF.request(url).response { response in
            switch response.result {
            case let .success(data):
                do {
                    let json = try! JSON(data: data!)
                    let num = json["resultCount"].intValue
                    let results = json["results"]
                    for (index, subjson): (String, JSON) in results {
                        print("\(index): \(subjson)")
                        self.data.append(RowModel(
                            artworkUrl60: subjson["artworkUrl60"].stringValue,
                            description: subjson["description"].stringValue,
                            trackName: subjson["trackName"].stringValue))
                    }
                    self.isLoading = false
                }
                
            case let .failure(error):
                print(error)
            }
        }


    }
    
    
    
}
