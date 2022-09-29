//
//  MemoryGame.swift
//  Memorize
//
//  Created by 费九林 on 2022/9/26.
//

import SwiftUI
import Foundation


class ItemMemoryGame: ObservableObject {
   
    
    @Published private var model:MemoryGame<String> = ItemMemoryGame.creaMeeortGame()
    
    var page: Int = 1;
   
    static func creaMeeortGame() ->MemoryGame<String>{
        
        let emojis: [AnyObject] = []
        
        return MemoryGame<String>(numberOfPairsOfCards: emojis.count) { pairIndex in
           
           return ""
           
       }
        
    }
    
    
    var cards: Array<MemoryGame<String>.Card>{
        return model.cards
    }
    
    func chooss(card:MemoryGame<String>.Card){
       
        model.chooss(card: card)
    }
    
  
    
     func generateItems(refreshing: Bool,closures: @escaping () -> Void)  {
         
         if self.page <= 0 {
             self.page = 1
         }
         
        let size: Int = self.page*10
        
        let url:URL = URL(string: "https://itunes.apple.com/search?entity=software&limit=\(String(size))&term=chat")!
        let session = URLSession.shared
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        let task = session.dataTask(with: request as URLRequest) {(
            data, response, error) in
            
            guard let data = data, let _:URLResponse = response, error == nil else {
                print("error")
                
                DispatchQueue.main.async {
                    self.page -= 1
                    closures()
                }
                return
            }
            let dataString =  String(data: data, encoding: String.Encoding.utf8)
            let dict = self.getDictionaryFromJSONString(jsonString: dataString!)
            let array = dict["results"] as! [AnyObject]
            DispatchQueue.main.async {
                self.page += 1
                self.model.Cardappend(array: array)
                closures()
            }
            
            
        }
        task.resume()
        
    }
    

     func getDictionaryFromJSONString(jsonString:String) ->NSDictionary{
        let jsonData:Data = jsonString.data(using: .utf8)!
        let dict = try? JSONSerialization.jsonObject(with: jsonData, options: .mutableContainers)
        if dict != nil {
            return dict as! NSDictionary
        }
        return NSDictionary()
    }

    
}
