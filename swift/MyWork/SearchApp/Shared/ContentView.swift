//
//  ContentView.swift
//  Shared
//
//  Created by Changgeng Wang on 2021/9/26.
//

import SwiftUI
import CoreData

struct ContentView: View {
    @Environment(\.managedObjectContext) private var viewContext
    @StateObject var searchModel = SearchModel()
    
    var body: some View {
        NavigationView {
            VStack {
                SearchBar(text: $searchModel.keyword)
                SearchListView(sections: $searchModel.itemList,hasMore: $searchModel.hasMore) {
                    searchModel.appendQuery()
                }
            }.navigationTitle("Search")
        }
        
    }
}


struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView().environment(\.managedObjectContext, PersistenceController.preview.container.viewContext)
    }
}
