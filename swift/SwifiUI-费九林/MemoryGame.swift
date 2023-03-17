//
//  MemoryGame.swift
//  Memorize
//
//  Created by 费九林 on 2022/9/26.
//

import Foundation
import SwiftUI

struct MemoryGame<CardCountent> where CardCountent: Equatable {
    var cards: Array<Card>
   
    
   mutating func chooss(card:Card){
       
       if let chosenIndex: Int = cards.firstIndex(matching: card){
           
           self.cards[chosenIndex].isLike = !self.cards[chosenIndex].isLike
       }
       print("card chossen; \(card)")
    }
    
    
    init(numberOfPairsOfCards: Int, cardContFactory: (Int) -> CardCountent){
        cards = Array<Card>()
        for pairIndex in 0..<numberOfPairsOfCards {
            cards.append(
                (Card(
                      id: pairIndex,
                      imageUrl: "",
                      title: "标题0",
                      count: "塔利班",
                      isLike: false
                     ))
            )
        }
    }
    
    struct Card: Identifiable{
        var id: Int
        let imageUrl: String
        let title: String
        let count: String
        var isLike: Bool
    }
    
    mutating func Cardappend(array: [AnyObject]) {
        if array.count >= 1 {
            cards.removeAll()
        }
        for (index, element) in array.enumerated() {
           
            //print(index, ":", element)
            let sellerName: String = element["sellerName"] as! String
            let description: String = element["description"] as! String
            let artworkUrl100: String = element["artworkUrl100"] as! String
                    cards.append(
                        (Card(
                              id: index,
                              imageUrl: artworkUrl100,
                              title: sellerName,
                              count: description,
                              isLike: false
                             ))
                    )
            
            //print("\(cards)")


        }
        
        
        
//        var pairIndex = cards.count;
//        cards.append(
//            (Card(
//                  id: pairIndex,
//                  imageUrl: URL(string: "AAA")!,
//                  title: "标题标题标题标题标题标题标题标题标题标题",
//                  count: "塔利班---蜂鸟是开发呢说看菲尼克斯你开始你开始的你看附属卡菲尼克斯那分开三你看菲尼克斯",
//                  isLike: false
//                 ))
//        )
        
        
    }
    
    
    
}
