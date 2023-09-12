//
//  ArtistViewModel.swift
//  text
//
//  Created by Harden.L on 2023/5/23.
//

import Foundation
import SwiftUI

class ArtistViewModel :ObservableObject {
    
    @Published private(set) var artists:Array<Artist> = Array()
    
    private var allArtists:Array<Artist> = Array()
    
    @Published private(set) var total:CGFloat = 0
    private let pageCount = 20
    
    func loadSource() {
        let source:String = Bundle.main.path(forResource: "1", ofType: "txt")!
        do
        {
            let contents = try String(contentsOfFile: source)
            let dic:NSDictionary = getDictionaryFromJSONString(jsonString: contents)
            let total1:Int = dic.value(forKey: "resultCount") as! Int
            total = CGFloat(total1)
            let data:NSArray = dic.value(forKey: "results") as! NSArray;
            for item in data {
                let artist:NSDictionary = item as!NSDictionary
                let it = Artist(dic: artist);
                allArtists.append(it)
            }
            
            artists.append(contentsOf: allArtists.suffix(20))
        }
        catch
        {
            print("Contents could not be loaded.")
        }
    }
    
    func loadMoreData(index:Int){
        let subArtists = allArtists.suffix(from: index);
        artists.append(contentsOf:subArtists.suffix(20))
    }
    
    func getDictionaryFromJSONString(jsonString:String) ->NSDictionary{
     
        let jsonData:Data = jsonString.data(using: .utf8)!
     
        let dict = try? JSONSerialization.jsonObject(with: jsonData, options: .mutableContainers)
        if dict != nil {
            return dict as! NSDictionary
        }
        return NSDictionary()
    }
    
    func removeArtist(_ index: Int) {
           self.artists.remove(at: index)
       }
    
    func clear() {
        self.allArtists.removeAll()
        self.artists.removeAll()
    }
    
    func changeList(_ art:Artist) {
        for iten in artists {
            iten.select = art.select
        }
    }
}
