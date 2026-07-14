//
//  SearchBarNew.swift
//  SearchBarDemo
//
//  Created by Jaydon Qin on 2021/10/12.
//

import SwiftUI
import Foundation

struct ContentView: View {
    
    @State private var searchTerm : String = ""
    @State private var isEditing : Bool = false
    
    var body: some View {
        NavigationView{
            VStack{
                SearchBarNew(placeholder: "Tap here to search", searchText: searchTerm) { (text) in
                    self.searchTerm = text
                } onCommit: { (text) in
                    self.searchTerm = text
                } editingChange:{(editing) in
                    self.isEditing = editing
                }
                .background(RoundedRectangle(cornerRadius: 6).foregroundColor(.gray))
                .padding(EdgeInsets(top: self.isEditing ? 20 : 0, leading: 15, bottom: 10, trailing: 15))
                if self.isEditing {
                    SearchListView(searchTerm: $searchTerm)
                }else{
                    Spacer()
                }
                
            }
            .navigationBarTitle(Text("Search"))
            .navigationBarHidden(self.isEditing)
        
            
        }
        .padding(.top, 0.0)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    
    }
   
    
}
